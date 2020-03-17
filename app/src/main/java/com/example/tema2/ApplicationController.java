package com.example.tema2;

import android.app.Application;

import androidx.room.Room;

public class ApplicationController extends Application {
    private static ApplicationController mInstance;

    private static AppDatabase mAppDatabase;

    public static ApplicationController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        mAppDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "marks.db").build();
    }
    public static AppDatabase getAppDatabase(){
        return mAppDatabase;
    }
}
