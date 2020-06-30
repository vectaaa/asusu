package com.example.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
private static int SPLASH_TIME_OUT = 5000;
    //Hooks

            ImageView icon;
            TextView appname;

    //Animations
Animation middleAnimation, bottomAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        //Animations
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks


        icon = findViewById(R.id.icon);

        appname = findViewById(R.id.appname);

        //setting animations
        icon.setAnimation(middleAnimation);
        appname.setAnimation(bottomAnimation);

        //Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class );
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

}
}
