package com.example.duan1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;

public class RatingActivity extends AppCompatActivity {
    TextView tvFeeling;
    RatingBar ratingBar;
    Button btRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        tvFeeling=findViewById(R.id.tvFeeling);
        btRate=findViewById(R.id.btRate);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0)
                {
                    tvFeeling.setText("Bad");
                }
                else if (rating == 1)
                {
                    tvFeeling.setText("Need improvement");
                }
                else if (rating == 2 || rating == 3)
                {
                    tvFeeling.setText("Good");
                }
                else if (rating == 4)
                {
                    tvFeeling.setText("Excellent");
                }
                else
                {
                    tvFeeling.setText("Outstanding");
                }

            }
        });

        btRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RatingActivity.this,MenuActivity.class);
                Toast.makeText(RatingActivity.this, "Rating đã được gửi", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }

}