package com.choe.currencyapp;

import com.choe.currencyapp.dto.CurrencyData;
import com.choe.currencyapp.dto.CurrencyResponse;
import com.choe.currencyapp.dto.HistoryData;
import com.choe.currencyapp.entity.HistoryEntity;
import com.choe.currencyapp.service.CurrencyApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ConvertTest {

    @Autowired
    CurrencyApiService currencyApiService;

    @Test
    void test() {
        List<List<HistoryEntity>> sevenDayList = currencyApiService.get7days();
        Map<String, CurrencyData> currencyDataMap = new HashMap<>();

        for (int i = 0; i < sevenDayList.size(); i++) {
            for (int j = 0; j < sevenDayList.get(i).size(); j++) {
                String curUnit = sevenDayList.get(i).get(j).getCur_unit();

                CurrencyData currencyData = currencyDataMap.get(curUnit);
                if (currencyData == null) {
                    currencyData = new CurrencyData();
                    currencyData.setUnit(curUnit);
                    currencyData.setHistoryEntityList(new ArrayList<>());
                    currencyDataMap.put(curUnit, currencyData);
                    Double today = sevenDayList.get(0).get(j).getKftc_deal_bas_r();
                    Double yesterday = sevenDayList.get(1).get(j).getKftc_deal_bas_r();
                    currencyData.setCurrency(today);
                    currencyData.setDailyPercentage(Double.parseDouble(String.format("%.1g%n", (((today - yesterday) / yesterday) * 100))));
                }

                HistoryData historyData = new HistoryData();
                historyData.setDate(sevenDayList.get(i).get(j).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                historyData.setValue(sevenDayList.get(i).get(j).getKftc_deal_bas_r());

                currencyData.getHistoryEntityList().add(historyData);
            }
        }
    }
}
