package com.example.project_cars_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_cars_android.models.CarsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<CarsModel> mDataset;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView carImageView;
        public TextView firstLineTextView, carNameTextView, priceTextView, priceUahTextView, carMileAgeTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            carImageView = itemView.findViewById(R.id.carImageView);
            firstLineTextView = itemView.findViewById(R.id.firstLineTextView);
            carNameTextView = itemView.findViewById(R.id.carNameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            priceUahTextView = itemView.findViewById(R.id.priceUahTextView);
            carMileAgeTextView = itemView.findViewById(R.id.carMileageTextView);
        }
    }

    public RecyclerViewAdapter (ArrayList<CarsModel> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_holder, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        CarsModel carID = mDataset.get(position);
        holder.firstLineTextView.setText(carID.getEngine() + " | " + carID.getGearbox() + " | " + carID.getYear() + " г.");
        holder.carNameTextView.setText(carID.getMarkName() + " " + carID.getModelName());
        holder.priceTextView.setText(carID.getPrice() + "$ /");
        holder.priceUahTextView.setText(carID.getPriceUah() + " UAH");
        holder.carMileAgeTextView.setText(carID.getMileage() + " | г. " + carID.getCity());
        Picasso.get().load(carID.getPhotoData()).into(holder.carImageView);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
