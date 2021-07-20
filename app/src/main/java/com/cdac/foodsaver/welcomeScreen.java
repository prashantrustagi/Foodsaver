package com.cdac.foodsaver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class welcomeScreen extends AppCompatActivity {
    Button btn, welcomebtn_ngo;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);
        btn = findViewById(R.id.welcomescreen_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcomeScreen.this, signup.class);
                startActivity(intent);
            }
        });

      /*  text = findViewById(R.id.welcomescreen_txt);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcomeScreen.this, Login.class);
                startActivity(intent);
            }
        });*/
        welcomebtn_ngo =findViewById(R.id.welcomescreen_btnngo);
        welcomebtn_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t =  new Intent(welcomeScreen.this, ngosignup.class);
                startActivity(t);
            }
        });
    }
}