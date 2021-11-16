package com.cdac.foodsaver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.foodsaver.R;
import com.cdac.foodsaver.UpdateItems;
import com.cdac.foodsaver.firebasemodel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class firebaseAdapter extends FirestoreRecyclerAdapter<firebasemodel, firebaseAdapter.listViewHolder> {

    private OnItemClickListener listener;



    public firebaseAdapter(@NonNull FirestoreRecyclerOptions<firebasemodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull listViewHolder holder, int position, @NonNull firebasemodel model) {

        holder.foodType.setText(model.getFoodType());
        holder.food.setText(model.getItemName());
        holder.Ptime.setText(model.getPerishTime());
    }

    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_pending_order_list,parent,false);
        return new listViewHolder(view);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    class listViewHolder extends RecyclerView.ViewHolder
    {

        TextView food, foodType,Ptime;
        public listViewHolder(@NonNull View itemView) {
            super(itemView);

            food = itemView.findViewById(R.id.item_name_rec);
            foodType = itemView.findViewById(R.id.food_type_rec);
            Ptime = itemView.findViewById(R.id.perish_time_rec);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(UpdateItems.class,position);
                    }
                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClick(Class UpdateItems, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;
    }
}

