package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1.Adapter.BottomSheetAdapter;
import com.example.duan1.Adapter.MenuAdapter;
import com.example.duan1.Adapter.NoticationAdapter;
import com.example.duan1.Adapter.onClickListenerAdapter;
import com.example.duan1.R;
import com.example.duan1.model.Notification;
import com.example.duan1.model.Order;
import com.example.duan1.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView imgBackPress;
    RecyclerView rcvNotification;
    List<Notification> listNoti;
    NoticationAdapter noticationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initUI();
        addListData();
        imgBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void addListData() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        String phonenumber = sharedPreferences.getString("phone","012345789");
        db.collection("NOTIFICATION")
                .whereEqualTo("PHONE",phonenumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listNoti = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("====", document.getId() + " => " + document.getData());
                                String name = document.getString("NOTIFICATION");
                                Notification sanPham = new Notification(name);
                                listNoti.add(sanPham);
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(NotificationActivity.this,DividerItemDecoration.VERTICAL);
                            rcvNotification.addItemDecoration(itemDecoration);
                            rcvNotification.setLayoutManager(layoutManager);
                            noticationAdapter = new NoticationAdapter(listNoti, NotificationActivity.this);
                            rcvNotification.setAdapter(noticationAdapter);
                        } else {
                            Log.w("===", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(NotificationActivity.this,MainActivity.class);
        startActivity(i);
        Animatoo.animateFade(NotificationActivity.this);
        super.onBackPressed();
    }

    private void initUI() {
        imgBackPress = findViewById(R.id.imgBackPress);
        rcvNotification = findViewById(R.id.rcvNotification);
    }
}