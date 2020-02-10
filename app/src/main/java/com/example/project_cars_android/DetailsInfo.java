package com.example.project_cars_android;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.project_cars_android.fragments.PhotoViewerFragment;
import com.example.project_cars_android.models.CarsModel;
import com.example.project_cars_android.networking.ApiManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.project_cars_android.SearchActivity.API_KEY;

public class DetailsInfo extends AppCompatActivity {

    ImageView detailsInfoImageView;
    TextView carNameInfoTextView, cityInfo, mileAgeInfo, carType, engineInfo, gearboxInfo, detailsInfo, priceInfo, priceUahInfo, photoCounter;
    Button openRiaButton;
    Integer autoId;
    CarsModel data;
    ProgressBar progressBar;
    LinearLayout emptyStub;
    ScrollView contentScrollView;
    GridLayout footerGridLayout;

    ArrayList<CarsModel> photoDataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info);

        detailsInfoImageView = findViewById(R.id.detailsInfoImageView);
        carNameInfoTextView = findViewById(R.id.carNameInfoTextView);
        cityInfo = findViewById(R.id.cityInfo);
        mileAgeInfo = findViewById(R.id.mileAgeInfo);
        carType = findViewById(R.id.carType);
        engineInfo = findViewById(R.id.engineInfo);
        gearboxInfo = findViewById(R.id.gearboxInfo);
        detailsInfo = findViewById(R.id.detailsInfo);
        priceInfo = findViewById(R.id.priceInfo);
        priceUahInfo = findViewById(R.id.priceUahInfo);
        openRiaButton = findViewById(R.id.openRiaButton);
        photoCounter = findViewById(R.id.photoCountTextView);
        progressBar = findViewById(R.id.progressBar);
        emptyStub = findViewById(R.id.emptyStab);
        contentScrollView = findViewById(R.id.contentScrollView);
        footerGridLayout = findViewById(R.id.footer);

        data = (CarsModel) getIntent().getSerializableExtra("details");

        autoId = data.getId();
        Picasso.get().load(data.getPhotoData()).into(detailsInfoImageView);
        carNameInfoTextView.setText(data.getMarkName() + " " + data.getModelName());
        cityInfo.setText(data.getCity() + ", " + data.getRegionName() + " обл.");
        mileAgeInfo.setText(data.getMileage() + " пробег");
        carType.setText(data.getSubCategoryNameEng());
        engineInfo.setText(data.getEngine());
        gearboxInfo.setText(data.getGearbox());
        if (data.getDescription().equals("")){
            detailsInfo.setText("Описание отсутствует.");
        } else {
            detailsInfo.setText(data.getDescription());
        }
        priceInfo.setText(data.getPrice() + " $");
        priceUahInfo.setText(data.getPriceUah() + " грн.");
        photoCounter.setText(data.getCount() + " фото");

        emptyStub.findViewById(R.id.emptyStubButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                fetchPhotoData();
            }
        });

        fetchPhotoData();
    }

    public void photoViewerOnClick(View view) {
        Fragment photoViewerFragment = null;
        try {
            photoViewerFragment = PhotoViewerFragment.class.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable("photoData", photoDataArray);
            photoViewerFragment.setArguments(bundle);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.add(R.id.detailsInfoContainer, photoViewerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void fetchPhotoData() {
        photoDataArray = new ArrayList<>();
        final Call<ResponseBody> photoCall = ApiManager.getInstance().photoData(autoId, API_KEY);
        photoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    showContent();
                    JSONObject photoData = new JSONObject(response.body().string()).getJSONObject("data").getJSONObject(String.valueOf(autoId));
                    for (Iterator<String> iterator = photoData.keys(); iterator.hasNext();) {
                        String key = iterator.next();
                        JSONArray photoUrl = photoData.getJSONObject(key).getJSONArray("formats");
                        CarsModel photoDataModel = new CarsModel();
                        photoDataModel.setPhotoUrl(String.valueOf(photoUrl.get(1)));
                        photoDataArray.add(photoDataModel);
                    }
                    Log.d(SearchActivity.TAG, "onResponse1: " + photoDataArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showEmptyStub();
            }
        });
    }

    public void openRiaOnClick(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://auto.ria.com" + data.getAutoLink())));
    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        contentScrollView.setVisibility(View.GONE);
        footerGridLayout.setVisibility(View.GONE);
        emptyStub.setVisibility(View.GONE);
    }
    private void showContent() {
        progressBar.setVisibility(View.GONE);
        contentScrollView.setVisibility(View.VISIBLE);
        footerGridLayout.setVisibility(View.VISIBLE);
        emptyStub.setVisibility(View.GONE);

    }
    private void showEmptyStub() {
        progressBar.setVisibility(View.GONE);
        contentScrollView.setVisibility(View.GONE);
        footerGridLayout.setVisibility(View.GONE);
        emptyStub.setVisibility(View.VISIBLE);
    }
}
