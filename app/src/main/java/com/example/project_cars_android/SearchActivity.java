package com.example.project_cars_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.project_cars_android.models.CarsModel;
import com.example.project_cars_android.networking.ApiManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    final static String API_KEY = "LV8tpree68pj0YpN5NfcXGFXkKcEWGUyxfJQYH5C";
    final static String TAG = "KEK";
    //    private EndlessRecyclerViewScrollListener scrollListener;
    int pageNum;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<CarsModel> carInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        carInfoList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstItem = layoutManager.find
//            }
//        });
        mAdapter = new RecyclerViewAdapter(carInfoList);

        fetchCarList(pageNum);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CarsModel carsModel = carInfoList.get(position);
                Intent intent = new Intent(SearchActivity.this, DetailsInfo.class);
                intent.putExtra("details", carsModel);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdapter);

//        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                pageNum = page;
//                fetchCarList(page);
//            }
//        };
//        recyclerView.addOnScrollListener(scrollListener);
    }

    private void fetchCarList(int page) {
        final ArrayList<Integer> carList = new ArrayList<>();
        Call<ResponseBody> searchCall = ApiManager.getInstance().search(API_KEY, page, 5);
        searchCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONObject results = object.getJSONObject("result").getJSONObject("search_result");
                    JSONArray ids = results.getJSONArray("ids");
                    for (int i = 0; i < ids.length(); i++) {
                        int dataObj = ids.getInt(i);
                        carList.add(dataObj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < carList.size() ; j++) {
                    int dataInfo = carList.get(j);
                    Call<ResponseBody> infoCall = ApiManager.getInstance().info(API_KEY, dataInfo);
                    infoCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                JSONObject object = new JSONObject(response.body().string());
                                JSONObject autoData = object.getJSONObject("autoData");
                                JSONObject imageData = object.getJSONObject("photoData");
                                JSONObject stateData = object.getJSONObject("stateData");

                                CarsModel carsModel = new CarsModel();

                                carsModel.setId(autoData.getInt("autoId"));
                                carsModel.setPhotoData(imageData.getString("seoLinkF"));
                                carsModel.setMarkName(object.getString("markName"));
                                carsModel.setModelName(object.getString("modelName"));
                                carsModel.setEngine(autoData.getString("fuelName"));
                                carsModel.setGearbox(autoData.getString("gearboxName"));
                                carsModel.setYear(autoData.getString("year"));
                                carsModel.setPrice(object.getString("USD"));
                                carsModel.setPriceUah(object.getString("UAH"));
                                carsModel.setMileage(autoData.getString("race"));
                                carsModel.setCity(object.getString("locationCityName"));
                                carsModel.setRegionName(stateData.getString("regionName"));
                                carsModel.setSubCategoryNameEng(autoData.getString("subCategoryNameEng"));
                                carsModel.setDescription(autoData.getString("description"));

                                carInfoList.add(carsModel);
                                mAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d(TAG, "onFailure:");
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure:");
            }
        });
        Log.d(TAG, "fetchCarList: " + page);
    }
}
