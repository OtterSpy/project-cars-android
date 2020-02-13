package com.example.project_cars_android;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_cars_android.models.Bodystyle;
import com.example.project_cars_android.models.City;
import com.example.project_cars_android.models.FuelType;
import com.example.project_cars_android.models.Gearbox;
import com.example.project_cars_android.models.Mark;
import com.example.project_cars_android.models.Model;
import com.example.project_cars_android.models.ParamDataset;
import com.example.project_cars_android.models.State;

import java.util.List;

public class ParamListAdapter extends RecyclerView.Adapter<ParamListAdapter.ParamListViewHolder> {

    private List mDataset;
    private static final int HEADER = 0;
    private static final int CONTENT = 1;


    private boolean isBodystyle, isModel, isCity, isMark, isState, isGearbox, isFuelType;

    public static class ParamListViewHolder extends RecyclerView.ViewHolder {

        public TextView paramTextView;

        public ParamListViewHolder(View itemView) {
            super(itemView);

            paramTextView = itemView.findViewById(R.id.paramName);

        }
    }

    public ParamListAdapter(List myDataset, boolean isBodystyle, boolean isMark, boolean isModel, boolean isState, boolean isCity, boolean isGearbox, boolean isFuelType) {
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
        if (isBodystyle) {
            holder.paramTextView.setText(((Bodystyle) mDataset.get(position - 1)).getParamBodystyleName());
        } else if (isMark){
            holder.paramTextView.setText(((Mark) mDataset.get(position - 1)).getParamMarkName());
        } else if (isState) {
            State param = (State) mDataset.get(position - 1);
            holder.paramTextView.setText(param.getParamStateName());
        } else if (isModel) {
            Model param = (Model) mDataset.get(position - 1);
            holder.paramTextView.setText(param.getParamModelName());
        } else if (isCity) {
            City param = (City) mDataset.get(position - 1);
            holder.paramTextView.setText(param.getParamCityName());
        } else if (isGearbox) {
            Gearbox param = (Gearbox) mDataset.get(position - 1);
            holder.paramTextView.setText(param.getParamGearboxName());
        } else if (isFuelType) {
            FuelType param = (FuelType) mDataset.get(position - 1);
            holder.paramTextView.setText(param.getParamFuelTypeName());
        }
    }
    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? HEADER : CONTENT;
    }

    private boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return mDataset.size() + 1;
    }
}
