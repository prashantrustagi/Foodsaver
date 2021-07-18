package com.cdac.foodsaver;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.foodsaver.adapter.PendingOrderAdapter;
import com.cdac.foodsaver.model.PendingOrder;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView pendingRecycler;
    PendingOrderAdapter pendingOrderAdapter;
    TextView viewAll;
    ImageView imageView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        drawerLayout = findViewById(R.id.drawerLayout);


        List<PendingOrder> pendingOrderList = new ArrayList<>();
        pendingOrderList.add(new PendingOrder("ABC NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("BCD NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("CDE NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("EFG NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("XYZ NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        pendingOrderList.add(new PendingOrder("ZXC NGO", "Burger, Fries, Noodles, Soup...", "8.40pm", "Pickup(Akash 723)", R.drawable.burger));
        setPendingRecycler(pendingOrderList);
        viewAll = findViewById(R.id.viewAll);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAllActivity.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });




        // Code for the navbar



        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id)
                {
                    case R.id.my_profile:
                        Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
                        Intent profileIntent = new Intent(MainActivity.this, Profile.class);
                        startActivity(profileIntent);
                        break;
                    case R.id.my_orders:
                        Intent ordersIntent = new Intent(MainActivity.this, Orders.class);
                        startActivity(ordersIntent);
                        break;
                    case R.id.my_settings:
                        Intent settingsIntent = new Intent(MainActivity.this, Settings.class);
                        startActivity(settingsIntent);
                        break;
                    case R.id.my_help:
                        Intent helpIntent = new Intent(MainActivity.this, Help.class);
                        startActivity(helpIntent);
                        break;
                    case R.id.my_privacy:
                        Intent privacyIntent = new Intent(MainActivity.this, Privacy.class);
                        startActivity(privacyIntent);
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });

    }



    private void setPendingRecycler(List<PendingOrder> pendingOrderList) {
        pendingRecycler = findViewById(R.id.pending_recycler5);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        pendingRecycler.setLayoutManager(layoutManager);
        pendingOrderAdapter = new PendingOrderAdapter(this, pendingOrderList);
        pendingRecycler.setAdapter(pendingOrderAdapter);
    }




}
