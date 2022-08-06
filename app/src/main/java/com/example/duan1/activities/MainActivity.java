package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.duan1.Adapter.NewsAdapter;
import com.example.duan1.R;
import com.example.duan1.model.News;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Boolean isEx;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;
    BottomNavigationView bottomNavigationView;
    MediaPlayer mp;
    NestedScrollView ncl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(MainActivity.this, R.raw.nhac2);
        mp.setLooping(true);
        mp.setVolume(1000, 1000);
        mp.start();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        initUl();
        initToolbar();
        addListData();
        initToolbarAnimation();
        initBottomNavigtion();
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        LoadSetting();
    }


    private void initBottomNavigtion() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_store:
                        startActivity(new Intent(MainActivity.this, TableActivity.class));
                        break;
                    case R.id.menu_menu:
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        break;
                    case R.id.menu_home:
                        break;
                    case R.id.menu_gift:
                        startActivity(new Intent(MainActivity.this, GiftActivity.class));
                        break;
                }
                return true;
            }
        });
    }
    public void LoadSetting(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean chk_night = sp.getBoolean("Night", false);
        if (chk_night) {
            ncl.setBackgroundColor(Color.parseColor("#222222"));
        } else {
            ncl.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }


    private void initUl() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        appBarLayout = findViewById(R.id.appbarLayout);
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collaspingToolbarLayout);
        recyclerView = findViewById(R.id.recycleView);
        ncl=findViewById(R.id.ncl);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void addListData() {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        newsAdapter = new NewsAdapter(MainActivity.this,getListNews());
        recyclerView.setAdapter(newsAdapter);
    }

    private void initToolbarAnimation() {

        collapsingToolbarLayout.setTitle("Hello ");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toolbarimage);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor = palette.getVibrantColor(getResources().getColor(R.color.white));
                collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
                collapsingToolbarLayout.setContentScrimColor(myColor);
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.trans));

            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200) {
                    isEx = false;
                } else {
                    isEx = true;
                }
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.icon_thongbao_menu){
            Intent i = new Intent(MainActivity.this,NotificationActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<News> getListNews() {
        List<News> list = new ArrayList<>();
        list.add(new News("Make up for lost time at this slick new European bar and bistro by Joel Bickford", R.drawable.img, "https://www.delicious.com.au/eat-out/restaurants/article/menzies-bar-bistro-opens-shell-house-sydney-cbd/86rwp6ij"));
        list.add(new News("An upper-crust bakery and restaurant has opened at Hinchcliff House, Sydney", R.drawable.img_2, "https://www.delicious.com.au/eat-out/restaurants/article/grana-sydney-day-restaurant-bakery-opens-hinchcliff-house/rfy4h6nf"));
        list.add(new News("This 100-year-old house in the Northern Rivers is now a must-visit restaurant", R.drawable.img_3, "https://www.delicious.com.au/eat-out/restaurants/article/tweed-river-house-french-bistro-bar-opens-murwillumbah/xxk82khc"));
        list.add(new News("Maurice Terzini and Joe Vargetto to open an Italian restaurant dedicated to 'peasant food'", R.drawable.img_5, "https://www.delicious.com.au/eat-out/restaurants/article/cucina-povera-italian-restaurant-open-melbourne-cbd/rq145yn3"));
        list.add(new News("25 Sydney restaurants that you can book right now", R.drawable.img_6, "https://www.delicious.com.au/eat-out/restaurants/gallery/sydney-restaurants-you-can-book-right-now/pfnlqyfg"));
        list.add(new News("10 new restaurants in Sydney you may have missed", R.drawable.img_1, "https://www.delicious.com.au/eat-out/restaurants/article/menzies-bar-bistro-opens-shell-house-sydney-cbd/86rwp6ij"));
        list.add(new News("Make up for lost time at this slick new European bar and bistro by Joel Bickford", R.drawable.img, "https://www.delicious.com.au/eat-out/restaurants/article/menzies-bar-bistro-opens-shell-house-sydney-cbd/86rwp6ij"));
        list.add(new News("An upper-crust bakery and restaurant has opened at Hinchcliff House, Sydney", R.drawable.img_2, "https://www.delicious.com.au/eat-out/restaurants/article/grana-sydney-day-restaurant-bakery-opens-hinchcliff-house/rfy4h6nf"));
        list.add(new News("This 100-year-old house in the Northern Rivers is now a must-visit restaurant", R.drawable.img_3, "https://www.delicious.com.au/eat-out/restaurants/article/tweed-river-house-french-bistro-bar-opens-murwillumbah/xxk82khc"));
        list.add(new News("Maurice Terzini and Joe Vargetto to open an Italian restaurant dedicated to 'peasant food'", R.drawable.img_5, "https://www.delicious.com.au/eat-out/restaurants/article/cucina-povera-italian-restaurant-open-melbourne-cbd/rq145yn3"));
        list.add(new News("25 Sydney restaurants that you can book right now", R.drawable.img_6, "https://www.delicious.com.au/eat-out/restaurants/gallery/sydney-restaurants-you-can-book-right-now/pfnlqyfg"));
        list.add(new News("10 new restaurants in Sydney you may have missed", R.drawable.img_1, "https://www.delicious.com.au/eat-out/restaurants/article/menzies-bar-bistro-opens-shell-house-sydney-cbd/86rwp6ij"));
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.release();
        finish();
    }



    @Override
    protected void onResume() {

        initToolbarAnimation();
        LoadSetting();
        super.onResume();
    }



}