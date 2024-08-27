package com.handson.trip_planner.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {
    @Value("${google.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getImageUrlsForPlaces(String tripPlan, String cityName) {
        List<String> places = new ArrayList<>();
        places.add(cityName); // Add the city name to the list

        // Parse the JSON trip plan
        JSONArray days = new JSONArray(tripPlan);

        // Add places from trip days
        for (int i = 0; i < days.length(); i++) {
            JSONObject day = days.getJSONObject(i);
            JSONArray activities = day.getJSONArray("activities");

            for (int j = 0; j < activities.length(); j++) {
                String place = activities.getJSONObject(j).getString("place");
                places.add(place + " " + cityName);
            }
        }

        // Fetch image URLs for each place
        List<String> imageUrls = new ArrayList<>();
        for (String place : places) {
            String imageUrl = fetchImageUrlFromGoogle(place);
            if (imageUrl != null) {
                imageUrls.add(imageUrl);
            }
        }

        return imageUrls;
    }

    private String fetchImageUrlFromGoogle(String place) {
        String formattedPlace = place.replace(" ", "+");
        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" +
                "input=" + formattedPlace +
                "&inputtype=textquery" +
                "&fields=photos" +
                "&key=" + apiKey;

        try {
            String response = restTemplate.getForObject(url, String.class);
            String photoReference = extractPhotoReferenceFromResponse(response);

            if (photoReference != null) {
                return getPhotoUrl(photoReference);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPhotoUrl(String photoReference) {
        return "https://maps.googleapis.com/maps/api/place/photo?" +
                "maxwidth=400" +
                "&photoreference=" + photoReference +
                "&key=" + apiKey;
    }

    private String extractPhotoReferenceFromResponse(String response) throws Exception {
        JsonNode root = objectMapper.readTree(response);
        JsonNode candidates = root.path("candidates");

        if (candidates.isArray() && candidates.size() > 0) {
            JsonNode photos = candidates.get(0).path("photos");
            if (photos.isArray() && photos.size() > 0) {
                return photos.get(0).path("photo_reference").asText();
            }
        }
        return null;
    }
//
//    public String getRoutesForTripPlan(String tripPlan) {
//
//        System.out.println("from map service " + tripPlan);
//
//        List<List<LatLng>> dailyCoordinates = extractCoordinatesFromPlan(tripPlan);
//
//        List<String> routes = new ArrayList<>();
//        GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
//
//        for (List<LatLng> dayCoords : dailyCoordinates) {
//            try {
//                DirectionsResult result = DirectionsApi.newRequest(context)
//                        .origin(dayCoords.get(0))
//                        .destination(dayCoords.get(dayCoords.size() - 1))
//                        .waypoints(dayCoords.subList(1, dayCoords.size() - 1).toArray(new LatLng[0]))
//                        .optimizeWaypoints(true)
//                        .await();
//
//                routes.add(result.routes[0].overviewPolyline.getEncodedPath());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return new JSONArray(routes).toString();
//    }

    public List<List<LatLng>> extractCoordinatesFromPlan(String tripPlan) {
        JSONArray days = new JSONArray(tripPlan);
        List<List<LatLng>> dailyCoordinates = new ArrayList<>();

        for (int i = 0; i < days.length(); i++) {
            JSONArray activities = days.getJSONObject(i).getJSONArray("activities");
            List<LatLng> coordinates = new ArrayList<>();

            for (int j = 0; j < activities.length(); j++) {
                double lat = activities.getJSONObject(j).getDouble("lat");
                double lng = activities.getJSONObject(j).getDouble("lng");
                coordinates.add(new LatLng(lat, lng));
            }

            dailyCoordinates.add(coordinates);
        }

        return dailyCoordinates;
    }



//    public JSONObject getLatLngFromCity(String cityName) {
//        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", cityName, apiKey);
//        String response = restTemplate.getForObject(url, String.class);
//
//        if (response != null) {
//            JSONObject jsonResponse = new JSONObject(response);
//            JSONObject location = jsonResponse.getJSONArray("results")
//                    .getJSONObject(0)
//                    .getJSONObject("geometry")
//                    .getJSONObject("location");
//
//            return location; // Contains "lat" and "lng"
//        }
//
//        return null;
//    }
}