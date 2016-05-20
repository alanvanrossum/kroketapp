package com.context.kroket.escapeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * This activity is part of the minigame A.
 * Responsible for showing the assignment.
 */
public class Game_AA_Activity extends AppCompatActivity {


    /**
     * Initializes the layout.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__aa_);
    }

    /**
     * When the picture button is clicked, the Game_A_Activity is started.
     *
     * @param view
     */
    public void picButton(View view) {

        Intent intent = new Intent(this, Game_A_Activity.class);
        startActivity(intent);
    }


}
