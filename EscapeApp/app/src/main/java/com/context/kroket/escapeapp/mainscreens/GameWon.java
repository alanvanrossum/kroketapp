package com.context.kroket.escapeapp.mainscreens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.context.kroket.escapeapp.R;

/** Activity responsible for showing the game won screen. */
public class GameWon extends AppCompatActivity {

    /**
     * Initializes the layout.
     *
     * @param savedInstanceState
     *          If the activity is being re-initialized after previously being
     *          shut down then this Bundle contains the data it most recently
     *          supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_won);
    }

    /**
     * Disables going back to the previous activity.
     */
    @Override
    public void onBackPressed() {
    }
}
