package com.example.water_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        button = findViewById(R.id.button1);
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
        String containerText = containerEditText.getText().toString();
        if (containerText.isEmpty()) {
            Toast.makeText(this, "Please enter an container name", Toast.LENGTH_SHORT).show();
            return;
        }
        String volumeText = volumeEditText.getText().toString();
        int volumeInt = Integer.parseInt(volumeText);
        if (volumeText.isEmpty()) {
            Toast.makeText(this, "Please enter a volume", Toast.LENGTH_SHORT).show();
            return;
        }
        Container container = new Container(containerText,volumeInt);

        containerReference.push().setValue(container)
                .addOnSuccessListener( aVoid ->{
                            Toast.makeText(AddContainerActivity.this,"Data inserted succesfully",Toast.LENGTH_SHORT).show();
                        })
                .addOnFailureListener(e->Toast.makeText(AddContainerActivity.this,"Failed to insert data",Toast.LENGTH_SHORT).show());
    }
}