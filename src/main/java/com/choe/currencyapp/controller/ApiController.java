package com.choe.currencyapp.controller;

import com.choe.currencyapp.service.CurrencyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    CurrencyApiService currencyApiService;

}
