package com.choe.currencyapp.repository;

import com.choe.currencyapp.entity.CurrencyApiResponse;
import com.choe.currencyapp.entity.HistoryEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CurrencyApiRepository {

    public List<HistoryEntity> getCurrencyData(String date) {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0");
        String url = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=YNSxeKpl86OM9TcskSyWggOpzyWlSZfm&data=AP01&searchdate=" + date;
        try {
            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(url));
            ResponseEntity<CurrencyApiResponse[]> response = restTemplate.exchange(requestEntity, CurrencyApiResponse[].class);
            CurrencyApiResponse[] currencyArray = response.getBody();
            if (currencyArray == null) {
                return null;
            }
            List<HistoryEntity> historyEntityList = new ArrayList<>();
            for (CurrencyApiResponse currencyApiResponse : currencyArray) {
                HistoryEntity historyEntity = new HistoryEntity();
                historyEntity.setCur_unit(currencyApiResponse.getCur_unit());
                historyEntity.setDate(parseDate(date));
                historyEntity.setKftc_deal_bas_r(Double.valueOf(currencyApiResponse.getKftc_deal_bas_r().replace(",", "")));
                historyEntityList.add(historyEntity);
            }
            return historyEntityList;
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
