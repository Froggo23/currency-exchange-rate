package com.choe.currencyapp.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoryEntity {

    LocalDate date;
    String cur_unit;
    Double kftc_deal_bas_r;
}
