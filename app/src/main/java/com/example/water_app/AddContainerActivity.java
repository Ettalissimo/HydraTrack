package com.example.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContainerActivity extends AppCompatActivity {

    private DatabaseReference containerReference;
    Button button;
    EditText containerEditText;
    EditText volumeEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_container);
        DatabaseReference containerReference = FirebaseDatabase.getInstance().getReference("Containers");
        button = findViewById(R.id.button);
        containerEditText = findViewById(R.id.nameInput);
        volumeEditText = findViewById(R.id.numberInput);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });


    }

    private void insertData(){

    }
}