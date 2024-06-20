package com.choe.currencyapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CurrencyDatabaseRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(String unit, String value, String date) {
        String numericString = value.replace(",", "");
        double doubleValue = Double.parseDouble(numericString);
        String sql = "INSERT INTO currency.history (date, cur_unit, kftc_deal_bas_r) values ('" + date + "', '" + unit + "', "+ doubleValue + ")";
        jdbcTemplate.execute(sql);
    }

    public Integer countHistory(String date) {
        String sql = "SELECT COUNT(*) FROM currency.history WHERE date = '" + date + "'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
