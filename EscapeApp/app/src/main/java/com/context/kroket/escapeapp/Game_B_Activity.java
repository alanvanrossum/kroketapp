package com.context.kroket.escapeapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This activity is responsible for minigame B.
 */
public class Game_B_Activity extends AppCompatActivity {

    TextView timer;
    long startTime;
    long timeLimit = 20000;
    int amount = -1;
    int seconds = 1;
    int goal = 125;
    boolean done = false;

    ConnectionService connectionService;
    boolean serviceIsBound = false;

    Handler timerHandler = new Handler();

    //Handles actions that should happen when timer has certain values
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = timeLimit - (System.currentTimeMillis() - startTime);
            seconds = (int) (millis / 1000);
            seconds = seconds % 60;

            //Still time left, update timer on screen
            if (seconds >= 0) {
                timer.setText(String.format("%2d", seconds));
                timerHandler.postDelayed(this, 1000);
            } else {
                //Out of time: stop timer and update textfield
                timerHandler.removeCallbacks(timerRunnable);
                ((TextView) findViewById(R.id.clickButton)).setText("TIME'S UP!");
                //The goal is reached: do something
                if (amount >= goal) {       //beide mobile players moeten een andere code krijgen?
                    connectionService.endB();
                    done = true;
                    //((TextView) findViewById(R.id.amount)).setText("Great job! You unlocked the following part of the code: 548");
                } else {
                    //Goal not reached. Able to start game again.
                    ((TextView) findViewById(R.id.amount)).setText("Too bad! \nClick restart to try again.");
                    ((Button) findViewById(R.id.restartButton)).setVisibility(View.VISIBLE);
                    ((Button) findViewById(R.id.restartButton)).setEnabled(true);
                }
            }
        }
    };

    //Defines callbacks for service binding, used in bindService()
    private ServiceConnection mConnection = new ServiceConnection() {

        /**
         * Called when a connection to the Service has been established
         *
         * @param className The concrete component name of the service that has
         * been connected.
         * @param service The IBinder of the Service's communication channel,
         * which you can now make calls on.
         */
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ConnectionService.myBinder binder = (ConnectionService.myBinder) service;
            connectionService = binder.getService();
            serviceIsBound = true;
        }

        /**
         * Called when a connection to the Service has been lost.
         *
         * @param arg0 The concrete component name of the service whose
         * connection has been lost.
         */
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serviceIsBound = false;
        }
    };

    /**
     * Binds to ConnectionService
     */
    @Override
    protected void onStart() {
        super.onStart();

        Intent i = new Intent(this, ConnectionService.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);

        //Change the current activity to this
        ((App)this.getApplicationContext()).setCurrentActivity(this);
    }

    /**
     * Method to restart the minigame B.
     *
     * @param view
     */
    public void restartButton(View view) {
            Intent intent = new Intent(this, Game_B_Activity.class);
            startActivity(intent);
    }

    /**
     * Initializes the layout.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__b_);

        timer = (TextView) findViewById(R.id.timer);
        ((Button) findViewById(R.id.restartButton)).setVisibility(View.INVISIBLE);
        ((Button) findViewById(R.id.restartButton)).setEnabled(false);
    }

    /**
     * Executes when the clickButton is clicked.
     * Increases the count by 1.
     *
     * @param view
     */
    public void clickButton(View view) {
        if (!done) {
            if (amount == -1) {
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                ((Button) view).setText("CLICK ME!");
                amount++;
            } else if (seconds >= 0) {
                amount++;
                ((TextView) findViewById(R.id.amount)).setText("Times clicked: " + amount);
            }
        } else {
            ((Button) findViewById(R.id.clickButton)).setText("Finish");
            Intent i = new Intent(this, WaitingActivity.class);
            startActivity(i);
        }
    }
}
