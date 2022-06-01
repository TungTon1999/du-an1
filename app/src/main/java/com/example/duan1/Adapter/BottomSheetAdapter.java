package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.News;
import com.example.duan1.model.Order;
import com.example.duan1.model.SanPham;

import java.util.List;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomSheetAdapterViewHolder> {
    List<Order> list;
    List<Order> nList;
    Context context;
    public BottomSheetAdapter(Context context, List<Order> list) {
        this.list=list;
        nList = list;
        this.context = context;
    }
    @Override
    public BottomSheetAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_layout,parent,false);
        return new BottomSheetAdapter.BottomSheetAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetAdapterViewHolder holder, int position) {
        Integer Count = 0;
        Order sanPham = list.get(position);
        if (sanPham == null){
            return;
        }
        holder.tvPriceOrder.setText(sanPham.getPrice()+"$");
        holder.tvNameOrder.setText(sanPham.getName());
        for (int i=0 ; i < list.size() ; i++){
            if (nList.get(i).getName().equals(sanPham.getName())){
                Count = Count + 1;
            }
        }
        holder.tvCountOrder.setText(Count+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BottomSheetAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountOrder,tvNameOrder,tvPriceOrder;
        public BottomSheetAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountOrder = itemView.findViewById(R.id.tvCountOrder);
            tvNameOrder = itemView.findViewById(R.id.tvNameOrder);
            tvPriceOrder = itemView.findViewById(R.id.tvPriceOrder);

        }
    }
}
