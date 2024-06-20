package com.choe.currencyapp.dto;
import lombok.Data;

import java.util.List;

@Data
public class CurrencyResponse {
    private List<CurrencyData> data;
    private String highest;
    private String lowest;
}
