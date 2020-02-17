package com.example.project_cars_android.activityes;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.example.project_cars_android.R;
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

import static com.example.project_cars_android.activityes.SearchActivity.API_KEY;

public class DetailsInfo extends AppCompatActivity {

    private static final String OPEN_RIA_BASE_URL = "https://auto.ria.com";

    private Integer autoId;
    private CarsModel data;
    private ProgressBar progressBar;
    private LinearLayout emptyStub;
    private ScrollView contentScrollView;
    private GridLayout footerGridLayout;

    private ArrayList<CarsModel> photoDataArray;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info);

        ImageView detailsInfoImageView = findViewById(R.id.detailsInfoImageView);
        TextView carNameInfoTextView = findViewById(R.id.carNameInfoTextView);
        TextView cityInfo = findViewById(R.id.cityInfoTextView);
        TextView mileAgeInfo = findViewById(R.id.mileAgeInfoTextView);
        TextView carType = findViewById(R.id.carTypeTextView);
        TextView engineInfo = findViewById(R.id.engineInfoTextView);
        TextView gearboxInfo = findViewById(R.id.gearboxInfoTextView);
        TextView detailsInfo = findViewById(R.id.detailsInfoTextView);
        TextView priceInfo = findViewById(R.id.priceInfoTextView);
        TextView priceUahInfo = findViewById(R.id.priceUahInfoTextView);
        Button openRiaButton = findViewById(R.id.openRiaButton);
        TextView photoCounter = findViewById(R.id.photoCountTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        progressBar = findViewById(R.id.progressBar);
        emptyStub = findViewById(R.id.emptyStab);
        contentScrollView = findViewById(R.id.contentScrollView);
        footerGridLayout = findViewById(R.id.footer);

        data = (CarsModel) getIntent().getSerializableExtra("details");

        String dateString = data.getAddDate();
        String[] publicationDate = dateString.split("-");
        autoId = data.getId();
        dateTextView.setText("Дата публикации: " + publicationDate[2].split(" ")[0] + "." + publicationDate[1] + "." + publicationDate[0] + " ");
        Picasso.get().load(data.getPhotoData()).into(detailsInfoImageView);
        carNameInfoTextView.setText(data.getMarkName() + " " + data.getModelName() + " | " + data.getYear() + " год.");
        cityInfo.setText(data.getCity() + ", " + data.getRegionName() + " обл.");
        mileAgeInfo.setText(data.getMileage());
        if (data.getSubCategoryNameEng().equals("sedan")){
            carType.setText(getResources().getString(R.string.sedan));
        } else if(data.getSubCategoryNameEng().equals("vnedorozhnik-krossover")) {
            carType.setText(getResources().getString(R.string.offroad));
        } else if (data.getSubCategoryNameEng().equals("miniven")) {
            carType.setText(getResources().getString(R.string.minivan));
        } else if (data.getSubCategoryNameEng().equals("khetchbek")) {
            carType.setText(getResources().getString(R.string.khetchbek));
        } else if (data.getSubCategoryNameEng().equals("universal")) {
            carType.setText(getResources().getString(R.string.universal));
        } else if (data.getSubCategoryNameEng().equals("kupe")) {
            carType.setText(getResources().getString(R.string.coupe));
        } else if (data.getSubCategoryNameEng().equals("legkovoj-furgon-do-1-5-t")) {
            carType.setText(getResources().getString(R.string.to_1_5t));
        } else if (data.getSubCategoryNameEng().equals("kabriolet")) {
            carType.setText(getResources().getString(R.string.cabriolet));
        } else if (data.getSubCategoryNameEng().equals("pikap")) {
            carType.setText(getResources().getString(R.string.pickup));
        } else if (data.getSubCategoryNameEng().equals("liftbek")) {
            carType.setText(getResources().getString(R.string.liftback));
        } else if (data.getSubCategoryNameEng().equals("limuzin")) {
            carType.setText(getResources().getString(R.string.limo));
        } else if (data.getSubCategoryNameEng().equals("drugoj")) {
            carType.setText(getResources().getString(R.string.other));
        } else if (data.getSubCategoryNameEng().equals("rodster")) {
            carType.setText(getResources().getString(R.string.roadster));
        }
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
        openRiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(OPEN_RIA_BASE_URL + data.getAutoLink())));
            }
        });
        openRiaButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) DetailsInfo.this.getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("", "https://auto.ria.com" + data.getAutoLink());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(DetailsInfo.this, "Ссылка скопирована", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        fetchPhotoData();
    }

    public void photoViewerOnClick(View view) {
        if (data.getCount() == 0) {
            Toast.makeText(this, "На данном объявлении нет фото", Toast.LENGTH_SHORT).show();
        } else {
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
    }

    private void fetchPhotoData() {
        photoDataArray = new ArrayList<>();
        final Call<ResponseBody> photoCall = ApiManager.getInstance().photoData(autoId, API_KEY);
        photoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    showContent();
                    if (response.body() != null) {
                        JSONObject photoData = new JSONObject(response.body().string()).getJSONObject("data").getJSONObject(String.valueOf(autoId));
                        photoDataArray.clear();
                        for (Iterator<String> iterator = photoData.keys(); iterator.hasNext();) {
                            String key = iterator.next();
                            JSONArray photoUrl = photoData.getJSONObject(key).getJSONArray("formats");
                            CarsModel photoDataModel = new CarsModel();
                            photoDataModel.setPhotoUrl(String.valueOf(photoUrl.get(1)));
                            photoDataArray.add(photoDataModel);
                        }
                        Log.d(SearchActivity.TAG, "onResponse1: " + photoDataArray);
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showEmptyStub();
            }
        });
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
