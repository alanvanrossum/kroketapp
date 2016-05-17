package com.context.kroket.escapeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Game_AA_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__aa_);
    }

    public void picButton(View view) {
        Intent intent = new Intent(this, Game_A_Activity.class);
        startActivity(intent);
    }
}
