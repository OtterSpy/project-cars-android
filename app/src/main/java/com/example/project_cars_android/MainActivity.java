package com.example.project_cars_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    ArrayList<CarsModel> paramArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paramArrayList = new ArrayList<>();

        fetchMarkIdList();
    }
    private void fetchMarkIdList() {
        Call<ResponseBody> markCall = ApiManager.getInstance().searchMarks(API_KEY);
        markCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray object = new JSONArray(response.body().string());
                    CarsModel params = new CarsModel();
                    for (int i = 0; i < object.length(); i++) {
                        JSONObject tmpObj = object.getJSONObject(i);
                        params.setMarkId(tmpObj.getInt("value"));
                        params.setParamMarkName(tmpObj.getString("name"));
                        paramArrayList.add(params);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse: " + paramArrayList);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void searchClick(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }
}
