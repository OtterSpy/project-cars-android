package com.example.project_cars_android.models;

import java.io.Serializable;

public class Gearbox implements Serializable {

    private int gearboxId;
    private String paramGearboxName;

    public int getGearboxId() {
        return gearboxId;
    }

    public void setGearboxId(int gearboxId) {
        this.gearboxId = gearboxId;
    }

    public String getParamGearboxName() {
        return paramGearboxName;
    }

    public void setParamGearboxName(String paramGearboxName) {
        this.paramGearboxName = paramGearboxName;
    }
}
