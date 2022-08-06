package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SplashActivity extends AppCompatActivity {
    ImageView logo, background;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        background = findViewById(R.id.img_Background);
        lottieAnimationView = findViewById(R.id.lav);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (remember == true) {
//
//                } else {
//                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                }
//                Animatoo.animateZoom(SplashActivity.this);  //fire the zoom animation
//                finish();
            }
        }, 3000);
    }

}