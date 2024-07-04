package com.choe.currencyapp.service;


import com.choe.currencyapp.entity.CurrencyApiResponse;
import com.choe.currencyapp.entity.HistoryEntity;
import com.choe.currencyapp.repository.CurrencyApiRepository;
import com.choe.currencyapp.repository.CurrencyDatabaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
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
    public List<List<HistoryEntity>> get7days() {
        LocalDate standardDate = LocalDate.now();
        List<List<HistoryEntity>> resultList = new ArrayList<>();
        int daysCount = 0;

        while (resultList.size() < 7) {
            standardDate = standardDate.minusDays(daysCount);
            String formattedDate = formatDate(standardDate);
            daysCount++;
            //주말 체크
            if(standardDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    standardDate.getDayOfWeek() ==DayOfWeek.SUNDAY){
                continue;
            }
            //이미 했는지 체크. 만약 있었으면 추가하고 스킵
            if(currencyDatabaseRepository.countHistory(formattedDate) > 0){
                List<HistoryEntity> historyEntityList = currencyDatabaseRepository.getHistoryList(formattedDate);
                resultList.add(historyEntityList);
                continue;
            }



        }
        return null;
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


    @SneakyThrows
    private List<CurrencyApiResponse> parseCurrencyData(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        CurrencyApiResponse[] currencyArray = mapper.readValue(jsonString, CurrencyApiResponse[].class);
        return List.of(currencyArray);
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
