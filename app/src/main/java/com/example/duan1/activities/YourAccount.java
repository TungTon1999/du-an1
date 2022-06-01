package com.example.duan1.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.duan1.Adapter.MenuAdapter;
import com.example.duan1.R;
import com.example.duan1.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourAccount extends AppCompatActivity {
    FirebaseFirestore db;
    ImageView civAvt;
    EditText edtUpdateFirstName, edtUpdateLastName, edtUpdateEmail;
    TextView tvUpdatePhone, tvUpdateDOB;
    Button btnUpdate;
    String phonenumber;
    private Uri uri;
    boolean isLoad = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String avt = "";
    ConfirmOTP confirmOTP ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_account);
        edtUpdateEmail = findViewById(R.id.edtUpdateEmail);
        civAvt = findViewById(R.id.civAvt);
        edtUpdateFirstName = findViewById(R.id.edtUpdateFirstName);
        edtUpdateLastName = findViewById(R.id.edtUpdateLastName);
        tvUpdatePhone = findViewById(R.id.tvUpdatePhone);
        tvUpdateDOB = findViewById(R.id.tvUpdateDOB);
        btnUpdate = findViewById(R.id.btnUpdate);
        db = FirebaseFirestore.getInstance();
        loadData();
        loadImage();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
                if (isLoad == false){
                    StorageReference storageRef = storage.getReference();
                    StorageReference mountainImagesRef = storageRef.child("images/avt" + sharedPreferences.getString("firstname", "123") + sharedPreferences.getString("lastname", "123") + ".jpg");
                    mountainImagesRef.getName().equals(mountainImagesRef.getName());
                    mountainImagesRef.getPath().equals(mountainImagesRef.getPath());
                    mountainImagesRef.putFile(uri);
                }
                String id = sharedPreferences.getString("id", "012345789");
                String firstName = edtUpdateFirstName.getText().toString().toUpperCase();
                String lastName = edtUpdateLastName.getText().toString().toUpperCase();
                String Email = edtUpdateEmail.getText().toString().toUpperCase();
                DocumentReference washingtonRef = db.collection("USERS").document(id);
                washingtonRef
                        .update("FIRSTNAME", firstName, "LASTNAME", lastName, "EMAIL", Email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                                loadData();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error updating document", e);
                            }
                        });
            }
        });
        civAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
    }

    private void loadImage() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        StorageReference aaaa = storage.getReference().child("images/avt" + sharedPreferences.getString("firstname", "123") + sharedPreferences.getString("lastname", "123") + ".jpg");
        aaaa.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(civAvt);
                isLoad = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isLoad = false;
            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();

        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            avt = uri.toString();
            civAvt.setImageURI(uri);
            isLoad = false;
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        phonenumber = sharedPreferences.getString("phone","012345789");
        db.collection("USERS")
                .whereEqualTo("PHONENUMBER",phonenumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       DocumentReference user = db.collection("USERS").document(document.getId());
                                                       user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                               if (task.isSuccessful()) {
                                                                   DocumentSnapshot doc = task.getResult();
                                                                   edtUpdateFirstName.setText(doc.getString("FIRSTNAME"));
                                                                   edtUpdateLastName.setText(doc.getString("LASTNAME"));
                                                                   tvUpdateDOB.setText(doc.getString("DOB"));
                                                                   edtUpdateEmail.setText(doc.getString("EMAIL"));
                                                                   tvUpdatePhone.setText(doc.getString("PHONENUMBER"));

                                                               }
                                                           }
                                                       })
                                                               .addOnFailureListener(new OnFailureListener() {
                                                                   @Override
                                                                   public void onFailure(@NonNull Exception e) {
                                                                       Toast.makeText(YourAccount.this, "Đọc dữ liệu không thành công!", Toast.LENGTH_SHORT).show();
                                                                   }
                                                               });
                                                   }
                                               }
                                           }
                                       });
    }


}
