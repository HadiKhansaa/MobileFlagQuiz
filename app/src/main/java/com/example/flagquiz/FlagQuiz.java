package com.example.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class FlagQuiz extends AppCompatActivity {

    String[] imgList;
    Button btn1, btn2, btn3;
    ImageView img;
    AssetManager assets;
    String correctAnswer = "";
    TextView prompt;
    LinearLayout buttonsLayout;
    int questionCounter = 1;
    boolean answered = false;
    int correctAnswers = 0;
    static int nbOfOptions = 3;
    Intent intent;
    private Spinner spinner;
    private String[] ansList = new String[9];
    Button correctBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_quiz);

        Intent myIntent = getIntent();
        nbOfOptions = myIntent.getIntExtra("nbOfChoices",  0);
        assets = this.getAssets();
        intent = new Intent(this, EndScreen.class);


        img = findViewById(R.id.flag);
        buttonsLayout = findViewById(R.id.buttonsLayout);
        prompt = findViewById(R.id.question);


        try {
            imgList = assets.list("png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fillQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    public void changeButtonsNumber(){
        //buttonsLayout.addView();
    }


    public void fillQuestion() throws IOException {

        buttonsLayout.removeAllViews();
        String correctImg = "";
        int[] indexes = new int[9];
        int r = (int)(Math.random()*nbOfOptions);
        for(int i=0; i<nbOfOptions; i++)
        {
            int randomIndex = (int)(Math.random()*222);
            for(int j=0; j< indexes.length; j++)
            {
                if(randomIndex == indexes[j]){
                    randomIndex =  (int)(Math.random()*222);
                    j=-1;
                }
            }
            int len = imgList[randomIndex].split("-")[1].length();
            String imgName = imgList[randomIndex].split("-")[1].substring(0, len-4);
            LinearLayout.LayoutParams params;
            if(nbOfOptions == 9){
                params = new LinearLayout.LayoutParams(1000, 110);
                params.bottomMargin = 1;
            }
            else{
                params = new LinearLayout.LayoutParams(1000, 130);
                params.bottomMargin = 10;
                params.topMargin = 10;
            }

            Button button = new Button(this);
            button.setText(imgName);
            button.setId(i);

            button.setBackgroundColor(Color.rgb(230, 230, 250));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!answered) {
                        Button selectedButton = findViewById(v.getId());
                        if (selectedButton.getText().equals(correctAnswer)) {
                            correctAnswers++;
                            selectedButton.setBackgroundColor(Color.GREEN);
                        } else {
                            selectedButton.setBackgroundColor(Color.RED);
                            correctBtn.setBackgroundColor(Color.GREEN);
                        }
                        answered = true;
                    }
                }
            });
            ansList[i] = imgName;
            buttonsLayout.addView(button, params);

            if(i == r)
            {
                correctImg = imgList[randomIndex];
                correctAnswer = imgName;
                correctBtn = button;
            }
        }

        Drawable dImage1 = Drawable.createFromStream(assets.open("png/" + correctImg), correctImg);
        img.setImageDrawable(dImage1);

    }

//    public void checkAnswer(View v){
//
//        if(!answered) {
//            Button selectedButton = findViewById(v.getId());
//            if ((v.getId() == R.id.choice1 && correctAnswer == btn1.getText()) || (v.getId() == R.id.choice2 && correctAnswer == btn2.getText()) || (v.getId() == R.id.choice3 && correctAnswer == btn3.getText())) {
//                correctAnswers++;
//                selectedButton.setBackgroundColor(Color.GREEN);
//            } else {
//                selectedButton.setBackgroundColor(Color.RED);
//            }
//            answered = true;
//        }
//    }

    public void nextQuestion(View v) throws IOException {
        if(questionCounter == 10)
        {
            intent.putExtra("correctAnswers", correctAnswers);
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            db.updateRating(correctAnswers*10);
            startActivity(intent);
        }
        else if(answered) {
            questionCounter++;
            fillQuestion();
            prompt.setText("Question " + questionCounter + " of 10");
            answered = false;
        }
        else
        {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        }
    }
}