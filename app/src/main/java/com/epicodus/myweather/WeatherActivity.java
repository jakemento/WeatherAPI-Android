package com.epicodus.myweather;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    public ArrayList<Weather> mWeathers = new ArrayList<>();
    public static final String TAG = WeatherActivity.class.getSimpleName();
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.listView) ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        getWeathers(location);
        mLocationTextView.setText("the city you entered is " + location + "!");
    }

    private void getWeathers(String location) {
        final WeatherService weatherService = new WeatherService();

        weatherService.findWeathers(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mWeathers = weatherService.processResults(response);
                WeatherActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter adapter = new ArrayAdapter(WeatherActivity.this,
                                android.R.layout.simple_list_item_1);
                        mListView.setAdapter(adapter);

                        for (Weather weather : mWeathers) {
                            Log.d(TAG, "Temp: " + weather.getTemp());
                            Log.d(TAG, "City: "+ weather.getName());
                            Log.d(TAG, "description: " +  weather.getDescription());
                            Log.d(TAG, "humidity: " + weather.getHumidity()+ "%");
                            Log.d(TAG, "windspeed: " + weather.getWindSpeed());
                        }
                    }
                });
            }
        });
    }
}