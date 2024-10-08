package com.choe.currencyapp.repository;

import com.choe.currencyapp.entity.HistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CurrencyDatabaseRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(String unit, Double value, String date) {
        String sql = "INSERT INTO currency.history (date, cur_unit, kftc_deal_bas_r) values ('" + date + "', '" + unit + "', "+ value + ")";
        jdbcTemplate.execute(sql);
    }

    public Integer countHistory(String date) {
        String sql = "SELECT COUNT(*) FROM currency.history WHERE date = '" + date + "'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<HistoryEntity> getHistoryList(String date) {
        String sql = "SELECT * FROM currency.history WHERE date = '" + date + "'";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        List<HistoryEntity> historyEntityList = new ArrayList<>();

        for (Map row : rows) {
            HistoryEntity historyEntity = new HistoryEntity();
            historyEntity.setCur_unit((String) row.get("cur_unit"));
            historyEntity.setDate(((Date)row.get("date")).toLocalDate());
            historyEntity.setKftc_deal_bas_r((Double) row.get("kftc_deal_bas_r"));
            historyEntityList.add(historyEntity);
        }

        return historyEntityList;
    }
}

