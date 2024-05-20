package com.example.water_app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddWaterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner containerSpinner;
    private DatabaseReference containerReference;
    private DatabaseReference amountReference;
    private List<Container> containerList;
    private ArrayAdapter<String> adapter;
    private Button button;
    private EditText amountEditText;
    private WaterConsumption water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water);

        containerSpinner = findViewById(R.id.container_spinner);
        amountEditText = findViewById(R.id.numberInput);
        button = findViewById(R.id.button);
        containerList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        containerSpinner.setAdapter(adapter);
        containerSpinner.setOnItemSelectedListener(this);

        containerReference = FirebaseDatabase.getInstance().getReference("Containers");
        amountReference = FirebaseDatabase.getInstance().getReference("WaterConsumption");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        fetchContainers();
    }

    private void insertData() {
        String amountText = amountEditText.getText().toString();
        if (amountText.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedPosition = containerSpinner.getSelectedItemPosition();
        if (selectedPosition == AdapterView.INVALID_POSITION) {
            Toast.makeText(this, "Please select a container", Toast.LENGTH_SHORT).show();
            return;
        }

        Container selectedContainer = containerList.get(selectedPosition);
        String currentDate = getCurrentDate();

        water = new WaterConsumption(amount, selectedContainer.name, currentDate);

        amountReference.push().setValue(water)
                .addOnSuccessListener(aVoid -> Toast.makeText(AddWaterActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(AddWaterActivity.this, "Failed to insert data", Toast.LENGTH_SHORT).show());
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private void fetchContainers() {
        containerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                containerList.clear();
                List<String> containerNames = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Container container = snapshot.getValue(Container.class);
                    if (container != null) {
                        containerList.add(container);
                        containerNames.add(container.name);
                    }
                }
                adapter.clear();
                adapter.addAll(containerNames);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddWaterActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
