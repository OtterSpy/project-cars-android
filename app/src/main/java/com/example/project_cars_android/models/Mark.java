package com.example.project_cars_android.models;

import java.io.Serializable;

public class Mark implements Serializable {

    private int markId;
    private String paramMarkName;

    public int getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }

    public String getParamMarkName() {
        return paramMarkName;
    }

    public void setParamMarkName(String paramMarkName) {
        this.paramMarkName = paramMarkName;
    }

}
