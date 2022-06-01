package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.Adapter.MenuAdapter;
import com.example.duan1.R;
import com.example.duan1.model.SanPham;
import com.example.duan1.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ConfirmOTP extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Boolean check = false;
    Button btConfirm;
    String phonenumber;
    String Id;
    FirebaseAuth mAuth;
    EditText edtFirstName, edtLastName, edtEmail, edtDOB,edtOTP1,edtOTP2,edtOTP3,edtOTP4,edtOTP5,edtOTP6;
    TextView edtPhoneNumber,tvPhoneNumber;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otp);
        phonenumber = getIntent().getStringExtra("mobile");
        btConfirm = findViewById(R.id.btConfirm);
        mAuth = FirebaseAuth.getInstance();
        edtOTP1 = findViewById(R.id.edtOTP1);
        edtOTP2 = findViewById(R.id.edtOTP2);
        edtOTP3 = findViewById(R.id.edtOTP3);
        edtOTP4 = findViewById(R.id.edtOTP4);
        edtOTP5 = findViewById(R.id.edtOTP5);
        edtOTP6 = findViewById(R.id.edtOTP6);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        ischeckinfo(phonenumber);
        OtpConfirm();
        tvPhoneNumber.setText(phonenumber);
        edtOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    edtOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    edtOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    edtOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    edtOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    edtOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTP = edtOTP1.getText().toString() + edtOTP2.getText().toString() + edtOTP3.getText().toString() + edtOTP4.getText().toString() +edtOTP5.getText().toString() +edtOTP6.getText().toString();
                if (OTP.isEmpty())
                    Toast.makeText(getApplicationContext(), "Blank Field can not be processed", Toast.LENGTH_LONG).show();
                else if (OTP.length() != 6)
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Id, OTP);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
    }

    private void OtpConfirm() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Id = s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            writeLoginStatus(true);

                            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
                            boolean isLogin = sharedPreferences.getBoolean("isLogin",false);
                            if (isLogin == true){
                                startActivity(new Intent(ConfirmOTP.this, MainActivity.class));
                            }else if (isLogin == false){
                                openDiadlog();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Signin Code Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void ischeckinfo(String phonenumber) {

         db.collection("USERS")
                 .whereEqualTo("PHONENUMBER",phonenumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("====", document.getId() + " => " + document.getData());
                                String firstname = document.getString("FIRSTNAME");
                                String lastname = document.getString("LASTNAME");
                                String dob = document.getString("DOB");
                                String phone = document.getString("PHONENUMBER");
                                String email = document.getString("EMAIL");
                                String avt = document.getString("AVT");
                                check = document.getBoolean("ISLOGIN");
                                String id = document.getId();
                                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLogin", check);
                                editor.putString("avt",avt);
                                editor.putString("firstname",firstname);
                                editor.putString("lastname",lastname);
                                editor.putString("dob",dob);
                                editor.putString("phone",phone);
                                editor.putString("email",email);
                                editor.putString("id",id);
                                editor.commit();
                                break;
                            }

                        } else {
                            Log.w("===", "Error getting documents.", task.getException());

                        }
                    }
                });

    }

    private void openDiadlog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.login_info_layout, null);
        builder.setView(view);
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        edtDOB = view.findViewById(R.id.edtDOB);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        edtPhoneNumber.setText(phonenumber);
        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog date = new DatePickerDialog(ConfirmOTP.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtDOB.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, nam, thang, ngay);
                date.show();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edtFirstName.getText().toString();
                String lastName = edtLastName.getText().toString();
                String email = edtEmail.getText().toString();
                String dob = edtDOB.getText().toString();
                String avt = " ";
                Map<String, Object> user = new HashMap<>();
                user.put("FIRSTNAME", firstName);
                user.put("LASTNAME", lastName);
                user.put("EMAIL", email);
                user.put("PHONENUMBER", phonenumber);
                user.put("DOB", dob);
                user.put("ISLOGIN", true);
                db.collection("USERS")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(">>>>>>>>>>>>>>>", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("<<<<<<<<<<<<<<<<<<<<<<", "Error adding document", e);
                            }
                        });
                startActivity(new Intent(ConfirmOTP.this, MainActivity.class));
                finish();
                dialog.dismiss();
            }
        });
    }

    private void writeLoginStatus(boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remember", status);
        editor.commit();
    }
}