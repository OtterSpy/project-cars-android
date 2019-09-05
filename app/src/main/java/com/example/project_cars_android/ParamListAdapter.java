package com.example.project_cars_android;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_cars_android.models.CarsModel;

import java.util.ArrayList;

public class ParamListAdapter extends RecyclerView.Adapter<ParamListAdapter.ParamListViewHolder> {

    ArrayList<CarsModel> mDataset;

    public static class ParamListViewHolder extends RecyclerView.ViewHolder {

        public TextView paramTextView;

        public ParamListViewHolder(View itemView) {
            super(itemView);

            paramTextView = itemView.findViewById(R.id.paramName);

        }
    }

    public ParamListAdapter(ArrayList<CarsModel> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ParamListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.param_item, viewGroup, false);
        ParamListViewHolder paramListViewHolder = new ParamListViewHolder(view);
        return paramListViewHolder;
    }

    @Override
    public void onBindViewHolder(ParamListViewHolder recyclerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
