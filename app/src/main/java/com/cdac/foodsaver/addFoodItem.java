package com.cdac.foodsaver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addFoodItem extends AppCompatActivity {
    ImageView backButton;
    Button addFoodItem;
    Spinner sp;
    EditText iName, pTime;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_food_item);
        iName = findViewById(R.id.ItemName2);
        pTime = findViewById(R.id.perishTime);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sp =findViewById(R.id.foodType_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.foodType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp.setAdapter(adapter);
        addFoodItem = findViewById(R.id.addItemList_btn);
        addFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodType = sp.getSelectedItem().toString().trim();
                String itemName2 = iName.getText().toString().trim();
                String perishTime = pTime.getText().toString().trim();

                if(foodType.isEmpty() || itemName2.isEmpty() || perishTime.isEmpty()){

                    Toast.makeText(addFoodItem.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else {
                    DocumentReference documentReference = firebaseFirestore.collection("Pending Order").document(firebaseUser.getUid()).collection("My pendind order").document();
                    Map<String, Object> list =new HashMap<>();
                    list.put("FoodType", foodType);
                    list.put("ItemName",itemName2);
                    list.put("PerishTime",perishTime);

                    documentReference.set(list).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(addFoodItem.this, "Item added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(addFoodItem.this, createFoodListing.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            Toast.makeText(addFoodItem.this, "Failed to add item", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }
}