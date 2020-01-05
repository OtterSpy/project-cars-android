package com.example.project_cars_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_cars_android.models.CarsModel;
import com.example.project_cars_android.networking.ApiManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.project_cars_android.SearchActivity.API_KEY;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "KEK";
    ArrayList<CarsModel> paramBodystyleArrayList;
    ArrayList<CarsModel> paramMarkArrayList;
    ArrayList<CarsModel> paramModelArrayList;
    ArrayList<CarsModel> paramStatesArrayList;
    ArrayList<CarsModel> paramCityArrayList;
    ArrayList<CarsModel> paramGearboxArrayList;
    ArrayList<CarsModel> paramFuelTypeArrayList;

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

        paramMarkId = 0;
        paramStateId = 0;

        currency = 1;

        fetchBodystyleIdList();
        fetchMarkIdList();
        fetchStatesIdList();
        fetchGearboxIdList();
        fetchFuelTypeIdList();

//        Log.d(TAG, "onCreate: " + priceFromEditText.getText());
    }
    private void fetchBodystyleIdList() {
        paramBodystyleArrayList = new ArrayList<>();
        final Call<ResponseBody> bodystyleCall = ApiManager.getInstance().searchBodystyle(API_KEY);
            bodystyleCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONArray bodystyleObject = new JSONArray(response.body().string());
                        for (int i = 0; i < bodystyleObject.length(); i++) {
                            JSONObject tmpBodystyleObject = bodystyleObject.getJSONObject(i);
                            CarsModel bodystyleParams = new CarsModel();
                            bodystyleParams.setParamBodystyleName(tmpBodystyleObject.getString("name"));
                            bodystyleParams.setBodystyleId(tmpBodystyleObject.getInt("value"));
                            paramBodystyleArrayList.add(bodystyleParams);
                        }
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
    private void fetchMarkIdList() {
        paramMarkArrayList = new ArrayList<>();
        Call<ResponseBody> markCall = ApiManager.getInstance().searchMarks(API_KEY);
            markCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONArray object = new JSONArray(response.body().string());
                        for (int i = 0; i < object.length(); i++) {
                            JSONObject tmpObj = object.getJSONObject(i);
                            CarsModel params = new CarsModel();
                            params.setParamMarkName(tmpObj.getString("name"));
                            params.setMarkId(tmpObj.getInt("value"));
                            paramMarkArrayList.add(params);
                        }
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
                            CarsModel modelParams = new CarsModel();
                            modelParams.setParamModelName(tmpObj.getString("name"));
                            modelParams.setModelId(tmpObj.getInt("value"));
                            paramModelArrayList.add(modelParams);
                        }
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
    }
    private void fetchStatesIdList() {
        paramStatesArrayList = new ArrayList<>();
        Call<ResponseBody> statesCall = ApiManager.getInstance().searchStates(API_KEY);
        statesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray statesObject = new JSONArray(response.body().string());
                    for (int i = 0; i < statesObject.length(); i++) {
                        JSONObject tmpStateObject = statesObject.getJSONObject(i);
                        CarsModel stateParams = new CarsModel();
                        stateParams.setParamStateName(tmpStateObject.getString("name"));
                        stateParams.setStateId(tmpStateObject.getInt("value"));
                        paramStatesArrayList.add(stateParams);
                    }
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
                        CarsModel cityParams = new CarsModel();
                        cityParams.setParamCityName(tmpCityObject.getString("name"));
                        cityParams.setCityId(tmpCityObject.getInt("value"));
                        paramCityArrayList.add(cityParams);
                    }
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
    private void fetchGearboxIdList() {
        paramGearboxArrayList = new ArrayList<>();
        Call<ResponseBody> gearboxesCall = ApiManager.getInstance().searchGearbox(API_KEY);
        gearboxesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray gearboxArray = new JSONArray(response.body().string());
                    for (int i = 0; i < gearboxArray.length(); i++) {
                        JSONObject gearboxObject = gearboxArray.getJSONObject(i);
                        CarsModel gearboxParams = new CarsModel();
                        gearboxParams.setParamGearboxName(gearboxObject.getString("name"));
                        gearboxParams.setGearboxId(gearboxObject.getInt("value"));
                        paramGearboxArrayList.add(gearboxParams);
                    }
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
    private void fetchFuelTypeIdList() {
        paramFuelTypeArrayList = new ArrayList<>();
        Call<ResponseBody> fuelTypeCall = ApiManager.getInstance().searchFuelType(API_KEY);
        fuelTypeCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray fuelTypeArray = new JSONArray(response.body().string());
                    for (int i = 0; i < fuelTypeArray.length(); i++) {
                        JSONObject fuelTypeObject = fuelTypeArray.getJSONObject(i);
                        CarsModel fuelTypeParams = new CarsModel();
                        fuelTypeParams.setParamFuelTypeName(fuelTypeObject.getString("name"));
                        fuelTypeParams.setFuelTypeId(fuelTypeObject.getInt("value"));
                        paramFuelTypeArrayList.add(fuelTypeParams);
                    }
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
    public void bodystyleClick(View view) {
        Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
        intent.putExtra("bodystyles", paramBodystyleArrayList);
        startActivityForResult(intent, 0);
    }
    public void marksClick(View view) {
            Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
            intent.putExtra("marks", paramMarkArrayList);
            startActivityForResult(intent, 1);
            Log.d(TAG, "marksClick: " + paramMarkArrayList);
    }
    public void modelsClick(View view) {
        if (paramMarkId == 1) {
            Toast.makeText(this, "Select Mark", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
            intent.putExtra("models", paramModelArrayList);
            startActivityForResult(intent, 2);
        }
    }
    public void statesClick(View view) {
        Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
        intent.putExtra("states", paramStatesArrayList);
        startActivityForResult(intent, 3);
        Log.d(TAG, "statesClick: " + paramStatesArrayList);
    }
    public void citiesClick(View view) {
        if (paramStateId == 0) {
            Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
            intent.putExtra("cities", paramCityArrayList);
            startActivityForResult(intent, 4);
        }
    }
    public void gearboxesClick(View view) {
        Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
        intent.putExtra("gearboxes", paramGearboxArrayList);
        startActivityForResult(intent, 5);
    }
    public void fuelTypeClick(View view) {
       Intent intent = new Intent(MainActivity.this, ParamListActivity.class);
       intent.putExtra("fuelTypes", paramFuelTypeArrayList);
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
                int modelId = data.getIntExtra("modelId", 0);
                paramModelId = modelId;
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
                gearboxButton.setText(fuelType);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchModelIdList();
        fetchCityIdList();
    }
}
