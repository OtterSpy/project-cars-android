package com.example.project_cars_android.models;

import java.io.Serializable;

import io.realm.RealmObject;

public class Mark extends RealmObject implements Serializable {

    int markId;
    String paramMarkName;

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
