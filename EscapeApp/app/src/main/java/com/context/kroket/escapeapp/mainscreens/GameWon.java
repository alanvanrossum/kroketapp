package com.context.kroket.escapeapp.mainscreens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.context.kroket.escapeapp.R;

public class GameWon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_won);
    }

    @Override
    public void onBackPressed() {
    }
}
