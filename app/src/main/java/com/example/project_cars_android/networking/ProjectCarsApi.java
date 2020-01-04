package com.example.project_cars_android.networking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectCarsApi {

    @GET("auto/categories/1/bodystyles")
    Call<ResponseBody> searchBodystyles(@Query("api_key") String apiKey);

    @GET("auto/categories/1/marks")
    Call<ResponseBody> searchMarks(@Query("api_key") String apiKey);

    @GET("auto/categories/1/marks/{markId}/models")
    Call<ResponseBody> searchModels(@Path("markId") int markId, @Query("api_key") String apiKey);

    @GET("auto/states")
    Call<ResponseBody> searchStates(@Query("api_key") String apiKey);

    @GET("auto/states/{stateId}/cities")
    Call<ResponseBody> searchCity(@Path("stateId") int stateId, @Query("api_key") String apiKey);

    @GET("auto/search")
    Call<ResponseBody> search(@Query("api_key") String apiKey, @Query("bodystyle[0]") int bodystyleId, @Query("marka_id[0]") int markId, @Query("model_id[0]") int modelId, @Query("state[0]") int stateId, @Query("city[0]") int cityId, @Query("page") int pageNum, @Query("countpage") int countPage, @Query("price_ot") int priceFrom, @Query("price_do") int priceTo, @Query("s_yers[0]") int yearFrom, @Query("po_yers[0]") int yearTo, @Query("raceFrom") int raceFrom , @Query("raceTo") int raceTo, @Query("currency") int currency);

    @GET("auto/info")
    Call<ResponseBody> info(@Query("api_key") String apiKey, @Query("auto_id") int autoId);

}
