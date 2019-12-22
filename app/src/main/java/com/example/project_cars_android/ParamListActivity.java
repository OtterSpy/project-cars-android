package com.example.project_cars_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.project_cars_android.models.CarsModel;

import java.util.ArrayList;
public class ParamListActivity extends AppCompatActivity {

    RecyclerView recyclerViewParamList;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<CarsModel> marksList;
    ArrayList<CarsModel> modelsList;
    ArrayList<CarsModel> statesList;
    ArrayList<CarsModel> citiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_list);

        recyclerViewParamList = findViewById(R.id.recyclerViewParamList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewParamList.setLayoutManager(layoutManager);

        marksList = (ArrayList<CarsModel>) getIntent().getSerializableExtra("marks");
        modelsList = (ArrayList<CarsModel>) getIntent().getSerializableExtra("models");
        statesList = (ArrayList<CarsModel>) getIntent().getSerializableExtra("states");
        citiesList = (ArrayList<CarsModel>) getIntent().getSerializableExtra("cities");

        if (marksList != null) {
            mAdapter = new ParamListAdapter(marksList, true, false, false, false);
        }
        if (modelsList != null) {
            mAdapter = new ParamListAdapter(modelsList, false, true, false, false);
        }
        if (statesList != null) {
            mAdapter = new ParamListAdapter(statesList, false, false, true, false);
        }
        if (citiesList != null) {
            mAdapter = new ParamListAdapter(citiesList, false, false, false, true);
        }
        recyclerViewParamList.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewParamList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (marksList != null){
                    CarsModel marks = marksList.get(position);
                    Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                    intent.putExtra("mark", marks.getParamMarkName());
                    intent.putExtra("markId", marks.getMarkId());
                    setResult(RESULT_OK, intent);
                    ParamListActivity.this.finish();
                } else if (modelsList != null) {
                    CarsModel models = modelsList.get(position);
                    Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                    intent.putExtra("model", models.getParamModelName());
                    intent.putExtra("modelId", models.getModelId());
                    setResult(RESULT_OK, intent);
                    ParamListActivity.this.finish();
                } else if (statesList != null) {
                    CarsModel states = statesList.get(position);
                    Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                    intent.putExtra("state", states.getParamStateName());
                    intent.putExtra("stateId", states.getStateId());
                    setResult(RESULT_OK, intent);
                    ParamListActivity.this.finish();
                } else if (citiesList != null) {
                    CarsModel cities = citiesList.get(position);
                    Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                    intent.putExtra("city", cities.getParamCityName());
                    intent.putExtra("cityId", cities.getCityId());
                    setResult(RESULT_OK, intent);
                    ParamListActivity.this.finish();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        recyclerViewParamList.setAdapter(mAdapter);
    }
}
