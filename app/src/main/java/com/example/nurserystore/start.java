package com.example.nurserystore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;

public class start extends AppCompatActivity {
    private Button loginButton, joinButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        loginButton = (Button) findViewById(R.id.loginbutton);
        joinButton = (Button) findViewById(R.id.joinbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(start.this, login.class);
                startActivity(intent);
            }

        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this, register.class);
                startActivity(intent);
            }
        });
    }
}