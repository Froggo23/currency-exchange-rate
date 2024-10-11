package com.choe.currencyapp.service;

import com.choe.currencyapp.dto.CurrencyData;
import com.choe.currencyapp.dto.CurrencyResponse;
import com.choe.currencyapp.dto.HistoryData;
import com.choe.currencyapp.entity.CurrencyApiResponse;
import com.choe.currencyapp.entity.HistoryEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class CurrencyPageService {

    public CurrencyResponse getCurrencyResponse(List<List<HistoryEntity>> entity) {
        CurrencyResponse currencyResponse = new CurrencyResponse();
        Map<String, CurrencyData> currencyDataMap = new HashMap<>();
        currencyResponse.setMap(currencyDataMap);

        for (List<HistoryEntity> historyEntities : entity) {
            for (int j = 0; j < historyEntities.size(); j++) {
                String curUnit = historyEntities.get(j).getCur_unit();

                CurrencyData currencyData = currencyDataMap.get(curUnit);
                if (currencyData == null) {
                    currencyData = new CurrencyData();
                    currencyData.setUnit(curUnit);
                    currencyData.setHistoryEntityList(new ArrayList<>());
                    currencyDataMap.put(curUnit, currencyData);
                    Double today = entity.get(entity.size()-1).get(j).getKftc_deal_bas_r();
                    Double yesterday = entity.get(entity.size()-2).get(j).getKftc_deal_bas_r();
                    currencyData.setCurrency(today);
                    currencyData.setDailyPercentage(Double.parseDouble(String.format("%.1g%n", (((today - yesterday) / yesterday) * 100))));
                }

                HistoryData historyData = new HistoryData();
                historyData.setDate(historyEntities.get(j).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                historyData.setValue(historyEntities.get(j).getKftc_deal_bas_r());

                currencyData.getHistoryEntityList().add(historyData);
            }

        }

        return currencyResponse;
    }

}
