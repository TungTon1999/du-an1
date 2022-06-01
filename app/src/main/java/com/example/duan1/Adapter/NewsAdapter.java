package com.example.duan1.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1.R;
import com.example.duan1.activities.MainActivity;
import com.example.duan1.activities.SplashActivity;
import com.example.duan1.activities.WebViewActivity;
import com.example.duan1.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{
    List<News> list;
    News news = null;
    Context context;


    public NewsAdapter(Context context,List<News> list) {
        this.list=list;
        this.context = context;
    }
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_layout,parent,false);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapterViewHolder holder, int position) {
        news = list.get(position);
        holder.imageView.setImageResource(news.getImage());
        holder.tv.setText(news.getContent());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = news.getUrl();
                Intent i = new Intent(context,WebViewActivity.class);
                i.putExtra("url",url);
                context.startActivity(i);
                Animatoo.animateFade(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView imageView;
        CardView cardView;
        public NewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvContent);
            imageView = itemView.findViewById(R.id.imgContent);
            cardView = itemView.findViewById(R.id.cardViewWeb);
        }
    }
}
