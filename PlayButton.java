package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class PlayButton extends AppCompatActivity {
    private TextView cmps;

    private TextView mzh;

    private TextView geo;

    private TextView bible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_button);
        Objects.requireNonNull(getSupportActionBar()).hide();

        cmps =  (TextView) findViewById(R.id.cmps);
        cmps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGamePlay("computer_science");
            }
        });

        mzh = (TextView) findViewById(R.id.mzh);
        mzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGamePlay("mizo_history");
            }
        });

        geo = (TextView) findViewById(R.id.geo);
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGamePlay("geography");
            }
        });

        bible = (TextView) findViewById(R.id.bible);
        bible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openGamePlay("bible");

            }
        });




    }

    public void openGamePlay(String str) {
        Intent intent = new Intent(this, GamePlay.class)
                .putExtra("d", str);
        startActivity(intent);
    }


}