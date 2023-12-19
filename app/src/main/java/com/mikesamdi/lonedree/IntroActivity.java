package com.mikesamdi.lonedree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class IntroActivity extends AppCompatActivity {


    LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

    lottie = findViewById(R.id.lottie);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }, 2300);

    }
}