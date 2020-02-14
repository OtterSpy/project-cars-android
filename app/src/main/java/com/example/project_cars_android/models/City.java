package com.example.project_cars_android.models;

import java.io.Serializable;

public class City implements Serializable {

    private int cityId;
    private String paramCityName;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getParamCityName() {
        return paramCityName;
    }

    public void setParamCityName(String paramCityName) {
        this.paramCityName = paramCityName;
    }
}
