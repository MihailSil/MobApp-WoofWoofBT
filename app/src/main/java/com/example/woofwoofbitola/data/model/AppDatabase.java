package com.example.woofwoofbitola.data.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DogReport.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // Define abstract methods for DAOs
    public abstract DogReportDao dogReportDao();

    // Singleton pattern to ensure only one instance of database is created
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "woof_woof_bitola_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
