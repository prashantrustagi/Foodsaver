package com.cdac.foodsaver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.foodsaver.adapter.PendingOrderAdapter;
import com.cdac.foodsaver.adapter.firebaseAdapter;
import com.cdac.foodsaver.model.PendingOrder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    ImageView backButton;
    RecyclerView viewAllRecycler;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private firebaseAdapter adapter;

    // FirestoreRecyclerAdapter<firebasemodel,listViewHolder> pendingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_all);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();



       /* pendingAdapter = new FirestoreRecyclerAdapter<firebasemodel, listViewHolder>(allList){
            @Override
            protected void onBindViewHolder(@NonNull listViewHolder listViewHolder , int i, @NonNull firebasemodel firebasemodel)
            {
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


        setUpRecyclerView();





    /*public class listViewHolder extends RecyclerView.ViewHolder
    {

        TextView food, foodType,Ptime;
        public listViewHolder(@NonNull View itemView) {
            super(itemView);

            food = itemView.findViewById(R.id.item_name_rec);
            foodType = itemView.findViewById(R.id.food_type_rec);
            Ptime = itemView.findViewById(R.id.perish_time_rec);

        }
    }*/
    }

    private void setUpRecyclerView(){

        Query query = firebaseFirestore.collection("Restaurant").document(firebaseUser.getUid()).collection("Pending order");
        // getting query into adapter
        FirestoreRecyclerOptions<firebasemodel> allList = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();

        adapter = new firebaseAdapter(allList);

        // method to setup recycler view
        viewAllRecycler = findViewById(R.id.viewAllRecycler);
        viewAllRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        viewAllRecycler.setLayoutManager(layoutManager);
        viewAllRecycler.setAdapter(adapter);

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
                                Toast.makeText(ViewAllActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                }).create().show();
            }
        }).attachToRecyclerView(viewAllRecycler);
    }
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


