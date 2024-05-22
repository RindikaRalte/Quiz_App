package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.material3.ProgressIndicatorDefaults;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.ListActivity;
import com.example.myapplication.model.ScoreModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GamePlay extends AppCompatActivity {

    ArrayList<ScoreModel> arraylist;

    FirebaseFirestore firebaseFirestore;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;

    TextView title;

    ProgressDialog bar;

    String answer;

    Button next;
    
    int doc = 0;
    int point = 0;

    Intent intent;

    String choose;

    FirebaseAuth userData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        Objects.requireNonNull(getSupportActionBar()).hide();
        arraylist = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        title = findViewById(R.id.title);
        next = findViewById(R.id.next);
        next.setEnabled(false);
        intent = getIntent();
        userData = FirebaseAuth.getInstance();

        answer1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        answer2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        answer3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        answer4.setBackgroundColor(Color.parseColor("#FFFFFF"));

        
        bar = new ProgressDialog(this);

        bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        bar.setMessage("Loading");
        bar.setCancelable(false);
        bar.show();
        loadData(doc);
        
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("point", choose);
                ScoreModel s1 = new ScoreModel(choose, answer,answer1.getText().toString(),answer2.getText().toString(),answer3.getText().toString(),answer4.getText().toString());
                answer1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                answer2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                answer3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                answer4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                arraylist.add(s1);
                next.setEnabled(false);
                answer1.setEnabled(true);
                answer1.setChecked(false);
                answer2.setEnabled(true);
                answer2.setChecked(false);
                answer3.setEnabled(true);
                answer3.setChecked(false);
                answer4.setEnabled(true);
                answer4.setChecked(false);
                bar.show();
                doc++;
                loadData(doc);


            }
        });

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!next.isEnabled()) {
                    choose = answer1.getText().toString();
                }



                next.setEnabled(true);
                answer2.setEnabled(false);
                answer3.setEnabled(false);
                answer4.setEnabled(false);



                if(answer1.getText().toString().equals(answer))
                {
                    answer1.setBackgroundColor(Color.parseColor("#00FF00"));
                    point++;
                }

                else{

                    answer1.setBackgroundColor(Color.parseColor("#FF0000"));

                    if (answer.equals(answer1.getText().toString()))
                    {
                        answer4.setBackgroundColor(Color.parseColor("#00FF00"));
                    } else if (answer.equals(answer4.getText().toString())) {

                        answer2.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                    else if (answer.equals(answer3.getText().toString())) {

                        answer3.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!next.isEnabled()) {
                    choose = answer2.getText().toString();
                }
                next.setEnabled(true);
                answer1.setEnabled(false);
                answer3.setEnabled(false);
                answer4.setEnabled(false);
                if(answer2.getText().toString().equals(answer))
                {
                    answer2.setBackgroundColor(Color.parseColor("#00FF00"));
                    point++;
                }

                else{
                    answer2.setBackgroundColor(Color.parseColor("#FF0000"));


                    if (answer.equals(answer1.getText().toString()))
                    {
                        answer1.setBackgroundColor(Color.parseColor("#00FF00"));
                    } else if (answer.equals(answer4.getText().toString())) {

                        answer4.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                    else if (answer.equals(answer3.getText().toString())) {

                        answer3.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!next.isEnabled()) {
                    choose = answer3.getText().toString();
                }
                next.setEnabled(true);
                answer2.setEnabled(false);
                answer1.setEnabled(false);
                answer4.setEnabled(false);
                if(answer3.getText().toString().equals(answer))
                {
                    answer3.setBackgroundColor(Color.parseColor("#00FF00"));
                    point++;
                }

                else{
                    answer3.setBackgroundColor(Color.parseColor("#FF0000"));




                    if (answer.equals(answer1.getText().toString()))
                    {
                        answer1.setBackgroundColor(Color.parseColor("#00FF00"));
                    } else if (answer.equals(answer2.getText().toString())) {

                        answer2.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                    else if (answer.equals(answer4.getText().toString())) {

                        answer4.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!next.isEnabled()) {
                    choose = answer4.getText().toString();
                }
                next.setEnabled(true);
                answer2.setEnabled(false);
                answer3.setEnabled(false);
                answer1.setEnabled(false);
                if(answer4.getText().toString().equals(answer))
                {
                    answer4.setBackgroundColor(Color.parseColor("#00FF00"));
                    point++;
                }

                else{

                    answer4.setBackgroundColor(Color.parseColor("#FF0000"));

                    if (answer.equals(answer1.getText().toString()))
                    {
                        answer1.setBackgroundColor(Color.parseColor("#00FF00"));
                    } else if (answer.equals(answer2.getText().toString())) {

                        answer2.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                    else if (answer.equals(answer3.getText().toString())) {

                        answer3.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                }
            }
        });


    }

    private void loadData(int doc) {
        firebaseFirestore.collection(Objects.requireNonNull(intent.getStringExtra("d"))).document(String.valueOf(doc)).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        bar.dismiss();
                        if (documentSnapshot.exists())
                        {
                            answer1.setText(Objects.requireNonNull(documentSnapshot.get("answer1")).toString());
                            answer2.setText(Objects.requireNonNull(documentSnapshot.get("answer2")).toString());
                            answer3.setText(Objects.requireNonNull(documentSnapshot.get("answer3")).toString());
                            answer4.setText(Objects.requireNonNull(documentSnapshot.get("answer4")).toString());
                            title.setText(Objects.requireNonNull(documentSnapshot.get("title")).toString());
                            answer = Objects.requireNonNull(documentSnapshot.get("crt_answer")).toString();
                        } else
                        {
                            point();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        bar.dismiss();
                        GamePlay.super.onBackPressed();
                        Log.e("Failed", e.toString());
                    }
                });
    }

    private void point() {

        for (ScoreModel score :
                arraylist) {
            Log.e("ScoreArray", ""+score);
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(GamePlay.this);


        builder.setMessage("You'r point is " + point);


        builder.setTitle("You'r score is  ");


        builder.setCancelable(false);


        builder.setPositiveButton("Finish", (DialogInterface.OnClickListener) (dialog, which) -> {

            finish();
        });


        builder.setNegativeButton("Check", (DialogInterface.OnClickListener) (dialog, which) -> {
            saveUserData();

        });


        AlertDialog alertDialog = builder.create();

        alertDialog.show();

    }

    private void saveUserData() {
        bar.show();
        firebaseFirestore.collection("user") .document(userData.getCurrentUser().getUid()+intent.getStringExtra("d"))
                        .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("username", documentSnapshot.get("username"));
                                        hashMap.put("points", String.valueOf(point));
                                        hashMap.put("cat", intent.getStringExtra("d"));
                                        hashMap.put("user_id" , userData.getCurrentUser().getUid());
                                        firebaseFirestore.collection("scorePoints" ) .document(userData.getCurrentUser().getUid()+ intent.getStringExtra("d"))
                                                .set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        startActivity(new Intent(GamePlay.this, ListActivity.class));
                                                        finishAffinity();
                                                    }
                                                });
                                    }
                                });

    }


}