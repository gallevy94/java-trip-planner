package com.handson.trip_planner.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class GeocodingService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public JSONObject getLatLngFromCity(String cityName) {
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", cityName, apiKey);
        String response = restTemplate.getForObject(url, String.class);

        if (response != null) {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject location = jsonResponse.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");

            return location; // Contains "lat" and "lng"
        }

        return null;
    }
}
