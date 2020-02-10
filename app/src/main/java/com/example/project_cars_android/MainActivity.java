package com.example.project_cars_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.project_cars_android.models.Bodystyle;
import com.example.project_cars_android.models.City;
import com.example.project_cars_android.models.FuelType;
import com.example.project_cars_android.models.Gearbox;
import com.example.project_cars_android.models.Mark;
import com.example.project_cars_android.models.Model;
import com.example.project_cars_android.models.State;
import com.example.project_cars_android.networking.ApiManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.project_cars_android.SearchActivity.API_KEY;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "KEK";
    List<Bodystyle> paramBodystyleArrayList;
    List<Mark> paramMarkArrayList;
    List<Model> paramModelArrayList;
    List<State> paramStatesArrayList;
    List<City> paramCityArrayList;
    List<Gearbox> paramGearboxArrayList;
    List<FuelType> paramFuelTypeArrayList;
    JSONArray bodystyleObject;

    Integer paramBodystyleId;
    Integer paramMarkId;
    Integer paramModelId;
    Integer paramStateId;
    Integer paramCityId;
    Integer paramGearboxId;
    Integer paramFuelTypeId;
    Integer priceFrom;
    Integer priceTo;
    Integer yearFrom;
    Integer yearTo;
    Integer raceFrom;
    Integer raceTo;
    Float engineFrom;
    Float engineTo;
    Integer currency;

    LinearLayout contentLinearLayout;
    LinearLayout emptyStub;
    ProgressBar progressBar;

    Button bodystyleButton;
    Button markButton;
    Button modelButton;
    Button stateButton;
    Button cityButton;
    Button gearboxButton;
    Button fuelTypeButton;


    EditText priceFromEditText, priceToEditText;
    EditText yearFromEditText, yearToEditText;
    EditText raceFromEditText, raceToEditText;
    EditText engineFromEditText, engineToEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bodystyleButton = findViewById(R.id.buttonSelectBodystyle);
        markButton = findViewById(R.id.buttonSelectMark);
        modelButton = findViewById(R.id.buttonSelectModel);
        stateButton = findViewById(R.id.buttonSelectStates);
        cityButton = findViewById(R.id.buttonSelectCity);
        gearboxButton = findViewById(R.id.buttonSelectGearbox);
        fuelTypeButton = findViewById(R.id.buttonSelectFuelType);

        priceFromEditText = findViewById(R.id.priceFromEditText);
        priceToEditText = findViewById(R.id.priceToEditText);
        yearFromEditText = findViewById(R.id.yearFromEditText);
        yearToEditText = findViewById(R.id.yearToEditText);
        raceFromEditText = findViewById(R.id.raceFromEditText);
        raceToEditText = findViewById(R.id.raceToEditText);
        engineFromEditText = findViewById(R.id.engineFromEditText);
        engineToEditText = findViewById(R.id.engineToEditText);

        contentLinearLayout = findViewById(R.id.contentLinearLayout);
        progressBar = findViewById(R.id.progressBar);
        emptyStub = findViewById(R.id.emptyStab);

        emptyStub.findViewById(R.id.emptyStubButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();

                fetchBodystyleIdList();
                fetchMarkIdList();
                fetchStatesIdList();
                fetchGearboxIdList();
                fetchFuelTypeIdList();
            }
        });

        paramMarkId = 0;
        paramStateId = 0;

        currency = 1;

        showProgressBar();

        fetchBodystyleIdList();
        fetchMarkIdList();
        fetchStatesIdList();
        fetchGearboxIdList();
        fetchFuelTypeIdList();
    }
    private void fetchBodystyleIdList() {
        paramBodystyleArrayList = new ArrayList<>();
            final Call<ResponseBody> bodystyleCall = ApiManager.getInstance().searchBodystyle(API_KEY);
            bodystyleCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        showContent();
                        bodystyleObject = new JSONArray(response.body().string());
                        for (int i = 0; i < bodystyleObject.length(); i++) {
                            JSONObject tmpBodystyleObject = bodystyleObject.getJSONObject(i);
                            Bodystyle model = new Bodystyle();
                            model.setBodystyleId(tmpBodystyleObject.getInt("value"));
                            model.setParamBodystyleName(tmpBodystyleObject.getString("name"));
                            paramBodystyleArrayList.add(model);
                        }
                        Log.d(TAG, "fetchBodystyleIdList: " + paramBodystyleArrayList);
                        saveData(paramBodystyleArrayList, "bodystyle");
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
    private void fetchMarkIdList() {
        paramMarkArrayList = new ArrayList<>();
        final Call<ResponseBody> markCall = ApiManager.getInstance().searchMarks(API_KEY);
            markCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        showContent();
                        bodystyleObject = new JSONArray(response.body().string());
                        for (int i = 0; i < bodystyleObject.length(); i++) {
                            JSONObject tmpObj = bodystyleObject.getJSONObject(i);
                            Mark model = new Mark();
                            model.setParamMarkName(tmpObj.getString("name"));
                            model.setMarkId(tmpObj.getInt("value"));
                            paramMarkArrayList.add(model);
                        }
                        saveData(paramMarkArrayList, "mark");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
    }
    private void fetchModelIdList() {
        paramModelArrayList = new ArrayList<>();
        if (paramMarkId != 0) {
            Call<ResponseBody> modelsCall = ApiManager.getInstance().searchModels(paramMarkId, API_KEY);
            modelsCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONArray object = new JSONArray(response.body().string());
                        for (int i = 0; i < object.length(); i++) {
                            JSONObject tmpObj = object.getJSONObject(i);
                            Model model = new Model();
                            model.setParamModelName(tmpObj.getString("name"));
                            model.setModelId(tmpObj.getInt("value"));
                            paramModelArrayList.add(model);
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
    }
    private void fetchStatesIdList() {
        paramStatesArrayList = new ArrayList<>();
        Call<ResponseBody> statesCall = ApiManager.getInstance().searchStates(API_KEY);
        statesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    showContent();
                    JSONArray statesObject = new JSONArray(response.body().string());
                    for (int i = 0; i < statesObject.length(); i++) {
                        JSONObject tmpStateObject = statesObject.getJSONObject(i);
                        State model = new State();
                        model.setParamStateName(tmpStateObject.getString("name"));
                        model.setStateId(tmpStateObject.getInt("value"));
                        paramStatesArrayList.add(model);
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
    private void fetchCityIdList() {
        paramCityArrayList = new ArrayList<>();
        Call<ResponseBody> citiesCall = ApiManager.getInstance().searchCity(paramStateId, API_KEY);
        citiesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray cityObject = new JSONArray(response.body().string());
                    for (int i = 0; i < cityObject.length(); i++) {
                        JSONObject tmpCityObject = cityObject.getJSONObject(i);
                        City model = new City();
                        model.setParamCityName(tmpCityObject.getString("name"));
                        model.setCityId(tmpCityObject.getInt("value"));
                        paramCityArrayList.add(model);
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
    private void fetchGearboxIdList() {
        paramGearboxArrayList = new ArrayList<>();
        Call<ResponseBody> gearboxesCall = ApiManager.getInstance().searchGearbox(API_KEY);
        gearboxesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    showContent();
                    JSONArray gearboxArray = new JSONArray(response.body().string());
                    for (int i = 0; i < gearboxArray.length(); i++) {
                        JSONObject gearboxObject = gearboxArray.getJSONObject(i);
                        Gearbox model = new Gearbox();
                        model.setParamGearboxName(gearboxObject.getString("name"));
                        model.setGearboxId(gearboxObject.getInt("value"));
                        paramGearboxArrayList.add(model);
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
    private void fetchFuelTypeIdList() {
        paramFuelTypeArrayList = new ArrayList<>();
        Call<ResponseBody> fuelTypeCall = ApiManager.getInstance().searchFuelType(API_KEY);
        fuelTypeCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    showContent();
                    JSONArray fuelTypeArray = new JSONArray(response.body().string());
                    for (int i = 0; i < fuelTypeArray.length(); i++) {
                        JSONObject fuelTypeObject = fuelTypeArray.getJSONObject(i);
                        FuelType model = new FuelType();
                        model.setParamFuelTypeName(fuelTypeObject.getString("name"));
                        model.setFuelTypeId(fuelTypeObject.getInt("value"));
                        paramFuelTypeArrayList.add(model);
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
    public void bodystyleClick(View view) {
        Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
        intent.putExtra("bodystyles", (Serializable) paramBodystyleArrayList);
        startActivityForResult(intent, 0);
        Log.d(TAG, "bodystyleClick: " + paramBodystyleArrayList);
    }
    public void marksClick(View view) {
            Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
            intent.putExtra("marks", (Serializable) paramMarkArrayList);
            startActivityForResult(intent, 1);
            Log.d(TAG, "marksClick: " + paramMarkArrayList);
    }
    public void modelsClick(View view) {
        if (paramMarkId == 0) {
            Toast.makeText(this, "Select Mark", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
            intent.putExtra("models", (Serializable) paramModelArrayList);
            startActivityForResult(intent, 2);
        }
    }
    public void statesClick(View view) {
        Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
        intent.putExtra("states", (Serializable) paramStatesArrayList);
        startActivityForResult(intent, 3);
        Log.d(TAG, "statesClick: " + paramStatesArrayList);
    }
    public void citiesClick(View view) {
        if (paramStateId == 0) {
            Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
            intent.putExtra("cities", (Serializable) paramCityArrayList);
            startActivityForResult(intent, 4);
        }
    }
    public void gearboxesClick(View view) {
        Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
        intent.putExtra("gearboxes", (Serializable) paramGearboxArrayList);
        startActivityForResult(intent, 5);
    }
    public void fuelTypeClick(View view) {
       Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
       intent.putExtra("fuelTypes", (Serializable) paramFuelTypeArrayList);
       startActivityForResult(intent, 6);
    }
    public void searchClick(View view) {
        if (!priceFromEditText.getText().toString().equals("")) {
            priceFrom = Integer.parseInt(priceFromEditText.getText().toString());
        } else {
            priceFrom = 0;
        }
        if (!priceToEditText.getText().toString().equals("")) {
            priceTo = Integer.parseInt(priceToEditText.getText().toString());
        } else {
            priceTo = 0;
        }
        if (!yearFromEditText.getText().toString().equals("")) {
            yearFrom = Integer.parseInt(yearFromEditText.getText().toString());
        } else {
            yearFrom = 0;
        }
        if (!yearToEditText.getText().toString().equals("")) {
            yearTo = Integer.parseInt(yearToEditText.getText().toString());
        } else {
            yearTo = 0;
        }
        if (!raceFromEditText.getText().toString().equals("")) {
            raceFrom = Integer.parseInt(raceFromEditText.getText().toString());
        } else {
            raceFrom = 0;
        }
        if (!raceToEditText.getText().toString().equals("")) {
            raceTo = Integer.parseInt(raceToEditText.getText().toString());
        } else {
            raceTo = 0;
        }
        if (!engineFromEditText.getText().toString().equals("")) {
            engineFrom = Float.parseFloat(engineFromEditText.getText().toString());
        } else {
            engineFrom = 0f;
        }
        if (!engineToEditText.getText().toString().equals("")) {
            engineTo = Float.parseFloat(engineToEditText.getText().toString());
        } else {
            engineTo = 0f;
        }
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("bodystyleId", paramBodystyleId);
        intent.putExtra("markId", paramMarkId);
        intent.putExtra("modelId", paramModelId);
        intent.putExtra("stateId", paramStateId);
        intent.putExtra("cityId", paramCityId);
        intent.putExtra("gearboxId", paramGearboxId);
        intent.putExtra("fuelTypeId", paramFuelTypeId);
        intent.putExtra("priceFrom", priceFrom);
        intent.putExtra("priceTo", priceTo);
        intent.putExtra("yearFrom", yearFrom);
        intent.putExtra("yearTo", yearTo);
        intent.putExtra("raceFrom", raceFrom);
        intent.putExtra("raceTo", raceTo);
        intent.putExtra("engineFrom", engineFrom);
        intent.putExtra("engineTo", engineTo);
        intent.putExtra("currency", currency);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                paramBodystyleId = data.getIntExtra("bodystyleId", 0);
                String bodystyle = data.getStringExtra("bodystyle");
                bodystyleButton.setText(bodystyle);
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                paramMarkId = data.getIntExtra("markId", 0);
                String mark = data.getStringExtra("mark");
                markButton.setText(mark);
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                paramModelId = data.getIntExtra("modelId", 0);
                String model = data.getStringExtra("model");
                modelButton.setText(model);
            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                paramStateId = data.getIntExtra("stateId", 0);
                String state = data.getStringExtra("state");
                stateButton.setText(state);
            }
        } else if (requestCode == 4) {
            if (resultCode == RESULT_OK) {

                int cityId = data.getIntExtra("cityId", 0);
                paramCityId = cityId;
                String city = data.getStringExtra("city");
                cityButton.setText(city);
            }
        } else if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                int gearboxId = data.getIntExtra("gearboxId", 0);
                paramGearboxId = gearboxId;
                String gearbox = data.getStringExtra("gearbox");
                gearboxButton.setText(gearbox);
            }
        } else if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                int fuelTypeId = data.getIntExtra("fuelTypeId", 0);
                paramFuelTypeId = fuelTypeId;
                String fuelType = data.getStringExtra("fuelType");
                fuelTypeButton.setText(fuelType);
            }
        }
    }
    private void saveData(List list, String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("sPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(name, json);
        editor.apply();

        Log.d(TAG, "saveData: " + " " + name + " " + list);
    }
    private List loadData(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("sPref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(name, null);
        Type type = new TypeToken<List>(){}.getType();
        List list = gson.fromJson(json, type);
        if (list == null) {
            list = new ArrayList<>();
        }
        Log.d(TAG, "loadData: " + " " + name + " " + list);
        return list;
    }
    @Override
    protected void onResume() {
        super.onResume();
        fetchModelIdList();
        fetchCityIdList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (paramBodystyleArrayList.size() != 0) {
            saveData(paramBodystyleArrayList, "bodystyle");
        }
        if (paramMarkArrayList.size() != 0){
            saveData(paramMarkArrayList, "mark");
        }
        Log.d(TAG, "onDestroy: ");
    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        contentLinearLayout.setVisibility(View.GONE);
        emptyStub.setVisibility(View.GONE);
    }
    private void showContent() {
        progressBar.setVisibility(View.GONE);
        contentLinearLayout.setVisibility(View.VISIBLE);
        emptyStub.setVisibility(View.GONE);

    }
    private void showEmptyStub() {
        progressBar.setVisibility(View.GONE);
        contentLinearLayout.setVisibility(View.GONE);
        emptyStub.setVisibility(View.VISIBLE);
    }
}
