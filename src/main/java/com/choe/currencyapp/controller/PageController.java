package com.choe.currencyapp.controller;

import com.choe.currencyapp.service.CurrencyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    CurrencyApiService currencyApiService;

    @GetMapping("/")
    public String page(Model model){
//        CurrencyResponse currencyResponse = currencyApiService.fetchAllCurrency();
//        model.addAttribute("currencyResponse", currencyResponse);
        return "index";
    }


}
