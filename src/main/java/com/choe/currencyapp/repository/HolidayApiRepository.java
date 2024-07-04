package com.choe.currencyapp.repository;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class HolidayApiRepository {

    public String getHolidayData(String date){
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String[] splits = date.split("-");

        try {
            URL url = new URL("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey=V%2Fe3PXGoZK5PhH1ujmbm1blYx4fm3ofICKRTZUBAbX9JX62iVoj5wVk6WKknzCI0prqiMqAYwa32l%2FpEypjXKA%3D%3D&solYear="+splits[0]+"&solMonth="+splits[1]);
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

