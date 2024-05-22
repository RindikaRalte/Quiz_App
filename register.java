package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class register extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    EditText name;

    EditText mail;

    ProgressDialog progressDialog;

    EditText pass;

    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        name = findViewById(R.id.name);

        mail = findViewById(R.id.mail);

        pass = findViewById(R.id.pass);

        enter = findViewById(R.id.enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")){
                    name.setError("Enter your username");
                } else if (mail.getText().toString().equals("")) {
                    mail.setError("Enter your gmail");

                } else if (pass.getText().toString().equals("")) {
                    pass.setError("enter your password");

                } else if (pass.getText().length() < 6) {
                    pass.setError("Password must contain 6 words");

                } else {
                    progressDialog.setMessage("Wait");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    auth.createUserWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("username", name.getText().toString());
                                        hashMap.put("email", mail.getText().toString());
                                        hashMap.put("password", pass.getText().toString());
                                        hashMap.put("user_id" , auth.getCurrentUser().getUid());

                                        firebaseFirestore.collection("user").document(auth.getCurrentUser().getUid())
                                                .set(hashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        startActivity(new Intent(register.this, MainActivity.class));
                                                        finishAffinity();
                                                    }
                                                });







                                    }else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            progressDialog.dismiss();
                                            Toast.makeText(register.this, "Account already exist", Toast.LENGTH_SHORT).show();
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(register.this, "Cannot register", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TAG",  e.getMessage().toString());
                                    progressDialog.hide();
                                    Toast.makeText(register.this, "Fail" , Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });


    }


}