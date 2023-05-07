package com.example.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        result = findViewById(R.id.result);

        result.setText("Your final result is " + correctAnswers + " out of 10 correct answers ("+ ((double)correctAnswers/10)*100 + "%)");

    }

    public void resetGame(View v)
    {
        if(v.getId() == R.id.reset) {
            Intent i = new Intent(this, FlagQuiz.class);
            i.putExtra("nbOfChoices", FlagQuiz.nbOfOptions);
            startActivity(i);
        }else if(v.getId() == R.id.profile){
            Intent i = new Intent(this, Profile.class);
            i.putExtra("nbOfChoices", FlagQuiz.nbOfOptions);
            startActivity(i);
        }else{
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("nbOfChoices", FlagQuiz.nbOfOptions);
            startActivity(i);
        }
    }

}