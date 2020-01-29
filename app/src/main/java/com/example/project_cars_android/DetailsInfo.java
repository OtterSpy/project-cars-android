package com.example.project_cars_android;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    TextView carNameInfoTextView, cityInfo, mileAgeInfo, carType, engineInfo, gearboxInfo, detailsInfo, priceInfo, priceUahInfo;
    Integer autoId;

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

        CarsModel data = (CarsModel) getIntent().getSerializableExtra("details");

        autoId = data.getId();
        Picasso.get().load(data.getPhotoData()).into(detailsInfoImageView);
        carNameInfoTextView.setText(data.getMarkName() + " " + data.getModelName());
        cityInfo.setText(data.getCity() + ", " + data.getRegionName() + " обл.");
        mileAgeInfo.setText(data.getMileage() + " пробег");
        carType.setText(data.getSubCategoryNameEng());
        engineInfo.setText(data.getEngine());
        gearboxInfo.setText(data.getGearbox());
        detailsInfo.setText(data.getDescription());
        priceInfo.setText(data.getPrice() + " $");
        priceUahInfo.setText(data.getPriceUah() + " грн.");

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

            }
        });
    }
}
