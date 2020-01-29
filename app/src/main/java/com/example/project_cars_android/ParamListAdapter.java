package com.example.project_cars_android;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_cars_android.models.CarsModel;

import java.util.ArrayList;

public class ParamListAdapter extends RecyclerView.Adapter<ParamListAdapter.ParamListViewHolder> {

    ArrayList<CarsModel> mDataset;
    public CarsModel params;

    boolean isBodystyle, isModel, isCity, isMark, isState, isGearbox, isFuelType;

    public static class ParamListViewHolder extends RecyclerView.ViewHolder {

        public TextView paramTextView;

        public ParamListViewHolder(View itemView) {
            super(itemView);

            paramTextView = itemView.findViewById(R.id.paramName);

        }
    }

    public ParamListAdapter(ArrayList<CarsModel> myDataset, boolean isBodystyle, boolean isMark, boolean isModel, boolean isState, boolean isCity, boolean isGearbox, boolean isFuelType) {
        mDataset = myDataset;
        this.isBodystyle = isBodystyle;
        this.isMark = isMark;
        this.isModel = isModel;
        this.isState = isState;
        this.isCity = isCity;
        this.isGearbox = isGearbox;
        this.isFuelType = isFuelType;
    }

    @Override
    public ParamListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.param_item, viewGroup, false);
        ParamListViewHolder paramListViewHolder = new ParamListViewHolder(view);
        return paramListViewHolder;
    }

    @Override
    public void onBindViewHolder(ParamListViewHolder holder, int position) {
        if (isHeader(position)) {
            holder.paramTextView.setText("Все");
            return;
        }
        params = mDataset.get(position - 1);
        if (isBodystyle) {
            holder.paramTextView.setText(params.getParamBodystyleName());
        } else if (isMark){
            holder.paramTextView.setText(params.getParamMarkName());
        } else if (isState) {
            holder.paramTextView.setText(params.getParamStateName());
        } else if (isModel) {
            holder.paramTextView.setText(params.getParamModelName());
        } else if (isCity) {
            holder.paramTextView.setText(params.getParamCityName());
        } else if (isGearbox) {
            holder.paramTextView.setText(params.getParamGearboxName());
        } else if (isFuelType) {
            holder.paramTextView.setText(params.getParamFuelTypeName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? 0 : 1;
    }

    public boolean isHeader (int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return mDataset.size() + 1;
    }
}
