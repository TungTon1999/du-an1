package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class GiftActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CardView cardViewCT,cardViewVVT;
    MediaPlayer mp;
    RelativeLayout rl_store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        mp = MediaPlayer.create(GiftActivity.this, R.raw.nhac4);
        mp.setLooping(true);
        mp.setVolume(1000, 1000);
        mp.start();

        initUI();
        initBottomNavigtionTable();
        LoadSetting();
        cardViewCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GiftActivity.this, CaoThangActivity.class));
                Animatoo.animateSlideUp(GiftActivity.this);
            }
        });
        cardViewVVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GiftActivity.this, VoVanTanActivity.class));
                Animatoo.animateSlideUp(GiftActivity.this);
            }
        });
    }


    private void initUI() {
        cardViewCT = findViewById(R.id.cardViewCaoThang);
        cardViewVVT = findViewById(R.id.cardViewVoVanTan);
        bottomNavigationView = findViewById(R.id.bottomNavigationGift);
        rl_store = findViewById(R.id.rl_store);
    }
    public void LoadSetting(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean chk_night = sp.getBoolean("Night", false);
        if (chk_night) {
            rl_store.setBackgroundColor(Color.parseColor("#222222"));
        } else {
            rl_store.setBackgroundColor(Color.parseColor("#BDBBBB"));
        }

    }

    private void initBottomNavigtionTable() {
        bottomNavigationView.setSelectedItemId(R.id.menu_gift);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_store:
                        startActivity(new Intent(GiftActivity.this, TableActivity.class));
                        Animatoo.animateFade(GiftActivity.this);  //fire the zoom animation
                        break;
                    case R.id.menu_menu:
                        startActivity(new Intent(GiftActivity.this, MenuActivity.class));
                        Animatoo.animateFade(GiftActivity.this);  //fire the zoom animation
                        break;
                    case R.id.menu_home:
                        startActivity(new Intent(GiftActivity.this, MainActivity.class));
                        Animatoo.animateFade(GiftActivity.this);  //fire the zoom animation
                        break;
                    case R.id.menu_gift:

                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        mp.release();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GiftActivity.this, MainActivity.class));
        Animatoo.animateFade(GiftActivity.this);  //fire the zoom animation
        finish();
    }

    @Override
    protected void onResume() {
        LoadSetting();
        super.onResume();
    }
}