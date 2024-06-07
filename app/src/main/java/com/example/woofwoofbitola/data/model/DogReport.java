package com.example.woofwoofbitola.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dog_reports")
public class DogReport {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String location;
    private String description;
    private String photoPath;

    public DogReport(String location, String description, String photoPath) {
        this.location = location;
        this.description = description;
        this.photoPath = photoPath;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
}
