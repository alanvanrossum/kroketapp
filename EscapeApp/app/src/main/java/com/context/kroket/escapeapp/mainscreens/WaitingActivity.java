package com.context.kroket.escapeapp.mainscreens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.minigames.D_Gyroscope;

/**
 * Responsible for the screen that is shown when no minigame is active.
 */
public class WaitingActivity extends AppCompatActivity {

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
        setContentView(R.layout.waiting_activity);
    }

    /**
     * Start the activity. Change current activity to this.
     */
    @Override
    protected void onStart() {
        super.onStart();

        //Change the current activity.
        ((ActivityManager)this.getApplicationContext()).setCurrentActivity(this);
    }

    /**
     * Method that starts minigame D.
     *
     * @param view the view that was clicked.
     */
    public void startButtonWaiting(View view) {
        Intent intent = new Intent(this, D_Gyroscope.class);
        startActivity(intent);
    }
        
    @Override
    public void onBackPressed() {
    }
}
