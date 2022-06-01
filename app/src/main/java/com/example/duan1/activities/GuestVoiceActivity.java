package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GuestVoiceActivity extends AppCompatActivity {
    FirebaseFirestore db ;
    TextView tvHotline,tvEmail,tvFeedback;
    LinearLayout linearLayoutFeedback;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText edPhanHoi;
    int show = 1 ;
    Button btPhanHoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_voice);
        db=FirebaseFirestore.getInstance();
        edPhanHoi = findViewById(R.id.edPhanHoi);
        tvHotline = findViewById(R.id.tvHotline);
        tvEmail = findViewById(R.id.tvEmail);
        tvFeedback = findViewById(R.id.tvFeedback);
        linearLayoutFeedback = findViewById(R.id.linearLayoutFeedback);
        btPhanHoi=findViewById(R.id.btPhanHoi);
        String phanHoi = edPhanHoi.getText().toString();
        tvHotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone ="19000000";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);
            }
        });
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"nhatlam452@gmail.com"});
                try {
                    startActivity(Intent.createChooser(i,"Send mail ....."));
                }catch (Exception e){
                    Toast.makeText(GuestVoiceActivity.this, e+"", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show == 1){
                    linearLayoutFeedback.setVisibility(View.VISIBLE);
                    show = 2;
                }else {
                    linearLayoutFeedback.setVisibility(View.INVISIBLE);
                    show = 1;
                }

                btPhanHoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> feedback = new HashMap<>();
                        feedback.put("PHANHOI", phanHoi);
                        db.collection("FEEDBACK")
                                .add(feedback)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(">>>>>>>>>>>>>>>", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(GuestVoiceActivity.this, "Thank for your Feedback", Toast.LENGTH_SHORT).show();
                                        Intent i=new Intent(GuestVoiceActivity.this,MenuActivity.class);
                                        startActivity(i);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("<<<<<<<<<<<<<<<<<<<<<<", "Error adding document", e);
                                        Toast.makeText(GuestVoiceActivity.this, "Gửi FeeddBack thất bai", Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GuestVoiceActivity.this, MenuActivity.class));
        Animatoo.animateSlideDown(GuestVoiceActivity.this);

    }

}



