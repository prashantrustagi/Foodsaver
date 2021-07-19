package com.cdac.foodsaver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {
    private static  int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             Intent intent = new Intent(splashScreen.this,welcomeScreen.class);
             startActivity(intent);
             finish();
         }

         },SPLASH_TIME_OUT);


}
}