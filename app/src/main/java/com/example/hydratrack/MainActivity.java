package com.example.hydratrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private MainActivity activity;
    private String[] suggestions = {
            "L'important dans la vie, ce n'est point le triomphe, mais le combat; l'essentiel ce n'est pas d'avoir vaincu, mais de s'être bien battu.",
            "Phrase 1", "Phrase 2", "Phrase 3",
            "Another Phrase 1", "Another Phrase 2", "Another Phrase 3"

    };
    private int currentSuggestionIndex = 0;

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
                popup.setTitle("SUGGESTION OF THE DAY");
                String nextSuggestion = getNextSuggestion();
                popup.setSubTitle(nextSuggestion);
                popup.getButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Oui!",Toast.LENGTH_SHORT).show();
                        popup.dismiss();
                    }
                });

                popup.build();
            }
        });
    }

    private String getNextSuggestion(){
        String nextSuggestion = suggestions[currentSuggestionIndex];
        if (currentSuggestionIndex == suggestions.length - 1) {
            currentSuggestionIndex = 0;
        }else {
            currentSuggestionIndex = currentSuggestionIndex + 1;
        }
        return nextSuggestion;
    }
}