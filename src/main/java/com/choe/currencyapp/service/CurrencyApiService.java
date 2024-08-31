package com.choe.currencyapp.service;


import com.choe.currencyapp.entity.HistoryEntity;
import com.choe.currencyapp.entity.history.HolidayEntity;
import com.choe.currencyapp.repository.CurrencyApiRepository;
import com.choe.currencyapp.repository.CurrencyDatabaseRepository;
import com.choe.currencyapp.repository.HolidayApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyApiService {

    @Autowired
    CurrencyApiRepository currencyApiRepository;

    @Autowired
    CurrencyDatabaseRepository currencyDatabaseRepository;

    @Autowired
    HolidayApiRepository holidayApiRepository;

    public List<List<HistoryEntity>> get7days() {
        LocalDate standardDate = LocalDate.now();
        List<List<HistoryEntity>> resultList = new ArrayList<>();

        while (resultList.size() < 7) {
            standardDate = standardDate.minusDays(1);
            String formattedDate = formatDate(standardDate);

            //주말 체크
            if (standardDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    standardDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }
            //이미 했는지 체크. 만약 있었으면 추가하고 스킵
            if (currencyDatabaseRepository.countHistory(formattedDate) > 0) {
                List<HistoryEntity> historyEntityList = currencyDatabaseRepository.getHistoryList(formattedDate);
                resultList.add(historyEntityList);
                continue;
            }
            //공휴일 체크
            HolidayEntity holidayEntity = holidayApiRepository.getHolidayData(formattedDate);
            if (isHoliday(holidayEntity, formattedDate)) {
                continue;
            }
            List<HistoryEntity> result = currencyApiRepository.getCurrencyData(formattedDate);
            if (result != null) {
                resultList.add(result);
                for (HistoryEntity historyEntity : result) {
                    currencyDatabaseRepository.save(historyEntity.getCur_unit(), historyEntity.getKftc_deal_bas_r(), formatDate(historyEntity.getDate()));
                }
            }
        }
        return resultList;
    }
//    public CurrencyResponse fetchAllCurrency() {
//        LocalDate currentDate = LocalDate.now();
//        List<List<CurrencyApiResponse>> resultList = new ArrayList<>();
//        int daysCount = 0;
//
//        while (daysCount < 10) {
//            LocalDate dateToFetch = currentDate.minusDays(daysCount);
//            String formattedDate = formatDate(dateToFetch);
//            if(currencyDatabaseRepository.countHistory(formattedDate) == 0){
//                String resultStr = currencyApiRepository.getCurrencyData(formattedDate);
//                List<CurrencyApiResponse> currencyList = parseCurrencyData(resultStr);
//                if (currencyList != null && !currencyList.isEmpty()) {
//                    for(CurrencyApiResponse currencyApiResponse : currencyList){
//                        String u = currencyApiResponse.getCur_unit();
//                        String v = currencyApiResponse.getKftc_deal_bas_r();
//                        String d = formattedDate;
//                        currencyDatabaseRepository.save(u,v,d);
//                    }
//                }
//                resultList.add(currencyList);
//            }
//            daysCount++;
//        }
//
//
//        return null;
//    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    private boolean isHoliday(HolidayEntity holidayEntity, String date) {
        if (holidayEntity == null || holidayEntity.getBody() == null || holidayEntity.getBody().getItems() == null
                || holidayEntity.getBody().getItems().getItemList() == null) {
            return false;
        }
        return holidayEntity.getBody().getItems().getItemList().stream()
                .anyMatch(item -> date.equals(item.getLocdate()));
    }
}
