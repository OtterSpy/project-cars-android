package com.example.project_cars_android.models;

import java.io.Serializable;

public class State implements Serializable {

    private int stateId;
    private String paramStateName;

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getParamStateName() {
        return paramStateName;
    }

    public void setParamStateName(String paramStateName) {
        this.paramStateName = paramStateName;
    }
}
