package com.example.duan1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.activities.TableActivity;
import com.example.duan1.model.HoaDon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    List<HoaDon> list;
    Boolean isAdmin = false;
    Context context;
    FirebaseFirestore db;

    public BillAdapter(List<HoaDon> list, Context context) {
        this.list = list;
        this.context = context;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = FirebaseFirestore.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon hoaDon = list.get(position);
        if (hoaDon == null) {
            return;
        }
        holder.tv_foodname.setText("Your Food" + "\n" + hoaDon.getFoodname());
        holder.tv_address.setText("Address : " + hoaDon.getAddress());
        holder.tv_price.setText(hoaDon.getPrice());
        holder.tv_date.setText("Date : " + hoaDon.getDate());
        holder.tv_discount.setText("Discount code : " + hoaDon.getDiscountcode());
        holder.tv_phonenumberr.setText(hoaDon.getPhone());
        holder.btnRating.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onClick(View view) {
                holder.tvStatus.setText("Is Delivery");
                holder.tvStatus.setTextColor(R.color.teal_200);
               SharedPreferences sharedPreferences = context.getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
               Map<String, Object> noti = new HashMap<>();
               noti.put("NOTIFICATION", "Your order is confirmed \" + \"\\n\" +\"Please wait for Us");
               noti.put("PHONE",hoaDon.getPhone() );

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
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_foodname, tv_date, tv_discount, tv_address, tv_price,tvStatus,tv_phonenumberr;
        Button btnRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_foodname = itemView.findViewById(R.id.tv_foodname);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_price = itemView.findViewById(R.id.tv_price);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnRating = itemView.findViewById(R.id.btnRating);
            tv_phonenumberr = itemView.findViewById(R.id.tv_phonenumberr);
        }
    }
}
