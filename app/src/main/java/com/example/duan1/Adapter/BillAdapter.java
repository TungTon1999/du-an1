package com.example.duan1.Adapter;

import android.content.Context;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

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
        holder.rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                holder.rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        holder.btnRating.setVisibility(View.VISIBLE);
                        holder.btnRating.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "Your rating is send " + "\n" + "Thank for your Advise", Toast.LENGTH_SHORT).show();
                                holder.btnRating.setVisibility(View.GONE);
                                holder.rating_bar.setVisibility(View.GONE);
                            }
                        });


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
        TextView tv_foodname, tv_date, tv_discount, tv_address, tv_price;
        RatingBar rating_bar;
        Button btnRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_foodname = itemView.findViewById(R.id.tv_foodname);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_price = itemView.findViewById(R.id.tv_price);
            rating_bar = itemView.findViewById(R.id.rating_bar);
            btnRating = itemView.findViewById(R.id.btnRating);
        }
    }
}
