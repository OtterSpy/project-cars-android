package com.example.project_cars_android.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.project_cars_android.activityes.adapters.ParamListAdapter;
import com.example.project_cars_android.R;
import com.example.project_cars_android.helpers.RecyclerItemClickListener;
import com.example.project_cars_android.models.Bodystyle;
import com.example.project_cars_android.models.City;
import com.example.project_cars_android.models.FuelType;
import com.example.project_cars_android.models.Gearbox;
import com.example.project_cars_android.models.Mark;
import com.example.project_cars_android.models.Model;
import com.example.project_cars_android.models.State;

import java.util.ArrayList;
import java.util.List;

public class ParamListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewParamList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private List<Bodystyle> bodystyleList;
    private List<Mark> marksList;
    private List<Model> modelsList;
    private List<State> statesList;
    private List<City> citiesList;
    private List<Gearbox> gearboxesList;
    private List<FuelType> fuelTypeList;
    private List tmpArray;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_list);

        EditText searchParamEditText = findViewById(R.id.searchParamEditText);
        recyclerViewParamList = findViewById(R.id.recyclerViewParamList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewParamList.setLayoutManager(layoutManager);

        bodystyleList = (List<Bodystyle>) getIntent().getSerializableExtra("bodystyles");
        marksList = (List<Mark>) getIntent().getSerializableExtra("marks");
        modelsList = (List<Model>) getIntent().getSerializableExtra("models");
        statesList = (List<State>) getIntent().getSerializableExtra("states");
        citiesList = (List<City>) getIntent().getSerializableExtra("cities");
        gearboxesList = (List<Gearbox>) getIntent().getSerializableExtra("gearboxes");
        fuelTypeList = (List<FuelType>) getIntent().getSerializableExtra("fuelTypes");

        if (bodystyleList != null) {
            mAdapter = new ParamListAdapter(bodystyleList, true, false, false, false, false, false, false);
        } else if (marksList != null) {
            mAdapter = new ParamListAdapter(marksList, false, true, false, false, false, false, false);
        } else if (modelsList != null) {
            mAdapter = new ParamListAdapter(modelsList, false, false, true, false, false, false, false);
        } else if (statesList != null) {
            mAdapter = new ParamListAdapter(statesList, false, false, false, true, false, false, false);
        } else if (citiesList != null) {
            mAdapter = new ParamListAdapter(citiesList, false, false, false, false, true, false, false);
        } else if (gearboxesList != null) {
            mAdapter = new ParamListAdapter(gearboxesList, false, false, false, false, false, true, false);
        } else if (fuelTypeList != null) {
            mAdapter = new ParamListAdapter(fuelTypeList, false, false, false, false, false, false, true);
        }

        searchParamEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                bodystyleList = (List<Bodystyle>) getIntent().getSerializableExtra("bodystyles");
                marksList = (List<Mark>) getIntent().getSerializableExtra("marks");
                modelsList = (List<Model>) getIntent().getSerializableExtra("models");
                statesList = (List<State>) getIntent().getSerializableExtra("states");
                citiesList = (List<City>) getIntent().getSerializableExtra("cities");
                gearboxesList = (List<Gearbox>) getIntent().getSerializableExtra("gearboxes");
                fuelTypeList = (List<FuelType>) getIntent().getSerializableExtra("fuelTypes");
                Log.d("KEK", "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tmpArray = new ArrayList<>();
                if (bodystyleList != null) {
                    for (int i = 0; i < bodystyleList.size(); i++) {
                        Bodystyle bodystylesTmp = bodystyleList.get(i);
                        if (bodystylesTmp.getParamBodystyleName().toLowerCase().contains(s)) {
                            tmpArray.add(bodystylesTmp);
                        }
                    }
                    bodystyleList = tmpArray;
                }
                else if (marksList != null) {
                    for (int i = 0; i < marksList.size(); i++) {
                        Mark marksTmp = marksList.get(i);
                        if (marksTmp.getParamMarkName().toLowerCase().contains(s)) {
                            tmpArray.add(marksTmp);
                        }
                    }
                    marksList = tmpArray;
                } else if (modelsList != null) {
                    for (int i = 0; i < modelsList.size(); i++) {
                        Model modelsTmp = modelsList.get(i);
                        if (modelsTmp.getParamModelName().toLowerCase().contains(s)) {
                            tmpArray.add(modelsTmp);
                        }
                    }
                    modelsList = tmpArray;
                } else if (statesList != null) {
                    for (int i = 0; i < statesList.size(); i++) {
                        State statesTmp = statesList.get(i);
                        if (statesTmp.getParamStateName().toLowerCase().contains(s)) {
                            tmpArray.add(statesTmp);
                        }
                    }
                    statesList = tmpArray;
                } else if (citiesList != null) {
                    for (int i = 0; i < citiesList.size(); i++) {
                        City citiesTmp = citiesList.get(i);
                        if (citiesTmp.getParamCityName().toLowerCase().contains(s)) {
                            tmpArray.add(citiesTmp);
                        }
                    }
                    citiesList = tmpArray;
                } else if (gearboxesList != null) {
                    for (int i = 0; i < gearboxesList.size(); i++) {
                        Gearbox gearboxTmp = gearboxesList.get(i);
                        if (gearboxTmp.getParamGearboxName().toLowerCase().contains(s)) {
                            tmpArray.add(gearboxTmp);
                        }
                    }
                    gearboxesList = tmpArray;
                } else if (fuelTypeList != null) {
                    for (int i = 0; i < fuelTypeList.size(); i++) {
                        FuelType fyelTypeTmp = fuelTypeList.get(i);
                        if (fyelTypeTmp.getParamFuelTypeName().toLowerCase().contains(s)) {
                            tmpArray.add(fyelTypeTmp);
                        }
                    }
                    fuelTypeList = tmpArray;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("KEK", "afterTextChangedAll: ");
                if (bodystyleList != null) {
                    mAdapter = new ParamListAdapter(bodystyleList, true, false, false, false, false, false, false);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewParamList.setAdapter(mAdapter);
                }
                else  if (marksList != null) {
                    mAdapter = new ParamListAdapter(marksList, false, true, false, false, false, false, false);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewParamList.setAdapter(mAdapter);
                } else if (modelsList != null) {
                    mAdapter = new ParamListAdapter(modelsList, false, false, true, false, false, false, false);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewParamList.setAdapter(mAdapter);
                }
                if (statesList != null) {
                    mAdapter = new ParamListAdapter(statesList, false, false, false, true, false, false, false);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewParamList.setAdapter(mAdapter);
                }
                if (citiesList != null) {
                    mAdapter = new ParamListAdapter(citiesList, false, false, false, false, true, false, false);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewParamList.setAdapter(mAdapter);
                }
                if (gearboxesList != null) {
                    mAdapter = new ParamListAdapter(gearboxesList, false, false, false, false, false, true, false);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewParamList.setAdapter(mAdapter);
                }
                if (fuelTypeList != null) {
                    mAdapter = new ParamListAdapter(fuelTypeList, false, false, false, false, false, false, true);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewParamList.setAdapter(mAdapter);
                }
            }
        });

        recyclerViewParamList.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewParamList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (bodystyleList != null) {
                    if (position == 0) {
                        allParamData("bodystyle", "bodystyleId", bodystyleList);
                    } else {
                        Bodystyle bodystyles = bodystyleList.get(position - 1);
                        Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                        intent.putExtra("bodystyle", bodystyles.getParamBodystyleName());
                        intent.putExtra("bodystyleId", bodystyles.getBodystyleId());
                        setResult(RESULT_OK, intent);
                        bodystyleList.clear();
                        Log.d("KEK", "onItemClick: " + position);
                        ParamListActivity.this.finish();
                    }
                }
                else if (marksList != null) {
                    if (position == 0) {
                        allParamData("mark", "markId", marksList);
                    } else {
                        Mark marks = marksList.get(position - 1);
                        Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                        intent.putExtra("mark", marks.getParamMarkName());
                        intent.putExtra("markId", marks.getMarkId());
                        setResult(RESULT_OK, intent);
                        marksList.clear();
                        ParamListActivity.this.finish();
                    }
                } else if (modelsList != null) {
                    if (position == 0) {
                        allParamData("model", "modelId", modelsList);
                    } else {
                        Model models = modelsList.get(position - 1);
                        Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                        intent.putExtra("model", models.getParamModelName());
                        intent.putExtra("modelId", models.getModelId());
                        setResult(RESULT_OK, intent);
                        modelsList.clear();
                        ParamListActivity.this.finish();
                    }
                } else if (statesList != null) {
                    if (position == 0) {
                        allParamData("state", "stateId", statesList);
                    } else {
                        State states = statesList.get(position - 1);
                        Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                        intent.putExtra("state", states.getParamStateName());
                        intent.putExtra("stateId", states.getStateId());
                        setResult(RESULT_OK, intent);
                        statesList.clear();
                        ParamListActivity.this.finish();
                    }
                } else if (citiesList != null) {
                    if (position == 0) {
                        allParamData("city", "cityId", citiesList);
                    } else {
                        City cities = citiesList.get(position - 1);
                        Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                        intent.putExtra("city", cities.getParamCityName());
                        intent.putExtra("cityId", cities.getCityId());
                        setResult(RESULT_OK, intent);
                        citiesList.clear();
                        ParamListActivity.this.finish();
                    }
                } else if (gearboxesList != null) {
                    if (position == 0) {
                        allParamData("gearbox", "gearboxId", gearboxesList);
                    } else {
                        Gearbox gearboxes = gearboxesList.get(position - 1);
                        Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                        intent.putExtra("gearbox", gearboxes.getParamGearboxName());
                        intent.putExtra("gearboxId", gearboxes.getGearboxId());
                        setResult(RESULT_OK, intent);
                        gearboxesList.clear();
                        ParamListActivity.this.finish();
                    }
                } else if (fuelTypeList != null) {
                    if (position == 0) {
                        allParamData("fuelType", "fuelTypeId", fuelTypeList);
                    } else {
                        FuelType fuelTypes = fuelTypeList.get(position - 1);
                        Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
                        intent.putExtra("fuelType", fuelTypes.getParamFuelTypeName());
                        intent.putExtra("fuelTypeId", fuelTypes.getFuelTypeId());
                        setResult(RESULT_OK, intent);
                        fuelTypeList.clear();
                        ParamListActivity.this.finish();
                    }
                }
            }
            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        recyclerViewParamList.setAdapter(mAdapter);
    }
    public void allParamData (String name, String nameId, List arrayList) {
        Intent intent = new Intent(ParamListActivity.this, MainActivity.class);
        intent.putExtra(name, "Все");
        intent.putExtra(nameId, 0);
        setResult(RESULT_OK, intent);
        arrayList.clear();
        ParamListActivity.this.finish();
    }
}
