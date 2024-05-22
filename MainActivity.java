package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.ListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private Button play;

    private Button topPlayer;

    private FirebaseAuth auth;

    private Button out;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate (Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView((R.layout.activity_main));
        Objects.requireNonNull(getSupportActionBar()).hide();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        if (auth.getCurrentUser()== null){
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finishAffinity();

        }

        updateChecker();

        out =  findViewById(R.id.out);

        play =  findViewById(R.id.play);

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, login.class));
                finishAffinity();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayButton();
            }
        });

        topPlayer = findViewById(R.id.topPlayer);
        topPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });

    }

    private void updateChecker() {

        databaseReference.child("update").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String version_code;
                try{
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    version_code = String.valueOf(pInfo.versionCode);
                    if (!version_code.equals(snapshot.child("v").getValue())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        // Set the message show for the Alert time
                        builder.setMessage("New version update ?");

                        // Set Alert Title
                        builder.setTitle("Alert !");

                        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                        builder.setCancelable(false);

                        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                        builder.setPositiveButton("Update", (DialogInterface.OnClickListener) (dialog, which) -> {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()));
                            startActivity(i);

                        });

                        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                            // If user click no then dialog box is canceled.
                            dialog.cancel();
                        });

                        // Create the Alert dialog
                        AlertDialog alertDialog = builder.create();
                        // Show the Alert Dialog box
                        alertDialog.show();
                    }


                } catch (PackageManager.NameNotFoundException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openPlayButton() {
        Intent intent = new Intent(this, PlayButton.class);
        startActivity(intent);
    }





}