package com.cdac.foodsaver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.foodsaver.adapter.PendingOrderAdapter;
import com.cdac.foodsaver.adapter.firebaseAdapter;
import com.cdac.foodsaver.model.PendingOrder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView pendingRecycler;
    TextView viewAll;
    ImageView imageView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    FloatingActionButton fab;
    MenuItem menuItem;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private firebaseAdapter adapter;
    // FirestoreRecyclerAdapter<firebasemodel, listViewHolder> pendingAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        drawerLayout = findViewById(R.id.drawerLayout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore =  FirebaseFirestore.getInstance();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fabBtn = new Intent(MainActivity.this,createFoodListing.class);
                startActivity(fabBtn);
            }
        });

        setUpRecyclerView();
   /*
       pendingAdapter = new FirestoreRecyclerAdapter<firebasemodel, listViewHolder>(allList){


    @Override
    protected void onBindViewHolder(@NonNull listViewHolder listViewHolder, int i, @NonNull firebasemodel firebasemodel){
        listViewHolder.foodType.setText(firebasemodel.getFoodType());
        listViewHolder.food.setText(firebasemodel.getItemName());
        listViewHolder.Ptime.setText(firebasemodel.getPerishTime());
    }
    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_pending_order_list,parent,false);
        return new listViewHolder(view);
    }

};*/




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
                    case R.id.logout:
                        Intent logoutintent= new Intent(MainActivity.this, Login.class);
                        startActivity(logoutintent);
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });

    }

    private void setUpRecyclerView(){

        Query query = firebaseFirestore.collection("Restaurant").document(firebaseUser.getUid()).collection("Pending order");
        // getting query into adapter
        FirestoreRecyclerOptions<firebasemodel> allList = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();

        adapter = new firebaseAdapter(allList);

        // method to setup recycler view
        pendingRecycler = findViewById(R.id.pending_recycler5);
        pendingRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        pendingRecycler.setLayoutManager(layoutManager);
        pendingRecycler.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //  position of the item to be deleted
                                int position = viewHolder.getAdapterPosition();
                                // removing this item from the adapter
                                adapter.deleteItem(position);
                                Toast.makeText(MainActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                }).create().show();
            }
        }).attachToRecyclerView(pendingRecycler);
    }


/*
    public class listViewHolder extends RecyclerView.ViewHolder
    {

        TextView food, foodType,Ptime;
        public listViewHolder(@NonNull View itemView) {
            super(itemView);

            food = itemView.findViewById(R.id.item_name_rec);
            foodType = itemView.findViewById(R.id.food_type_rec);
            Ptime = itemView.findViewById(R.id.perish_time_rec);

        }
    }*/


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!= null)
        {
            adapter.startListening();
        }
    }



}
