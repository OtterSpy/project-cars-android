package com.example.project_cars_android.models;

import java.util.List;

public class ParamDataHolder {
    private static ParamDataHolder instance;

    private List<? extends ParamDataset> dataList;

    private ParamDataHolder() {}

    public static ParamDataHolder getInstance() {
        if (instance == null) {
            instance = new ParamDataHolder();
        }
        return instance;
    }

    public static void setInstance(ParamDataHolder instance) {
        ParamDataHolder.instance = instance;
    }

    public List<? extends ParamDataset> getDataList() {
        return dataList;
    }

    public void setDataList(List<? extends ParamDataset> dataList) {
        this.dataList = dataList;
    }
}
