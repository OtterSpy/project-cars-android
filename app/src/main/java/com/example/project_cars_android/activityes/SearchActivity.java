package com.example.project_cars_android.activityes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.project_cars_android.helpers.EndlessRecyclerViewScrollListener;
import com.example.project_cars_android.R;
import com.example.project_cars_android.helpers.RecyclerItemClickListener;
import com.example.project_cars_android.activityes.adapters.SearchListAdapter;
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
    int pageNum;

    RecyclerView searchRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    LinearLayout emptyStub;

    TextView noResultTextView;

    Integer bodystyleId, markId, modelId, stateId, cityId, gearboxId, fuelTypeId, priceFrom, priceTo, yearFrom, yearTo, raceFrom, raceTo, currency;
    Float engineFrom, engineTo;

    ArrayList<CarsModel> carInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        progressBar = findViewById(R.id.progressBar);
        emptyStub = findViewById(R.id.emptyStab);
        noResultTextView = findViewById(R.id.noResultTextView);
        carInfoList = new ArrayList<>();
        searchRecyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new SearchListAdapter(carInfoList);

        bodystyleId = getIntent().getIntExtra("bodystyleId", 0);
        markId = getIntent().getIntExtra("markId", 0);
        modelId = getIntent().getIntExtra("modelId", 0);
        stateId = getIntent().getIntExtra("stateId", 0);
        cityId = getIntent().getIntExtra("cityId", 0);
        gearboxId = getIntent().getIntExtra("gearboxId", 0);
        fuelTypeId = getIntent().getIntExtra("fuelTypeId", 0);
        priceFrom = getIntent().getIntExtra("priceFrom", 0);
        priceTo = getIntent().getIntExtra("priceTo", 0);
        yearFrom = getIntent().getIntExtra("yearFrom", 0);
        yearTo = getIntent().getIntExtra("yearTo", 0);
        raceFrom = getIntent().getIntExtra("raceFrom", 0);
        raceTo = getIntent().getIntExtra("raceTo", 0);
        engineFrom = getIntent().getFloatExtra("engineFrom", 0);
        engineTo = getIntent().getFloatExtra("engineTo", 0);
        currency = getIntent().getIntExtra("currency", 1);


        emptyStub.findViewById(R.id.emptyStubButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                fetchCarList(pageNum);
            }
        });

        showProgressBar();

        fetchCarList(pageNum);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNum = page;
                fetchCarList(page);
            }
        };

        Log.d(TAG, "onCreate: " + bodystyleId + " " + markId + " " + modelId + " " + stateId + " " + cityId + " " + gearboxId + " " + fuelTypeId + " " + priceFrom + " " + priceTo + " " + yearFrom + " " + yearTo + " " + raceFrom + " " + raceTo + " " + engineFrom + " " + engineTo + " " + currency);

        searchRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, searchRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (carInfoList != null) {
                    CarsModel carsModel = carInfoList.get(position);
                    Intent intent = new Intent(SearchActivity.this, DetailsInfo.class);
                    intent.putExtra("details", carsModel);
                    startActivity(intent);
                } else {
                    showEmptyStub();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        searchRecyclerView.setAdapter(mAdapter);
        searchRecyclerView.addOnScrollListener(scrollListener);
    }

    private void fetchCarList(int page) {
        final ArrayList<Integer> carList = new ArrayList<>();
        Call<ResponseBody> searchCall = ApiManager.getInstance().search(API_KEY, bodystyleId, markId, modelId, stateId, cityId, gearboxId, fuelTypeId, page, 10, priceFrom, priceTo, yearFrom, yearTo, raceFrom, raceTo, engineFrom, engineTo, currency);
        searchCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    showRecyclerView();
                    if (response.body() != null) {
                        JSONObject object = new JSONObject(response.body().string());
                        JSONObject results = object.getJSONObject("result").getJSONObject("search_result");
                        JSONArray ids = results.getJSONArray("ids");
                        if (results.getInt("count") == 0) {
                            showNoResultTextView();
                        } else {
                            for (int i = 0; i < ids.length(); i++) {
                                int dataObj = ids.getInt(i);
                                carList.add(dataObj);
                            }
                        }
                    } else {
                        showEmptyStub();
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
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            try {
                                if (response.body() != null) {
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
                                    carsModel.setAutoLink(object.getString("linkToView"));
                                    carsModel.setCount(imageData.getInt("count"));

                                    carInfoList.add(carsModel);
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    showEmptyStub();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            showEmptyStub();
                        }
                    });
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                showEmptyStub();
                Log.d(TAG, "onFailure:");
            }
        });

    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        searchRecyclerView.setVisibility(View.GONE);
        noResultTextView.setVisibility(View.GONE);
        emptyStub.setVisibility(View.GONE);
    }
    private void showRecyclerView() {
        progressBar.setVisibility(View.GONE);
        searchRecyclerView.setVisibility(View.VISIBLE);
        noResultTextView.setVisibility(View.GONE);
        emptyStub.setVisibility(View.GONE);

    }
    private void showNoResultTextView() {
        progressBar.setVisibility(View.GONE);
        searchRecyclerView.setVisibility(View.GONE);
        noResultTextView.setVisibility(View.VISIBLE);
        emptyStub.setVisibility(View.GONE);
    }
    private void showEmptyStub() {
        progressBar.setVisibility(View.GONE);
        searchRecyclerView.setVisibility(View.GONE);
        noResultTextView.setVisibility(View.GONE);
        emptyStub.setVisibility(View.VISIBLE);
    }
}