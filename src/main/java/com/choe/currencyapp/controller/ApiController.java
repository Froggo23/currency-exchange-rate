package com.choe.currencyapp.controller;

import com.choe.currencyapp.dto.CurrencyResponse;
import com.choe.currencyapp.entity.HistoryEntity;
import com.choe.currencyapp.service.CurrencyApiService;
import com.choe.currencyapp.service.CurrencyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {
    @Autowired
    CurrencyApiService currencyApiService;
    @Autowired
    private CurrencyPageService currencyPageService;

    @GetMapping("/getData")
    public CurrencyResponse getData() {
        List<List<HistoryEntity>> sevenDayList = currencyApiService.get7days();
        return currencyPageService.getCurrencyResponse(sevenDayList);
//        CurrencyResponse currencyResponse = currencyApiService.fetchAllCurrency();
//        model.addAttribute("currencyResponse", currencyResponse);
    }

}
