package com.choe.currencyapp.service;

import com.choe.currencyapp.dto.CurrencyData;
import com.choe.currencyapp.dto.CurrencyResponse;
import com.choe.currencyapp.entity.HistoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CurrencyPageService {

    public CurrencyResponse getCurrencyResponse(List<List<HistoryEntity>> entity) {
        CurrencyResponse currencyResponse = new CurrencyResponse();
        for (List<HistoryEntity> list : entity) {
            for (int i = 0; i < list.size(); i++) {
                List<CurrencyData> c = currencyResponse.getData();
                CurrencyData currencyData = new CurrencyData();
                currencyData.setUnit(list.get(i).getCur_unit());
                 double c2 = list.get(i-1).getKftc_deal_bas_r();
//                currencyData.setDailyPercentage((c1-c2)/c2*100);
            }

        }



        return null;
    }

}
