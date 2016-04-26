package com.epicodus.myweather;

/**
 * Created by Guest on 4/25/16.
 */
public class Weather {
    private int mDayTemperature;
    private String mCityName;
    private String mCountry;
    private int mCityId;

    public Weather(String cityName, int cityId, String country, int dayTemp) {
        this.mDayTemperature = dayTemp;
        this.mCityName = cityName;
        this.mCountry = country;
        this.mCityId = cityId;

    }

    public int getTemp() {

        return mDayTemperature;
    }

    public String getName() {
        return mCityName;
    }

    public String getCountry() {
        return mCountry;
    }
}
