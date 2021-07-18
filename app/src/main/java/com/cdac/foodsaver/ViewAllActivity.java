package com.cdac.foodsaver;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.foodsaver.adapter.PendingOrderAdapter;
import com.cdac.foodsaver.model.PendingOrder;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    ImageView backButton;
    RecyclerView viewAllRecycler;
    PendingOrderAdapter pendingOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_all);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        List<PendingOrder> pendingOrderList = new ArrayList<>();
        pendingOrderList.add(new PendingOrder("ABC NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("BCD NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("CDE NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("EFG NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("XYZ NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("ZXC NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        setPendingRecycler(pendingOrderList);
    }

    private void setPendingRecycler(List<PendingOrder> pendingOrderList) {
        viewAllRecycler = findViewById(R.id.viewAllRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        viewAllRecycler.setLayoutManager(layoutManager);
        pendingOrderAdapter = new PendingOrderAdapter(this, pendingOrderList);
        viewAllRecycler.setAdapter(pendingOrderAdapter);
    }
}

