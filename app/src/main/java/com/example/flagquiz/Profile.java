package com.example.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView username, rating;
    MyDatabaseHelper db;
    Button logout, delete;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new MyDatabaseHelper(this);
        username = findViewById(R.id.username);
        rating = findViewById(R.id.rating);
        logout = findViewById(R.id.logOut);
        delete = findViewById(R.id.delete);

        Cursor result = db.getUserInfo(MainActivity.ID);
        result.moveToNext();

        username.setText(result.getString(result.getColumnIndex("username")));
        rating.setText(result.getString(result.getColumnIndex("rating")));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, MainActivity.class);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper db = new MyDatabaseHelper(Profile.this);
                db.deleteUser(MainActivity.ID);
                Intent i = new Intent(Profile.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}