package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.Notification;
import com.example.duan1.model.Order;

import java.util.List;

public class NoticationAdapter extends RecyclerView.Adapter<NoticationAdapter.NotificationAdapterViewHolder> {
    List<Notification> list;
    Context context;

    public NoticationAdapter(List<Notification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public NotificationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_layout,parent,false);
        return new NoticationAdapter.NotificationAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapterViewHolder holder, int position) {
        Notification notification = list.get(position);
        if (notification == null){
            return;
        }
        holder.tvNotification.setText(notification.getNoti());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotification;
        public NotificationAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotification = itemView.findViewById(R.id.tvNotification);
        }
    }
}
