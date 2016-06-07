package com.context.kroket.escapeapp.minigames;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.network.ConnectionService;
import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.WaitingActivity;


import java.util.ArrayList;

/**
 * This activity is responsible for minigame B.
 */
public class B_TapGame extends AppCompatActivity {

    long startTime;
    long timeLimit = 40000;     //Amount of time to click.
    int goal = 125;             //The amount of times that should be clicked.
    int amount = -1;            //Amount = -1 when the timer has not started yet.
    int seconds;
    public static boolean done = false;

    TextView timer;
    TextView clickButton;
    TextView amountView;
    TextView buttonView;

    //Receiver for broadcastst.
    Receiver updateReceiver;

    static ArrayList<String> buttons = new ArrayList<>();

    static ArrayList<String> sequences = new ArrayList<String>();

    ConnectionService connectionService;
    boolean serviceIsBound = false;

    Handler timerHandler = new Handler();

    //Handles actions that should happen when timer has certain values.
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = timeLimit - (System.currentTimeMillis() - startTime);
            seconds = (int) (millis / 1000);
            seconds = seconds % 60;

            //Update according to the time left.
            if (seconds >= 0) {
                timeLeft();
            } else {
                outOfTime();
            }
        }
    };


    /**
     * Defines what should happen (update) when there is still time left.
     */
    private void timeLeft() {
        timer.setText(String.format("%2d", seconds));
        timerHandler.postDelayed(timerRunnable, 1000);
    }

    /**
     * Defines what should happen when the player is out of time.
     */
    private void outOfTime() {
        //Out of time: stop timer and update textfield.
        timerHandler.removeCallbacks(timerRunnable);
        clickButton.setText("TIME'S UP!");
        //The goal is reached: send message to the server.
        if (amount >= goal) {
            connectionService.verifyB();

        } else {

            amountView.setText("Too bad!");

            //Goal not reached restart minigame B.
            connectionService.startB();

        }
    }

    //Defines callbacks for service binding, used in bindService().
    private ServiceConnection mConnection = new ServiceConnection() {

        /**
         * Called when a connection to the Service has been established.
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
     * Binds to ConnectionService.
     */
    @Override
    protected void onStart() {

        updateReceiver = new Receiver();
        registerReceiver(updateReceiver, new IntentFilter("buttonBroadcast"));


        super.onStart();

        Intent i = new Intent(this, ConnectionService.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);

        //Change the current activity to this
        ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);

    }

    /**
     * Method to restart the minigame B.
     *
     * @param view the view that was clicked.
     */
    public void restartButton(View view) {

        //TO do sent a restart message to the player
        Intent intent = new Intent(this, B_TapGame.class);
        startActivity(intent);
    }

    /**
     * Initializes the layout.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_tap_game);

        timer = (TextView) findViewById(R.id.timer);
        clickButton = ((TextView) findViewById(R.id.clickButton));
        amountView = ((TextView) findViewById(R.id.amount));
        buttonView = ((TextView) findViewById(R.id.buttonSequence));

    }

    /**
     * Executes when the clickButton is clicked.
     * Increases the count by 1.
     *
     * @param view the view that was clicked.
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
                showButtons();
                amountView.setText("Times clicked: " + amount);
            }
        } else {
            clickButton.setText("Finish");
            Intent i = new Intent(this, WaitingActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    /**
     * updates the buttonView text view every 25 clicks.
     */
    public void showButtons() {
        if (amount == 25) {
            buttonView.setText("First sequence: " + sequences.get(0));
        }
        if (amount == 50) {
            buttonView.setText("Second sequence: " + sequences.get(1));
        }
        if (amount == 75) {
            buttonView.setText("Third sequence: " + sequences.get(2));
        }
        if (amount == 100) {
            buttonView.setText("Fourth sequence: " + sequences.get(3));
        }

    }


    /**
     * Class for receiving broadcasts.
     */
    private static class Receiver extends BroadcastReceiver {

        /**
         * Defines what should happen when a message is received.
         *
         * @param context The Context in which the receiver is running.
         * @param intent  The Intent being received.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            buttons = (ArrayList<String>) intent.getExtras().get("buttonSequence");

            String addString = "";

            for (int x = 0; x < 4; x++) {
                sequences.add(addString);
            }

            for (int k = 0; k <= 3; k++) {

                for (int i = 4 * k + 1; i <= 4 * k + 4; i++) {
                    String temp = sequences.get(k);
                    if (i == 4 * k + 1) {
                        temp = buttons.get(i);
                    } else {
                        temp = temp + " + " + buttons.get(i);
                    }
                    sequences.set(k, temp);
                }
            }

        }
    }
}



