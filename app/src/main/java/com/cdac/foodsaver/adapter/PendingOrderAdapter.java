package com.cdac.foodsaver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.foodsaver.R;
import com.cdac.foodsaver.model.PendingOrder;

import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder> {
    Context context;
    List<PendingOrder> pendingOrderList;

    public PendingOrderAdapter(Context context, List<PendingOrder> popularFoodList) {
        this.context = context;
        this.pendingOrderList = popularFoodList;
    }

    @NonNull
    @Override
    public PendingOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_order_layout, parent,false);
        return new PendingOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrderAdapter.PendingOrderViewHolder holder, int position) {
        holder.foodImage.setImageResource(pendingOrderList.get(position).getImageurl());
        holder.name.setText(pendingOrderList.get(position).getName());
        holder.food.setText(pendingOrderList.get(position).getFood());
        holder.time.setText(pendingOrderList.get(position).getTime());
        holder.logictics.setText(pendingOrderList.get(position).getLogicticsName());
    }

    @Override
    public int getItemCount() {
        return pendingOrderList.size();
    }

    public static final class PendingOrderViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView food, name,time,logictics;
        public PendingOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            food = itemView.findViewById(R.id.food);
            name = itemView.findViewById(R.id.restaurant_name);
            time = itemView.findViewById(R.id.time);
            logictics = itemView.findViewById(R.id.logistics);
        }
    }
}

