package com.example.hydratrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private MainActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //tester les suggestions
        this.activity=this;
        this.button = findViewById(R.id.testSuggestion);
        button.setText("Suggestion");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Popup popup =new Popup(activity);
                popup.setTitle("Suggestion of the day");
                popup.setSubTitle("Give me water please");
                popup.build();
            }
        });
    }
}