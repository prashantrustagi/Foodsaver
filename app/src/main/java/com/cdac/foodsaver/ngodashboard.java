package com.cdac.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ngodashboard extends AppCompatActivity {
    TextView viewAll;
    RecyclerView ngorecView;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter<firebasemodel,listViewHolder> pendingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_ngodashboard);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore =  FirebaseFirestore.getInstance();

        viewAll = findViewById(R.id.viewAllngodash);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ngodashboard.this, ViewAllActivity.class);
                startActivity(intent);
            }
        });

        Query query = firebaseFirestore.collection("Restaurant").document(firebaseUser.getUid()).collection("Pending order");
        FirestoreRecyclerOptions<firebasemodel> allList = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();




           pendingAdapter = new FirestoreRecyclerAdapter<firebasemodel, listViewHolder>(allList){
               @Override
                protected void onBindViewHolder(@NonNull listViewHolder listViewHolder, int i, @NonNull firebasemodel firebasemodel) {
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

        };


        ngorecView = findViewById(R.id.ngodashrec);
        ngorecView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        ngorecView.setLayoutManager(layoutManager);
        ngorecView.setAdapter(pendingAdapter );

    }

    public class listViewHolder extends RecyclerView.ViewHolder
    {

        TextView food, foodType,Ptime;
        public listViewHolder(@NonNull View itemView) {
            super(itemView);

            food = itemView.findViewById(R.id.item_name_rec);
            foodType = itemView.findViewById(R.id.food_type_rec);
            Ptime = itemView.findViewById(R.id.perish_time_rec);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        pendingAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(pendingAdapter!= null)
        {
            pendingAdapter.startListening();
        }
    }
}
