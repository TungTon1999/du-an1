package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    CardView cvHistory,cvMusic,cvClause;
    LinearLayout linearLayoutRate,linearLayoutTVOTG,linearLayoutAccount,linearLayoutAddress,linearLayoutSetting,linearLayoutLogout,linearLayoutAdmin;
    BottomNavigationView bottomNavigationView;
    RelativeLayout rl;
    TextView tv_uni,tv_history,tv_music,tv_clause,tv_support,tv_rate,tv_voice,tv_account,tv_youraccount,tv_youraddress,tv_yoursetting,tv_logout;
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initUI();
        initBottomNavigtionTable();
        LoadSetting();
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        phonenumber = sharedPreferences.getString("phone","012345789");
        if(phonenumber.equalsIgnoreCase("+84789867336")){
            linearLayoutAdmin.setVisibility(View.VISIBLE);
        }
        cvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this,BillActivity.class);
                startActivity(i);
                Animatoo.animateSlideRight(MenuActivity.this);
            }
        });
        linearLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences2 = getSharedPreferences("LOGIN_STATUS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.clear();
                editor2.commit();
                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        linearLayoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("SET_SETTING", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.commit();
                Intent i = new Intent(MenuActivity.this,PreferenceSetting.class);
                startActivity(i);
            }
        });
        linearLayoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, YourAccount.class);
                startActivity(i);
            }
        });

        cvClause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, DieuKhoanActivity.class);
                startActivity(i);
            }
        });
        linearLayoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuActivity.this,CustomerLocationActivity.class);
                startActivity(i);
            }
        });
        linearLayoutTVOTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuActivity.this,GuestVoiceActivity.class);
                startActivity(i);
            }
        });
        linearLayoutRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        linearLayoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MenuActivity.this,AdminActivity.class);
                startActivity(i);
            }
        });
    }



    private void initUI() {
        cvHistory = findViewById(R.id.cvHistory);
        linearLayoutAdmin = findViewById(R.id.linearLayoutAdmin);
        cvClause = findViewById(R.id.cvDieuKhoan);
        linearLayoutRate = findViewById(R.id.linearLayoutRate);
        linearLayoutAccount = findViewById(R.id.linearLayoutYourAccount);
        linearLayoutTVOTG = findViewById(R.id.linearLayoutTVOTG);
        linearLayoutAddress = findViewById(R.id.linearLayoutYourAddress);
        linearLayoutSetting = findViewById(R.id.linearLayoutYourSetting);
        linearLayoutLogout = findViewById(R.id.linearLayoutLogOut);
        bottomNavigationView = findViewById(R.id.bottomNavigationMenu);
        rl=findViewById(R.id.rl);
        tv_uni=findViewById(R.id.tv_uni);
        tv_history=findViewById(R.id.tv_history);
        tv_clause=findViewById(R.id.tv_clause);
        tv_support=findViewById(R.id.tv_support);
        tv_rate=findViewById(R.id.tv_rate);
        tv_voice=findViewById(R.id.tv_voice);
        tv_account=findViewById(R.id.tv_account);
        tv_youraccount=findViewById(R.id.tv_youraccount);
        tv_youraddress=findViewById(R.id.tv_youraddress);
        tv_yoursetting=findViewById(R.id.tv_yoursetting);
        tv_logout=findViewById(R.id.tv_logout);


    }
    private void initBottomNavigtionTable() {
        bottomNavigationView.setSelectedItemId(R.id.menu_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_store:
                        startActivity(new Intent(MenuActivity.this, TableActivity.class));
                        Animatoo.animateFade(MenuActivity.this);  //fire the zoom animation
                        break;
                    case R.id.menu_menu:

                        break;
                    case R.id.menu_home:
                        startActivity(new Intent(MenuActivity.this, MainActivity.class));
                        Animatoo.animateFade(MenuActivity.this);  //fire the zoom animation
                        break;
                    case R.id.menu_gift:
                        startActivity(new Intent(MenuActivity.this, GiftActivity.class));
                        Animatoo.animateFade(MenuActivity.this);  //fire the zoom animation
                        break;

                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MenuActivity.this, MainActivity.class));
        Animatoo.animateFade(MenuActivity.this);  //fire the zoom animation
    }

    public void LoadSetting(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean chk_night = sp.getBoolean("Night", false);
        if (chk_night) {
            rl.setBackgroundColor(Color.parseColor("#222222"));
            tv_uni.setTextColor(Color.parseColor("#303f9f"));
            tv_history.setTextColor(Color.parseColor("#303f9f"));
           tv_clause.setTextColor(Color.parseColor("#303f9f"));
            tv_support.setTextColor(Color.parseColor("#303f9f"));
            tv_rate.setTextColor(Color.parseColor("#303f9f"));
            tv_voice.setTextColor(Color.parseColor("#303f9f"));
            tv_account.setTextColor(Color.parseColor("#303f9f"));
            tv_youraccount.setTextColor(Color.parseColor("#303f9f"));
            tv_youraddress.setTextColor(Color.parseColor("#303f9f"));
            tv_yoursetting.setTextColor(Color.parseColor("#303f9f"));
            tv_logout.setTextColor(Color.parseColor("#303f9f"));
        } else {
            rl.setBackgroundColor(Color.parseColor("#ffffff"));
            tv_uni.setTextColor(Color.parseColor("#000000"));
            tv_history.setTextColor(Color.parseColor("#000000"));
            tv_clause.setTextColor(Color.parseColor("#000000"));
            tv_support.setTextColor(Color.parseColor("#000000"));
            tv_rate.setTextColor(Color.parseColor("#000000"));
            tv_voice.setTextColor(Color.parseColor("#000000"));
            tv_account.setTextColor(Color.parseColor("#000000"));
            tv_youraccount.setTextColor(Color.parseColor("#000000"));
            tv_youraddress.setTextColor(Color.parseColor("#000000"));
            tv_yoursetting.setTextColor(Color.parseColor("#000000"));
            tv_logout.setTextColor(Color.parseColor("#000000"));
        }

        String orien = sp.getString("Orientation","");
        if("1".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        }else if("2".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else if("3".equals(orien)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }
    }


    @Override
    protected void onResume() {
        LoadSetting();
        super.onResume();
    }
}