package com.example.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

public class SetUp extends AppCompatActivity {

    private Spinner choiceSpinner;
    private Button button, profile;
    private Intent intent, intent2;
    private int nbOfOptions = 0;

    public SetUp() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        choiceSpinner = findViewById(R.id.choiceSpinner);
        button = findViewById(R.id.startQuiz);
        profile = findViewById(R.id.profile);

        choiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String choice = (String) adapterView.getItemAtPosition(i);

                if(choice.equals("3 choices")){
                    nbOfOptions = 3;
                }
                else if (choice.equals("6 choices")){
                    nbOfOptions = 6;
                }
                else{
                    nbOfOptions = 9;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent2 = new Intent(SetUp.this, Profile.class);
                startActivity(intent2);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SetUp.this, FlagQuiz.class);
                intent.putExtra("nbOfChoices", nbOfOptions);
                startActivity(intent);
            }
        });

    }
}