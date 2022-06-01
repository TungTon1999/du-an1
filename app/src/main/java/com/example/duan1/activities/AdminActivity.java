package com.example.duan1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.duan1.R;

public class  AdminActivity extends AppCompatActivity {
    Button btnUser,btnBill,btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnBill = findViewById(R.id.btnBill);
        btnUser = findViewById(R.id.btnUser);
        btnMenu = findViewById(R.id.btnMenu);
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,BillActivity.class);
                startActivity(i);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,TableActivity.class);
                i.putExtra("admin",true);
                startActivity(i);
            }
        });
    }
}