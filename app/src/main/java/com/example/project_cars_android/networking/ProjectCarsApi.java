package com.example.project_cars_android.networking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProjectCarsApi {

    @GET("auto/categories/1/marks")
    Call<ResponseBody> searchMarks(@Query("api_key") String apiKey);

    @GET("auto/search")
    Call<ResponseBody> search(@Query("api_key") String apiKey, @Query("page") int pageNum, @Query("countpage") int countPage);

    @GET("auto/info")
    Call<ResponseBody> info(@Query("api_key") String apiKey, @Query("auto_id") int autoId);

}
