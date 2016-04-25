package com.epicodus.myweather;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by Guest on 4/25/16.
 */
public class WeatherService {
    public static void findWeathers(String location, Callback callback) {
        String CONSUMER_KEY = Constants.WEATHER_CONSUMER_KEY;
        String CONSUMER_SECRET = Constants.WEATHER_CONSUMER_SECRET;
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.WEATHER_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }
}
