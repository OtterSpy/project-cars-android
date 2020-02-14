package com.example.project_cars_android.models;

import java.io.Serializable;

public class Model implements Serializable {

    private int modelId;
    private String paramModelName;

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getParamModelName() {
        return paramModelName;
    }

    public void setParamModelName(String paramModelName) {
        this.paramModelName = paramModelName;
    }
}
