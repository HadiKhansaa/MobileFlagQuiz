package com.example.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button signIn, signUp;
    MyDatabaseHelper myDb;
    Intent intent, intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);
        intent = new Intent(this, SignUp.class);
        intent2 = new Intent(this, SetUp.class);


        myDb = new MyDatabaseHelper(this);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = myDb.checkInfo(username.getText().toString(), password.getText().toString());
                if(result) startActivity(intent2);
                else Toast.makeText(MainActivity.this, "Wrong Email or password", Toast.LENGTH_SHORT).show();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });




    }
}