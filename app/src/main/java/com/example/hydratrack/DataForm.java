package com.example.hydratrack;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

public class DataForm extends AppCompatActivity {
    SeekBar seekBarAge;

    TextView textView ;

    String timeValue;

    Button buttonSleep , buttonWakeUp ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_form);

        seekBarAge = findViewById(R.id.seekBarAge);
        textView = findViewById(R.id.ageInt);

        buttonSleep = findViewById(R.id.buttonSleep);
        buttonWakeUp = findViewById(R.id.buttonWakeUp);




        SeekBar.OnSeekBarChangeListener object = null;
        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                buttonSleep.setText(timeValue);
            }
        });

        buttonWakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                buttonWakeUp.setText(timeValue);
            }
        });

    }

    private void openDialog(){
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String valeur ;

                valeur = String.valueOf(hourOfDay)+":"+String.valueOf(minute);

                timeValue = valeur ;

            }
        }, 7,0,true);
    }
}