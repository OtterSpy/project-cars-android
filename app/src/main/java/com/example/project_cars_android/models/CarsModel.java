package com.example.project_cars_android.models;

import java.io.Serializable;



public class CarsModel implements Serializable {

    private int id;
    private int count;
    private int adCounter;

    private String photoData;

    private String photoUrl;
    private String engine;
    private String gearbox;
    private String year;
    private String markName;
    private String modelName;
    private String price;
    private String priceUah;
    private String mileage;
    private String city;
    private String regionName;
    private String subCategoryNameEng;
    private String description;
    private String autoLink;

    public int getAdCounter() {
        return adCounter;
    }

    public void setAdCounter(int adCounter) {
        this.adCounter = adCounter;
    }

    public String getAutoLink() {
        return autoLink;
    }

    public void setAutoLink(String autoLink) {
        this.autoLink = autoLink;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSubCategoryNameEng() {
        return subCategoryNameEng;
    }

    public void setSubCategoryNameEng(String subCategoryNameEng) {
        this.subCategoryNameEng = subCategoryNameEng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoData() {
        return photoData;
    }

    public void setPhotoData(String photoData) {
        this.photoData = photoData;
    }
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceUah() {
        return priceUah;
    }

    public void setPriceUah(String priceUah) {
        this.priceUah = priceUah;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
