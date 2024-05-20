package com.example.hydratrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.Objects;

public class DataForm extends AppCompatActivity {
    SeekBar seekBarAge,seekBarWeight,seekBarHeight;

    TextView textViewAge , textViewWeight , textViewHeight ;

    String timeValue,wakeUpTime,sleepTime;

    Button buttonSleep , buttonWakeUp , save;



    Toolbar toolbar;

    DatabaseReference userReference ;


    private UserDetails userDetails ;

    private int age;

    private FirebaseDatabase db;

    private FirebaseAuth mAuth ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_form);
        userDetails = new UserDetails();
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userDetails.setUid(user.getUid());
                    userDetails.setEmail(user.getEmail());
                    // Retrieve user data using the uid
                } else {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        save = findViewById(R.id.buttonSave);

        toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null ){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        seekBarAge = findViewById(R.id.seekBarAge);
        seekBarHeight = findViewById(R.id.seekBarHeight);
        seekBarWeight = findViewById(R.id.seekBarWeight);
        textViewAge = findViewById(R.id.ageInt);
        textViewHeight = findViewById(R.id.heightInt);
        textViewWeight = findViewById(R.id.weightInt);



        buttonSleep = findViewById(R.id.buttonSleep);
        buttonWakeUp = findViewById(R.id.buttonWakeUp);


        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewAge.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userDetails.setAge(seekBarAge.getProgress());
            }
        });

        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewHeight.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userDetails.setHeight(seekBarHeight.getProgress());

            }
        });

        seekBarWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewWeight.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userDetails.setWeight(seekBarWeight.getProgress());
            }
        });



        buttonSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSleep();

            }
        });

        buttonWakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogWake();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReference = FirebaseDatabase.getInstance().getReference("Users");
                userReference.push().setValue(userDetails);
                Toast.makeText(DataForm.this , "Saved Informations", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }


    private void openDialogWake(){
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String valeur ;


                valeur = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay , minute);

                buttonWakeUp.setText(valeur);

                userDetails.setWakeUpTime(valeur);
                wakeUpTime = valeur;

            }
        }, 7,0,true);

        dialog.show();
    }

    private void openDialogSleep(){
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String valeur ;


                valeur = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay , minute);

                buttonSleep.setText(valeur);

                userDetails.setSleepTime(valeur);
                sleepTime = valeur;

            }
        }, 7,0,true);

        dialog.show();
    }
}