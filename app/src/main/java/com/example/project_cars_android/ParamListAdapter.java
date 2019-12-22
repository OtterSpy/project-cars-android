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

    boolean isModel, isCity, isMark, isState;

    public static class ParamListViewHolder extends RecyclerView.ViewHolder {

        public TextView paramTextView;

        public ParamListViewHolder(View itemView) {
            super(itemView);

            paramTextView = itemView.findViewById(R.id.paramName);

        }
    }

    public ParamListAdapter(ArrayList<CarsModel> myDataset, boolean isMark, boolean isModel, boolean isState, boolean isCity) {
        mDataset = myDataset;
        this.isMark = isMark;
        this.isModel = isModel;
        this.isState = isState;
        this.isCity = isCity;
    }

    @NonNull
    @Override
    public ParamListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.param_item, viewGroup, false);
        ParamListViewHolder paramListViewHolder = new ParamListViewHolder(view);
        return paramListViewHolder;
    }

    @Override
    public void onBindViewHolder(ParamListViewHolder holder, int position) {
        CarsModel params = mDataset.get(position);
        if (isMark){
            holder.paramTextView.setText(params.getParamMarkName());
        } else if (isState) {
            holder.paramTextView.setText(params.getParamStateName());
        } else if (isModel) {
            holder.paramTextView.setText(params.getParamModelName());
        } else if (isCity) {
            holder.paramTextView.setText(params.getParamCityName());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
