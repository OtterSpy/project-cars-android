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

    public Call<ResponseBody> search(String apiKey, int pageNum) {
        return service.search(apiKey, pageNum);
    }
    public Call<ResponseBody> info(String apiKey, int autoId) {
        return service.info(apiKey, autoId);
    }
}
