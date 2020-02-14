package com.example.project_cars_android.models;

import java.io.Serializable;

public class Bodystyle implements Serializable {

    private int bodystyleId;

    private String paramBodystyleName;

    public String getParamBodystyleName() {
        return paramBodystyleName;
    }

    public void setParamBodystyleName(String paramBodystyleName) {
        this.paramBodystyleName = paramBodystyleName;
    }

    public int getBodystyleId() {
        return bodystyleId;
    }

    public void setBodystyleId(int bodystyleId) {
        this.bodystyleId = bodystyleId;
    }
}
