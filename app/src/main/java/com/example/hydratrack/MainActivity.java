package com.example.hydratrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private MainActivity activity;
    FirebaseAuth auth ;
    FirebaseUser user ;

    TextView textView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance() ;
        user = auth.getCurrentUser() ;
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class) ;
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }



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
        WaterReminderScheduler.scheduleHourlyReminder(this);
    }
}
