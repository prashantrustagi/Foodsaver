package com.cdac.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ngologin extends AppCompatActivity {
    TextView signupTxt;
    EditText email, password;
    Button Loginbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ngologin);

        email = findViewById(R.id.email_loginngo);
        password = findViewById(R.id.psws_loginngo);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        signupTxt = findViewById(R.id.signup_txtngo);

        Loginbtn = findViewById(R.id.login_btnngo);
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(ngologin.this, "Please Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(ngologin.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Password.length() < 6) {
                    password.setError("Password must be greater than 6 characters.");
                    return;
                }

                // Registering user in the firebase

                fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            email.setText("");
                            password.setText("");

                            Toast.makeText(ngologin.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ngologin.this, ngodashboard.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(ngologin.this, "Error!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ngologin.this, ngosignup.class);
                startActivity(intent);
            }
        });
    }
}