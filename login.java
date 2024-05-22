package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.model.ListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class login extends AppCompatActivity {
    FirebaseAuth auth;


    EditText username;

    EditText password;

    Button button;

    Button register;
    ProgressDialog bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        auth = FirebaseAuth.getInstance();
       username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openregister();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")){
                    username.setError("Enter your email");
                } else if (password.getText().toString().equals("")) {
                    password.setError("Enter your password");

                }else {
                    getUserData(username.getText().toString(), password.getText().toString());

                }
            }


        });

        bar = new ProgressDialog(this);

        bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        bar.setMessage("Loading");
        bar.setCancelable(false);


    }

    private void getUserData(String m, String p) {

        bar.show();

        auth.signInWithEmailAndPassword(m, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                bar.hide();
                if(task.isSuccessful()) {
                    Log.d(TAG, "Sign in with email successful");
                    startActivity(new Intent(login.this, MainActivity.class));

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        bar.hide();
                        Toast.makeText(login.this, "Login fail try again", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.toString());
                    }
                });
    }

    public void openregister() {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
}