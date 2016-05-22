package com.context.kroket.escapeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * This activity is part of the minigame A.
 * Responsible for showing the picture.
 */
public class Game_A_Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_game_a);
    }

    /**
     * When the code button is clicked, the Game_AA_Activity is started.
     *
     * @param view
     */
    public void codeButton(View view) {
        Intent intent = new Intent(this, Game_AA_Activity.class);
        startActivity(intent);
    }

    /**
     * Start the activity. Change current activity to this.
     */
    @Override
    protected void onStart() {
        super.onStart();

        //Change the current activity
        ((App)this.getApplicationContext()).setCurrentActivity(this);
    }

}
