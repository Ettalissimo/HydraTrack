package com.example.water_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1002;

    public static int unreadNotificationsCount = 0;


    private Button button;
    private MainActivity activity;

    private int currentSuggestionIndex = 1; // Start from "sugg1"
    // Firebase
    private DatabaseReference suggestionsRef;
    private ValueEventListener suggestionsListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView timeTextView = findViewById(R.id.textView);
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        timeTextView.setText(currentTime);
        WaterReminderScheduler.scheduleHourlyReminder(this);
        Button addButton = findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddWaterActivity.class);
                startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= 33) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
       }
        }


        //Suggestions
        activity = this;

        // Initialize Firebase Database Reference
        suggestionsRef = FirebaseDatabase.getInstance().getReference("Suggestions");

        // Button to display suggestion
        button = findViewById(R.id.suggestion);
        button.setText("Suggestion");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextSuggestion();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Attach Firebase Listener to retrieve suggestions
        suggestionsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Handle data change
                if (dataSnapshot.exists()) {
                    // Display the first suggestion when data changes
                    displayNextSuggestion();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(activity, "Failed to load suggestions", Toast.LENGTH_SHORT).show();
            }
        };
        suggestionsRef.addValueEventListener(suggestionsListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Remove Firebase Listener when the activity is stopped
        if (suggestionsListener != null) {
            suggestionsRef.removeEventListener(suggestionsListener);
        }
    }

    private void displayNextSuggestion() {
        // Fetch and display the next suggestion from Firebase
        String currentSuggestionKey = "sugg" + currentSuggestionIndex;
        suggestionsRef.child(currentSuggestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nextSuggestion = dataSnapshot.child("text").getValue(String.class);
                    if (nextSuggestion != null) {
                        Popup popup = new Popup(activity);
                        popup.setTitle("SUGGESTION OF THE DAY");
                        popup.setSubTitle(nextSuggestion);
                        popup.getButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Oui!", Toast.LENGTH_SHORT).show();
                                popup.dismiss();
                            }
                        });
                        popup.build();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(activity, "Failed to load suggestions", Toast.LENGTH_SHORT).show();
            }

        });
        // Increment the current suggestion index for the next click
        currentSuggestionIndex++;
        if (currentSuggestionIndex > 3) { // Define MAX_SUGGESTIONS as needed
            currentSuggestionIndex = 1; // Reset index if it exceeds the maximum
        }
    }




}