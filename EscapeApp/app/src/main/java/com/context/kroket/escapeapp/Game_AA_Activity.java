package com.context.kroket.escapeapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * This activity is part of the minigame A.
 * Responsible for showing the assignment.
 */
public class Game_AA_Activity extends AppCompatActivity {

    //The answer to this minigame
    public final String correctCode = "1234";
    ConnectionService connectionService;
    boolean serviceIsBound = false;

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

        findViewById(R.id.verifyMessage).setVisibility(View.INVISIBLE);
    }

    /**
     * Binds to ConnectionService
     */
    @Override
    protected void onStart() {
        super.onStart();

        Intent i = new Intent(this, ConnectionService.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
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

    /**
     * Verifies if the code that is entered is correct.
     *
     * @param view
     */
    public void verifyButton(View view) {
        EditText answer = (EditText) findViewById(R.id.answerA);

        //check if the code is correct
        if (answer.getText().toString().matches(correctCode)) {
            //Send message to server that minigame A is finished
            if (serviceIsBound) {
                connectionService.endA();
            } else {
                System.out.println("ConnectionService not bound in Game AA");
            }

            //Go to the waiting screen
            Intent intent = new Intent(this, WaitingActivity.class);
            startActivity(intent);
        } else {
            findViewById(R.id.verifyMessage).setVisibility(View.VISIBLE);
        }
    }

}
