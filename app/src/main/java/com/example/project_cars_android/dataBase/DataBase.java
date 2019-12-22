package com.example.project_cars_android.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.project_cars_android.MainActivity.TAG;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "projectCarsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate DataBase");
        db.execSQL("create table markTable ("
                + "id integer,"
                + "mark text" + ");" );
        db.execSQL("create table modelTable ("
                + "id integer,"
                + "mark text" + ");" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
