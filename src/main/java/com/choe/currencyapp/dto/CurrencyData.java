package com.choe.currencyapp.dto;

import lombok.Data;
import java.util.List;

@Data
public class CurrencyData {
    private String unit;
    private Double currency;
    private Double dailyPercentage;
    private List<CurrencyHistory> currencyHistoryList;
}
