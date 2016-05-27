package com.context.kroket.escapeapp.mainscreens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.R;

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
}
