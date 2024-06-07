package com.example.woofwoofbitola;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.woofwoofbitola.data.model.AppDatabase;
import com.example.woofwoofbitola.data.model.DogReport;

import java.io.FileOutputStream;
import java.io.IOException;

public class ActivityReportUnchippedDogs extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    private EditText editTextLocation;
    private EditText editTextDescription;
    private ImageView imageViewPhoto;
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_unchipped_dogs);

        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        Button buttonCapturePhoto = findViewById(R.id.buttonCapturePhoto);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonCapturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ActivityReportUnchippedDogs.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivityReportUnchippedDogs.this,
                            new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    openCamera();
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            imageViewPhoto.setImageBitmap(photo);
        }
    }

    private void saveData() {
        String location = editTextLocation.getText().toString();
        String description = editTextDescription.getText().toString();

        if (location.isEmpty() || description.isEmpty() || photo == null) {
            Toast.makeText(this, "Please fill all fields and capture a photo", Toast.LENGTH_SHORT).show();
            return;
        }

        String photoFileName = "unchipped_dog_photo_" + System.currentTimeMillis() + ".jpg";

        try (FileOutputStream fos = openFileOutput(photoFileName, MODE_PRIVATE)) {
            photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            DogReport dogReport = new DogReport(location, description, photoFileName);
            AppDatabase db = AppDatabase.getInstance(this);
            db.dogReportDao().insert(dogReport);

            Toast.makeText(this, "Data saved locally", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
