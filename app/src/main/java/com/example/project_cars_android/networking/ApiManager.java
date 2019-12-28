package com.example.project_cars_android.networking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ApiManager {

    final static String BASE_URL = "https://developers.ria.com/";

    private static ProjectCarsApi service;

    private static ApiManager instance;

    private ApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(ProjectCarsApi.class);
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public Call<ResponseBody> search(String apiKey, int bodystyleId, int markId, int modelId, int stateId, int cityId, int pageNum, int countPage, int priceFrom, int priceTo, int currency) {
        return service.search(apiKey, bodystyleId, markId, modelId, stateId, cityId, pageNum, countPage, priceFrom, priceTo, currency);
    }
    public Call<ResponseBody> info(String apiKey, int autoId) {
        return service.info(apiKey, autoId);
    }
    public Call<ResponseBody> searchBodystyle(String apiKey) {
        return service.searchBodystyles(apiKey);
    }
    public Call<ResponseBody> searchMarks(String apiKey) {
        return service.searchMarks(apiKey);
    }
    public Call<ResponseBody> searchStates(String apiKey) {
        return service.searchStates(apiKey);
    }
    public Call<ResponseBody> searchCity(int stateId, String apiKey) {
        return service.searchCity(stateId, apiKey);
    }
    public Call<ResponseBody> searchModels(int markId, String apiKey) {
        return service.searchModels(markId, apiKey);
    }
}
