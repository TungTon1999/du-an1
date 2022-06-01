package com.example.duan1.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VoVanTanActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FusedLocationProviderClient fusedLocationProviderClient;
    public View layout_dialog_add;
    LayoutInflater inflater;
    AlertDialog dialogAdd;
    View linearLayoutManagerLocation;
    View linearLayoutManagerPhone;
    String currentLocation;
    Button buttonReserveVVT;
    CardView cardViewCTLocation;
    String locationCT = "223 Vo Van Tan";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet_vovanvan);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        layout_dialog_add = inflater.inflate(R.layout.layout_dialog_datban, (ViewGroup) findViewById(R.id.layout_dialog));



        if (layout_dialog_add.getParent() != null) {// xóa các view ở lần bấm chuột trước
            ((ViewGroup) layout_dialog_add.getParent()).removeAllViews();
        }
        //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
        EditText edChair =  layout_dialog_add.findViewById(R.id.edChair);  // editext này lấy ở file layout_custom_dialog
        TextView edTime =   layout_dialog_add.findViewById(R.id.edTime);
        TextView edDate =   layout_dialog_add.findViewById(R.id.edDate);
        Spinner spRoom =   layout_dialog_add.findViewById(R.id.spRoom);

        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int gio = calendar.get(Calendar.HOUR);
                int phut = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(VoVanTanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("HH:mm:ss");
                        calendar.set(0,0,0,hourOfDay,minute);
                        edTime.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                },gio,phut,true);
                timePickerDialog.show();
            }
        });

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog date = new DatePickerDialog(VoVanTanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },nam,thang,ngay);
                date.show();
            }
        });

        //Building dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout_dialog_add);
        builder.setTitle(locationCT);
        builder.setIcon(R.drawable.ic_house);
        String[] arrTT = {"Neutral Room","VIP1 Room","VIP2 Room"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(VoVanTanActivity.this,
                android.R.layout.simple_spinner_dropdown_item,arrTT);
        spRoom.setAdapter(spnAdapter);// nếu bạn không thích thì comment dòng này lại
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                // lấy dữ liệu người dùng nhập cho vào biến
                String chair = edChair.getText().toString();
                String time = edTime.getText().toString();
                String date = edDate.getText().toString();
                String room = (String) spRoom.getSelectedItem();

                // có dữ liệu rồi thì bạn gọi lệnh ghi vào csdl ở đây nhé

                Map<String, Object> table = new HashMap<>();
                table.put("CHAIR", chair);
                table.put("TIME", time);
                table.put("DATE", date);
                table.put("ROOM", room);


                db.collection("TABLE")
                        .add(table)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(VoVanTanActivity.this, "Dat ban thanh cong", Toast.LENGTH_SHORT).show();
                                Log.d(">>>>>>>>>>>>>>>", "DocumentSnapshot added with ID: " + documentReference.getId());
                                setNotification("You have a dinner at Our Restaurant at " + time + " on " + date);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("<<<<<<<<<<<<<<<<<<<<<<", "Error adding document", e);
                                Toast.makeText(VoVanTanActivity.this, "Đặt bàn thất bại", Toast.LENGTH_SHORT).show();

                            }
                        });


                dialog.dismiss();// tắt dialog
                Intent i = new Intent(VoVanTanActivity.this,GiftActivity.class);
                startActivity(i);
                finish();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // tắt dialog
            }
        });
        dialogAdd = builder.create(); // tạo dialog


        checkMyPermission();
        initUI();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        cardViewCTLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        linearLayoutManagerLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        linearLayoutManagerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
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
        Notification notification = new NotificationCompat.Builder(VoVanTanActivity.this,"RESTAURANT")
                .setContentTitle("Our Restaurant")
                .setContentText(notificaitons)
                .setSmallIcon(R.drawable.ic_baseline_restaurant_menu_24)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        String phonenumber = sharedPreferences.getString("phone","012345789");
        Map<String, Object> noti = new HashMap<>();
        noti.put("NOTIFICATION", notificaitons);
        noti.put("PHONE",phonenumber );

        db.collection("NOTIFICATION")
                .add(noti)
                .addOnSuccessListener(new OnSuccessListener <DocumentReference>() {
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
    }

    private void callPhone() {
        String phone ="19000000";

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }

    private void checkMyPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "permission grant", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "permission not grant", Toast.LENGTH_SHORT).show();

        }
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(VoVanTanActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude()
                                , 1);
                        currentLocation = addresses.get(0).getAddressLine(0);
                        Log.d("===",currentLocation+"");
                        openGGMap(currentLocation, locationCT);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void openGGMap(String currentLocation, String locationCT) {
        try {
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + currentLocation + "/" + locationCT);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void initUI() {
        linearLayoutManagerLocation = findViewById(R.id.linearLayoutLocationVoVanTan);
        linearLayoutManagerPhone = findViewById(R.id.linearLayoutPhoneCaoThangVoVanTan);
        buttonReserveVVT = findViewById(R.id.btnReserveVVT);
        cardViewCTLocation = findViewById(R.id.cardViewLocationVoVanTan);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VoVanTanActivity.this, GiftActivity.class));
        Animatoo.animateSlideDown(VoVanTanActivity.this);
    }
    public void ShowDialogAdd(View view){
        dialogAdd.show();
    }


}
