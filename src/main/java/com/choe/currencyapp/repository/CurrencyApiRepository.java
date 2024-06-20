package com.choe.currencyapp.repository;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class CurrencyApiRepository {

    public String getCurrencyData(String date){
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

                return response.toString();
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

}
