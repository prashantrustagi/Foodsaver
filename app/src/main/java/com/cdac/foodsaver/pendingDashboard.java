package com.cdac.foodsaver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.foodsaver.adapter.firebaseAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class pendingDashboard extends AppCompatActivity {
    ImageView backButton;
    RecyclerView recPendingList_nav;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private firebaseAdapter adapter;
    //FirestoreRecyclerAdapter<firebasemodel, listViewHolder> pendingAdapter;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore =  FirebaseFirestore.getInstance();
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        setUpRecyclerView();

        /*pendingAdapter = new FirestoreRecyclerAdapter<firebasemodel, listViewHolder>(allList){

            @Override
            protected void onBindViewHolder(@NonNull listViewHolder listViewHolder , int i, @NonNull firebasemodel firebasemodel){
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
*/

        recPendingList_nav = findViewById(R.id.recPendinglist_nav);
        recPendingList_nav.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recPendingList_nav.setLayoutManager(layoutManager);
        recPendingList_nav.setAdapter(adapter );


    }


    private void setUpRecyclerView(){

        Query query = firebaseFirestore.collection("Restaurant").document(firebaseUser.getUid()).collection("Pending order");
        // getting query into adapter
        FirestoreRecyclerOptions<firebasemodel> allList = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();

        adapter = new firebaseAdapter(allList);

        // method to setup recycler view
        recPendingList_nav = findViewById(R.id.recPendinglist_nav);
        recPendingList_nav.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recPendingList_nav.setLayoutManager(layoutManager);
        recPendingList_nav.setAdapter(adapter );


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
                                Toast.makeText(pendingDashboard.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                }).create().show();
            }
        }).attachToRecyclerView(recPendingList_nav);
    }
  /*  public class listViewHolder extends RecyclerView.ViewHolder
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
