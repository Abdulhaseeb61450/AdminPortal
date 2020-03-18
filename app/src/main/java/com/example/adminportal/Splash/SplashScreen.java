package com.example.adminportal.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.R;

public class SplashScreen extends AppCompatActivity {

    ImageView splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash = findViewById(R.id.splash);
        Animation myanim = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.transition);
        splash.startAnimation(myanim);
    }
}
