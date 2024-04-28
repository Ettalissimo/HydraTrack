package com.example.hydratrack;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class NotificationTestActivity extends AppCompatActivity {

    private EditText waterIntakeInput;
    private Button addButton;
    private WaterIntakeManager waterIntakeManager;

    private static final int PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);

        waterIntakeManager = new WaterIntakeManager(this, 2000); // Replace 2000 with the desired water objective

        waterIntakeInput = findViewById(R.id.water_intake_input);
        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get water intake value from EditText
                String waterIntakeStr = waterIntakeInput.getText().toString();
                if (!waterIntakeStr.isEmpty()) {
                    double waterIntake = Double.parseDouble(waterIntakeStr);
                    // Call method to record water consumption in WaterIntakeManager
                    waterIntakeManager.recordWaterConsumption(waterIntake);
                    // Display a message or perform any other actions as needed
                }
            }
        });

        // Request permission if not granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform necessary actions
            } else {
                // Permission denied, handle accordingly
            }
        }
    }
}
