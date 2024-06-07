package com.example.woofwoofbitola.data.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.example.woofwoofbitola.data.model.DogReport;

@Dao
public interface DogReportDao {

    @Insert
    void insert(DogReport dogReport);

    @Query("SELECT * FROM dog_reports")
    List<DogReport> getAllDogReports();
}
