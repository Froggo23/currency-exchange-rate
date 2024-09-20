package com.choe.currencyapp.dto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CurrencyResponse {
    private Map<String, CurrencyData> map;
}
