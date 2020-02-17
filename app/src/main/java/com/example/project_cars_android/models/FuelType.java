package com.example.project_cars_android.models;

import java.io.Serializable;


public class FuelType implements Serializable {

    private int fuelTypeId;
    private String paramFuelTypeName;

    public int getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(int fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public String getParamFuelTypeName() {
        return paramFuelTypeName;
    }

    public void setParamFuelTypeName(String paramFuelTypeName) {
        this.paramFuelTypeName = paramFuelTypeName;
    }
}
