package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ReciptActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipt);
        initUI();
        initBottomNavigtionTable();
    }

    private void initUI() {
        bottomNavigationView = findViewById(R.id.bottomNavigationReceipt);
    }

    private void initBottomNavigtionTable() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_store:
                        startActivity(new Intent(ReciptActivity.this, TableActivity.class));
                        Animatoo.animateFade(ReciptActivity.this);  //fire the zoom animation
                        break;
                    case R.id.menu_menu:
                        startActivity(new Intent(ReciptActivity.this, MenuActivity.class));
                        Animatoo.animateFade(ReciptActivity.this);  //fire the zoom animation
                        break;
                    case R.id.menu_home:
                        startActivity(new Intent(ReciptActivity.this, MainActivity.class));
                        Animatoo.animateFade(ReciptActivity.this);  //fire the zoom animation
                        break;
                    case R.id.menu_gift:
                        startActivity(new Intent(ReciptActivity.this, GiftActivity.class));
                        Animatoo.animateFade(ReciptActivity.this);  //fire the zoom animation
                        break;

                }
                return true;
            }
        });
    }




}