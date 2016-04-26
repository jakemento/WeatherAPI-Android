package com.epicodus.myweather;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;


public class WeatherService {
    public static void findWeathers(String city, Callback callback) {
        String WEATHER_API_KEY = Constants.WEATHER_API_KEY;


        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.WEATHER_LOCATION_QUERY_PARAMETER, city);
        urlBuilder.addQueryParameter(Constants.WEATHER_API_QUERY_PARAMETER, WEATHER_API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public ArrayList<Weather> processResults(Response response) {
        ArrayList<Weather> weathers = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {


                JSONObject weatherJSON = new JSONObject(jsonData);
                JSONArray weathersJSON = weatherJSON.getJSONArray("list");
                String city = weatherJSON.getJSONObject("city").getString("name");
                String country = weatherJSON.getJSONObject("city").getString("country");
                int cityId = weatherJSON.getJSONObject("city").getInt("id");
                for (int i = 0; i < weathersJSON.length(); i++) {
                    JSONObject dayJSON = weathersJSON.getJSONObject(i);
                    int dayTemp = dayJSON.getJSONObject("temp").getInt("day");
                    int weatherId = dayJSON.getJSONArray("weather").getJSONObject(0).getInt("id");


                    Weather weather = new Weather(city, cityId, country, dayTemp);
                    weathers.add(weather);
                }
            }
    } catch (IOException e) {
        e.printStackTrace();
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return weathers;
    }


}