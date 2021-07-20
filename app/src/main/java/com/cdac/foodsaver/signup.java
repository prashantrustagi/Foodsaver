package com.cdac.foodsaver;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    TextView loginNow;
    /*Spinner spinner;*/
    EditText  email, password, location;
    Button createAcc;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    final String[] userID = new String[1];





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.pswd_signup);
        fStore = FirebaseFirestore.getInstance();
        createAcc = findViewById(R.id.createAcc_btn);
        fAuth = FirebaseAuth.getInstance();
        location = findViewById(R.id.location_signup);

       /*spinner = findViewById(R.id.spinner);*/


        // Create an ArrayAdapter using the string array and a default spinner layout
/*
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
*/
        // Specify the layout to use when the list of choices appears
        /*adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        // Apply the adapter to the spinner
       /* spinner.setAdapter(adapter);*/

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                /*String userType = spinner.getSelectedItem().toString().trim();*/
                String Location = location.getText().toString();


                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(signup.this, "Please Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(signup.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Location)) {
                    Toast.makeText(signup.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (TextUtils.isEmpty(userType)) {
                    Toast.makeText(signup.this, "Please select your role", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if (Password.length() < 6) {
                    password.setError("Password must be greater than 6 characters.");
                    return;
                }
                // Registering user in the firebase
                fAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            email.setText("");
                            password.setText("");
                            location.setText("");

                            Toast.makeText(signup.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID[0] = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Restaurant").document(userID[0]);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Location", Location);
                            user.put("email", Email);
                            /*user.put("User Type", userType);*/

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    Toast.makeText(signup.this, "User Created", Toast.LENGTH_SHORT).show();

                                }
                            });


                        } else {
                            Toast.makeText(signup.this, "Error!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginNow = findViewById(R.id.loginNow_txt);
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, Login.class);
                startActivity(intent);
            }
        });
    }

}