package com.example.project_cars_android.activityes.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_cars_android.R;
import com.example.project_cars_android.models.CarsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.RecyclerViewHolder> {

    private ArrayList<CarsModel> mDataset;

    static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView carImageView;
        TextView firstLineTextView, carNameTextView, priceTextView, priceUahTextView, carMileAgeTextView;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            carImageView = itemView.findViewById(R.id.carImageView);
            firstLineTextView = itemView.findViewById(R.id.firstLineTextView);
            carNameTextView = itemView.findViewById(R.id.carNameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            priceUahTextView = itemView.findViewById(R.id.priceUahTextView);
            carMileAgeTextView = itemView.findViewById(R.id.carMileageTextView);
        }
    }

    public SearchListAdapter(ArrayList<CarsModel> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public SearchListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
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
