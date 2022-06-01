package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UpdateFood extends AppCompatActivity {

    EditText edt_foodname,edt_price;
    SanPham sanPham;
    TextView tv_type;
    FirebaseFirestore db;
    Button btn_update;
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        db = FirebaseFirestore.getInstance();
        sanPham = (SanPham) getIntent().getSerializableExtra("SanPham");
        edt_foodname=findViewById(R.id.edt_foodname);
        edt_price=findViewById(R.id.edt_price);
        btn_update=findViewById(R.id.btn_update);
        tv_type=findViewById(R.id.tv_type);

        edt_foodname.setText(sanPham.getName());
        edt_price.setText(sanPham.getPrice());
        tv_type.setText(sanPham.getType());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_foodname.getText().toString().trim();
                String price = edt_price.getText().toString().trim();
                String type = tv_type.getText().toString();
                if (!ValidationErrors(name, price)) {
                    SanPham s = new SanPham(name, price);

                    db.collection("USERS")
                            .whereEqualTo("TYPE", type)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            db.collection("MENU").document(document.getId())
                                                    .set(s)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(UpdateFood.this, "Update thành công", Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(UpdateFood.this, TableActivity.class);
                                                            startActivity(i);
                                                        }
                                                    });
                                        }

                                    } else {
                                        Log.w("===", "Error getting documents.", task.getException());

                                    }
                                }
                            });
                }
            }
        });
    }
    private boolean ValidationErrors(String name, String price){ return false;
    };
}