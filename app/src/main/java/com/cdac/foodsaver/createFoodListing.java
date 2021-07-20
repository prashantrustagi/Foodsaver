package com.cdac.foodsaver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.foodsaver.adapter.PendingOrderAdapter;
import com.cdac.foodsaver.model.PendingOrder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class createFoodListing extends AppCompatActivity {
    ImageView backButton;
    RecyclerView recyclerView3;
    PendingOrderAdapter pendingOrderAdapter;
    Button postListing;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_food_listing);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        List<PendingOrder> pendingOrderList = new ArrayList<>();
        pendingOrderList.add(new PendingOrder("Burger", "Veg X 2", "Perish ", "8:40PM", R.drawable.burgerlisting));
        pendingOrderList.add(new PendingOrder("Burger", "Veg X 2", "Perish ", "8:40PM", R.drawable.burgerlisting));
        pendingOrderList.add(new PendingOrder("Burger", "Veg X 2", "Perish ", "8:40PM", R.drawable.burgerlisting));
        pendingOrderList.add(new PendingOrder("Burger", "Veg X 2", "Perish ", "8:40PM", R.drawable.burgerlisting));
        setPendingRecycler(pendingOrderList);

        postListing = findViewById(R.id.postListing);
         postListing.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(createFoodListing.this, finalPost.class);
                 startActivity(intent);
             }
         });
         actionButton = findViewById(R.id.actionBtn);
         actionButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent move = new Intent(createFoodListing.this, addFoodItem.class);
                 startActivity(move);
             }
         });
    }
    private void setPendingRecycler(List<PendingOrder> pendingOrderList) {
        recyclerView3= findViewById(R.id.recyclerView_createFood);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView3.setLayoutManager(layoutManager);
        pendingOrderAdapter = new PendingOrderAdapter(this, pendingOrderList);
        recyclerView3.setAdapter(pendingOrderAdapter);
    }
}