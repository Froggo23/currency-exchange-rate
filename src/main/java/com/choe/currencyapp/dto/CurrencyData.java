package com.choe.currencyapp.dto;

import com.choe.currencyapp.entity.HistoryEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CurrencyData {
    private String unit;
    private Double currency;
    private Double dailyPercentage;
    private List<HistoryData> historyEntityList;
}
