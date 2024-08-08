package com.choe.currencyapp.repository;

import com.choe.currencyapp.entity.CurrencyApiResponse;
import com.choe.currencyapp.entity.HistoryEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CurrencyApiRepository {

    public List<HistoryEntity> getCurrencyData(String date) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=YNSxeKpl86OM9TcskSyWggOpzyWlSZfm&data=AP01&searchdate=" + date);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }

                ObjectMapper mapper = new ObjectMapper();
                CurrencyApiResponse[] currencyArray = mapper.readValue(response.toString(), CurrencyApiResponse[].class);
                List<CurrencyApiResponse> currencyApiResponseList = List.of(currencyArray);
                List<HistoryEntity> historyEntityList = new ArrayList<>();
                for (int i = 0; i <= currencyApiResponseList.size() - 1; i++) {
                    HistoryEntity historyEntity = new HistoryEntity();
                    historyEntity.setCur_unit(currencyApiResponseList.get(i).getCur_unit());
                    historyEntity.setDate(parseDate(date));
                    historyEntity.setKftc_deal_bas_r(Double.valueOf(currencyApiResponseList.get(i).getKftc_deal_bas_r().replace(",", "")));
                    historyEntityList.add(historyEntity);
                }

                return historyEntityList;
            } else {
                System.out.println("GET request not worked");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private LocalDate parseDate(String dateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
