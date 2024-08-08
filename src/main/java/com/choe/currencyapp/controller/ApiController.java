package com.choe.currencyapp.controller;

import com.choe.currencyapp.service.CurrencyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    CurrencyApiService currencyApiService;

    @GetMapping("/getData")
    public void getData(){
        currencyApiService.get7days();
//        CurrencyResponse currencyResponse = currencyApiService.fetchAllCurrency();
//        model.addAttribute("currencyResponse", currencyResponse);
        return ;
    }

}
