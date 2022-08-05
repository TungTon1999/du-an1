package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.Adapter.BottomSheetAdapter;
import com.example.duan1.Adapter.MenuAdapter;
import com.example.duan1.Adapter.onClickListenerAdapter;
import com.example.duan1.R;
import com.example.duan1.model.Order;
import com.example.duan1.model.SanPham;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TableActivity extends AppCompatActivity  {

    private static final int NOTIFICATION_ID = 1;
    EditText edtDiscount, edtInfomation;
    TextView tvAddress, tvCountItem, tvSum, btnOrder, tvTenDuong, tvDiaChi, tvNameCustomer, tvPhoneCustomer, tvPriceFirst, tvPriceDiscount, tvPriceCOD;
    Toolbar toolbar;
    String currentLocation1, infomationForDelvery, currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    Integer count = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinearLayoutManager layoutManager, layoutManager2;
    LinearLayout linnearGoToOrder, linearLayoutOrder, lnDelete;
    List<SanPham> listMenu;
    List<Order> listOrder;
    MenuAdapter menuAdapter;
    RecyclerView recyclerViewMenu, recycleViewOrder;
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    BottomSheetAdapter bottomSheetAdapter;
    MediaPlayer mp;
    CoordinatorLayout rl_table;
    BottomSheetBehavior bottomSheetBehavior;
    RelativeLayout bottomSheet;
    Date date = new Date();

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        mp = MediaPlayer.create(TableActivity.this, R.raw.nhac1);
        mp.setLooping(true);
        mp.setVolume(1000, 1000);
        mp.start();
        initUI();
        addListData();
        initToolbar();
        initBottomNavigtionTable();
        LoadSetting();


        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        linnearGoToOrder.setOnClickListener(new View.OnClickListener() {
            CharSequence s = DateFormat.getDateInstance().format(date.getTime());

            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    btnOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
                            Map<String, Object> bill = new HashMap<>();
                            bill.put("PHONE", sharedPreferences.getString("phone", "0912181340"));
                            bill.put("DATE", s);
                            bill.put("FOOD", listOrder);
                            bill.put("PRICE", tvPriceDiscount.getText().toString());
                            bill.put("ADDRESS", currentLocation);
                            bill.put("INFO", edtInfomation.getText().toString());
                            bill.put("DISCOUNTCODE", edtDiscount.getText().toString());
                            db.collection("BILL")
                                    .add(bill)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(">>>>>>>>>>>>>>>", "DocumentSnapshot added with ID: " + documentReference.getId());
                                            Toast.makeText(TableActivity.this, "Order thanh cong", Toast.LENGTH_SHORT).show();
                                            String n = "Your order is confirmed " + "\n" +"Please wait for Us";
                                            setNotification(n);
                                            listOrder.clear();
                                            count = 0;
                                            linearLayoutOrder.setVisibility(View.GONE);
                                            bottomSheetAdapter.notifyDataSetChanged();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("<<<<<<<<<<<<<<<<<<<<<<", "Error adding document", e);
                                        }
                                    });
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    });
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        tvNameCustomer.setText(sharedPreferences.getString("firstname", "A") + " " + sharedPreferences.getString("lastname", "B"));
        tvPhoneCustomer.setText(sharedPreferences.getString("phone", "012345789"));
        edtDiscount.setOnEditorActionListener(editaction);
        edtInfomation.setOnEditorActionListener(editaction);
        lnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOrder.clear();
                count = 0;
                linearLayoutOrder.setVisibility(View.GONE);
                bottomSheetAdapter.notifyDataSetChanged();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

    }

    private void setNotification(String notificaitons) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CHANEL";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("RESTAURANT", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Notification notification = new NotificationCompat.Builder(TableActivity.this,"RESTAURANT")
                .setContentTitle("Our Restaurant")
                .setContentText(notificaitons)
                .setSmallIcon(R.drawable.ic_baseline_restaurant_menu_24)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }

    private TextView.OnEditorActionListener editaction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (i) {
                case EditorInfo.IME_ACTION_GO:
                    currentLocation1 = edtDiscount.getText().toString();
                    if (edtDiscount.getText().toString().equalsIgnoreCase("DUAN1")) {
                        double price = count * 0.5;
                        tvPriceDiscount.setText(price + "$");
                        tvPriceCOD.setText(price + "$");
                    } else {
                        Toast.makeText(TableActivity.this, "Invalid Disount Code", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case EditorInfo.IME_ACTION_DONE:
                    infomationForDelvery = edtInfomation.getText().toString();
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    break;

            }
            return false;
        }

    };

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    public void addListData() {
        listOrder = new ArrayList<>();
        db.collection("MENU")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listMenu = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("====", document.getId() + " => " + document.getData());
                                String name = document.getString("NAME");
                                String price = document.getString("PRICE");
                                String type = document.getString("TYPE");
                                String image = document.getString("IMAGE");
                                String id = document.getId();
                                SanPham sanPham = new SanPham(name, price, type, image, id);
                                listMenu.add(sanPham);
                            }
                            layoutManager = new LinearLayoutManager(TableActivity.this);
                            recyclerViewMenu.setLayoutManager(layoutManager);
                            menuAdapter = new MenuAdapter(listMenu, TableActivity.this, new onClickListenerAdapter() {
                                @Override
                                public void onClickToOrder(String name, String price) {
                                    Order sanPhamOrder = new Order(name, price);
                                    listOrder.add(sanPhamOrder);
                                    layoutManager2 = new LinearLayoutManager(TableActivity.this);
                                    recycleViewOrder.setLayoutManager(layoutManager2);
                                    bottomSheetAdapter = new BottomSheetAdapter(TableActivity.this, listOrder);
                                    recycleViewOrder.setAdapter(bottomSheetAdapter);
                                    if (listOrder.size() > 0) {
                                        linearLayoutOrder.setVisibility(View.VISIBLE);
                                    }
                                    tvCountItem.setText(listOrder.size() + "");

                                    for (int i = (listOrder.size() - 1); i < listOrder.size(); i++) {
                                        Integer mPrice = Integer.parseInt(listOrder.get(i).getPrice());
                                        count = count + mPrice;
                                    }
                                    tvSum.setText(count + "$");
                                    tvPriceFirst.setText(count + "$");
                                    tvPriceDiscount.setText(count + "$");
                                    tvPriceCOD.setText(count + "$");
                                    bottomSheetAdapter.notifyDataSetChanged();
                                }
                            }, getIntent().getBooleanExtra("admin", false));
                            recyclerViewMenu.setAdapter(menuAdapter);
                        } else {
                            Log.w("===", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void initUI() {
        edtInfomation = findViewById(R.id.edtInfomation);
        lnDelete = findViewById(R.id.lnDelete);
        tvPriceDiscount = findViewById(R.id.tvPriceDiscount);
        tvPriceCOD = findViewById(R.id.tvPriceCOD);
        edtDiscount = findViewById(R.id.edtDiscount);
        tvPriceFirst = findViewById(R.id.tvPriceFirst);
        tvNameCustomer = findViewById(R.id.tvNameCustomer);
        tvPhoneCustomer = findViewById(R.id.tvPhoneCustomer);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvTenDuong = findViewById(R.id.tvTenDuong);
        linearLayoutOrder = findViewById(R.id.linnearLayoutOrder);
        btnOrder = findViewById(R.id.btnOrder);
        bottomSheet = findViewById(R.id.bottomSheet);
        linnearGoToOrder = findViewById(R.id.linnearGoToOrder);
        tvSum = findViewById(R.id.tvSum);
        tvAddress = findViewById(R.id.tvAddress);
        tvCountItem = findViewById(R.id.tvCountItem);
        toolbar = findViewById(R.id.toolbarMenu);

        recyclerViewMenu = findViewById(R.id.rcvMenu);
        recycleViewOrder = findViewById(R.id.recycleViewOrder);
        bottomNavigationView = findViewById(R.id.bottomNavigationTable);
        rl_table = findViewById(R.id.rl_table);
    }

    public void LoadSetting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean chk_night = sp.getBoolean("Night", false);
        if (chk_night) {
            rl_table.setBackgroundColor(Color.parseColor("#222222"));
            toolbar.setBackgroundColor(Color.parseColor("#222222"));
        } else {
            rl_table.setBackgroundColor(Color.parseColor("#ffffff"));
            toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }

    private void initBottomNavigtionTable() {
        bottomNavigationView.setSelectedItemId(R.id.menu_store);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_store:
                        break;
                    case R.id.menu_menu:
                        startActivity(new Intent(TableActivity.this, MenuActivity.class));
                        break;
                    case R.id.menu_home:
                        startActivity(new Intent(TableActivity.this, MainActivity.class));
                        break;
                    case R.id.menu_gift:
                        startActivity(new Intent(TableActivity.this, GiftActivity.class));
                        break;

                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TableActivity.this, MainActivity.class));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    listOrder = new ArrayList<>();
                    db.collection("MENU")
                            .whereEqualTo("TYPE", newText.toUpperCase())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        listMenu = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("====", document.getId() + " => " + document.getData());
                                            String name = document.getString("NAME");
                                            String price = document.getString("PRICE");
                                            String type = document.getString("TYPE");
                                            String image = document.getString("IMAGE");
                                            String id = document.getId();
                                            SanPham sanPham = new SanPham(name, price, type, image, id);
                                            listMenu.add(sanPham);
                                        }
                                        layoutManager = new LinearLayoutManager(TableActivity.this);
                                        recyclerViewMenu.setLayoutManager(layoutManager);
                                        menuAdapter = new MenuAdapter(listMenu, TableActivity.this, new onClickListenerAdapter() {
                                            @Override
                                            public void onClickToOrder(String name, String price) {
                                                Order sanPhamOrder = new Order(name, price);
                                                listOrder.add(sanPhamOrder);
                                                layoutManager2 = new LinearLayoutManager(TableActivity.this);
                                                recycleViewOrder.setLayoutManager(layoutManager2);
                                                bottomSheetAdapter = new BottomSheetAdapter(TableActivity.this, listOrder);
                                                recycleViewOrder.setAdapter(bottomSheetAdapter);
                                                if (listOrder.size() > 0) {
                                                    linearLayoutOrder.setVisibility(View.VISIBLE);
                                                }
                                                tvCountItem.setText(listOrder.size() + "");

                                                for (int i = (listOrder.size() - 1); i < listOrder.size(); i++) {
                                                    Integer mPrice = Integer.parseInt(listOrder.get(i).getPrice());
                                                    count = count + mPrice;
                                                }
                                                tvSum.setText(count + "$");
                                                tvPriceFirst.setText(count + "$");
                                                tvPriceDiscount.setText(count + "$");
                                                tvPriceCOD.setText(count + "$");
                                                bottomSheetAdapter.notifyDataSetChanged();
                                            }
                                        }, getIntent().getBooleanExtra("admin", false));
                                        recyclerViewMenu.setAdapter(menuAdapter);
                                    } else {
                                        Log.w("===", "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }else if (newText.length()==0){
                    listOrder = new ArrayList<>();
                    db.collection("MENU")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        listMenu = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("====", document.getId() + " => " + document.getData());
                                            String name = document.getString("NAME");
                                            String price = document.getString("PRICE");
                                            String type = document.getString("TYPE");
                                            String image = document.getString("IMAGE");
                                            String id = document.getId();
                                            SanPham sanPham = new SanPham(name, price, type, image, id);
                                            listMenu.add(sanPham);
                                        }
                                        layoutManager = new LinearLayoutManager(TableActivity.this);
                                        recyclerViewMenu.setLayoutManager(layoutManager);
                                        menuAdapter = new MenuAdapter(listMenu, TableActivity.this, new onClickListenerAdapter() {
                                            @Override
                                            public void onClickToOrder(String name, String price) {
                                                Order sanPhamOrder = new Order(name, price);
                                                listOrder.add(sanPhamOrder);
                                                layoutManager2 = new LinearLayoutManager(TableActivity.this);
                                                recycleViewOrder.setLayoutManager(layoutManager2);
                                                bottomSheetAdapter = new BottomSheetAdapter(TableActivity.this, listOrder);
                                                recycleViewOrder.setAdapter(bottomSheetAdapter);
                                                if (listOrder.size() > 0) {
                                                    linearLayoutOrder.setVisibility(View.VISIBLE);
                                                }
                                                tvCountItem.setText(listOrder.size() + "");
                                                for (int i = (listOrder.size() - 1); i < listOrder.size(); i++) {
                                                    Integer mPrice = Integer.parseInt(listOrder.get(i).getPrice());
                                                    count = count + mPrice;
                                                }
                                                tvSum.setText(count + "$");
                                                tvPriceFirst.setText(count + "$");
                                                tvPriceDiscount.setText(count + "$");
                                                tvPriceCOD.setText(count + "$");
                                                bottomSheetAdapter.notifyDataSetChanged();
                                            }
                                        }, getIntent().getBooleanExtra("admin", false));
                                        recyclerViewMenu.setAdapter(menuAdapter);
                                    } else {
                                        Log.w("===", "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }

                return false;
            }
        });
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.release();
        finish();
    }

    @Override
    protected void onResume() {
        LoadSetting();
        super.onResume();
    }
}