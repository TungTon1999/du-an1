package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.activities.ConfirmOTP;
import com.example.duan1.activities.GiftActivity;
import com.example.duan1.activities.MainActivity;
import com.example.duan1.activities.TableActivity;
import com.example.duan1.activities.UpdateFood;
import com.example.duan1.model.News;
import com.example.duan1.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.xml.sax.SAXParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuAdapterViewHolder>{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edtUpdatePrice,edtUpdateName;
    TableActivity tableActivity;
    Button btnUpdateMenu;
    List<SanPham> mList;
    List<SanPham> mListOld;
    Boolean isAdmin = false;
    private onClickListenerAdapter onClickListenerAdapter;
    Context context;
    public MenuAdapter(List<SanPham> mList, Context context,onClickListenerAdapter onClickListenerAdapter,Boolean isAdmin) {
        this.mList = mList;
        this.mListOld = mList;
        this.context=context;
        this.onClickListenerAdapter = onClickListenerAdapter;
        this.isAdmin = isAdmin;

    }


    @Override
    public MenuAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_layout,parent,false);
        return new MenuAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapterViewHolder holder, int position) {
        SanPham sanPham = mList.get(position);
        if (sanPham == null){
            return;
        }
        Picasso.get().load(sanPham.getImage()).into(holder.imageView);
        holder.tvPrice.setText(sanPham.getPrice()+"$");
        holder.tvName.setText(sanPham.getName());
        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenerAdapter.onClickToOrder(sanPham.getName(),sanPham.getPrice());
            }
        });

        if (isAdmin == true){
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.imgUpdate.setVisibility(View.VISIBLE);
            holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDiadlog(sanPham.getName() , sanPham.getPrice(),sanPham.getId());
                }
            });
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.collection("MENU").document(sanPham.getId())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(v.getContext(), "Da Xoa", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error deleting document", e);
                                }
                            });
                }
            });
        }
    }

    private void openDiadlog(String name  , String price,String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view2 = inflater.inflate(R.layout.layout_update_menu, null);
        builder.setView(view2);
        btnUpdateMenu = view2.findViewById(R.id.btnUpdateMenu);
        edtUpdateName = view2.findViewById(R.id.edtUpdateName);
        edtUpdatePrice = view2.findViewById(R.id.edtUpdatePrice);
        edtUpdateName.setText(name);
        edtUpdatePrice.setText(price);
        Dialog dialog = builder.create();
        dialog.show();
        btnUpdateMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameUpdate = edtUpdateName.getText().toString().toUpperCase();
                String priceUpdate = edtUpdatePrice.getText().toString();
                DocumentReference washingtonRef = db.collection("MENU").document(id);
                washingtonRef
                        .update("NAME", nameUpdate,"PRICE",priceUpdate)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                                dialog.dismiss();0
                                notifyDataSetChanged();
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


    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public class MenuAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView,imgUpdate;
        TextView tvPrice,tvName;
        CircleImageView fab;
        TextView tvDelete;
        public MenuAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgFood);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            fab = itemView.findViewById(R.id.floatingActionbuttonAddFood);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }
}