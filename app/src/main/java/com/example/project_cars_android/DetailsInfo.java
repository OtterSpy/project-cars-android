package com.example.project_cars_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_cars_android.models.CarsModel;
import com.squareup.picasso.Picasso;

public class DetailsInfo extends AppCompatActivity {

    ImageView detailsInfoImageView;
    TextView carNameInfoTextView, cityInfo, mileAgeInfo, carType, engineInfo, gearboxInfo, detailsInfo, priceInfo, priceUahInfo;

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
    }
}
