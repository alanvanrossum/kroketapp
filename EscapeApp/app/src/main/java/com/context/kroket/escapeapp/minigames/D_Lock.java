package com.context.kroket.escapeapp.minigames;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.network.ConnectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Minigame where a code for a lock has to be entered.
 */
public class D_Lock extends AppCompatActivity {
    ImageButton left, right;
    ImageView turnlock;
    boolean rotatingRight;
    ArrayList<Integer> enteredSequence;
    ConnectionService connectionService;
    boolean serviceIsBound = false;
    ArrayList<Integer> correctSequence;

    /**
     * This method adds listeners to the arrow pictures by calling addListenersToArrows()
     * This method also sets the global variable imageView to match the turning lock, so it can be easily rotated.
     * Finally, this method creates a simple arraylist of integers to store the current value of the lock everytime the direction is changed.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_lock);
        addListenersToArrows();
        turnlock = (ImageView) findViewById(R.id.turnlock);
        enteredSequence = new ArrayList<Integer>();
        correctSequence = new ArrayList<Integer>();
        correctSequence.add(13);
        correctSequence.add(37);
        correctSequence.add(21);
    }

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
     * This is an initializing method that is called once during the creation of the activity.
     * This method adds a button function to the two arrows in the bottom of the screen, which make calls to rotateRight() and rotateLeft()
     */
    private void addListenersToArrows() {
        left = (ImageButton) findViewById(R.id.arrowleft);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rotateLeft();
            }

        });
        right = (ImageButton) findViewById(R.id.arrowright);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rotateRight();
            }

        });
    }

    /**
     * This method is called when the RightArrow is pressed
     * This method adds 9 to the rotation, and if the direction is changed it calls to addToSequence()
     */
    private void rotateRight() {
        if (!rotatingRight) {
            rotatingRight = true;
            addToSequence();
        }
        rotate(9);
    }

    /**
     * This method is called when the LeftArrow is pressed.
     * This method subtracts 9 from the rotation, and if the direction is changed it calls to addToSequence()
     */
    private void rotateLeft() {
        if (rotatingRight) {
            rotatingRight = false;
            addToSequence();
        }
        rotate(-9);
    }

    /**
     * This method is called when the user switches from turning Left to turning Right or vice versa.
     * This method adds the number the lock is currently at to the sequence.
     */
    private void addToSequence() {
        if (turnlock.getRotation() > 0) {
            enteredSequence.add(Math.round(40 - (turnlock.getRotation() / 9)));
        } else {
            enteredSequence.add(Math.round(turnlock.getRotation() / - 9));
        }
        if (enteredSequence.size() > 3) {
            enteredSequence = toArrayList(enteredSequence.subList(1, enteredSequence.size()));
        }
        checkGameComplete();
    }

    private ArrayList<Integer> toArrayList(List<Integer> integers) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for(int i = 0; i < integers.size(); i++){
            res.add(integers.get(i));
        }
        return res;
    }

    /**
     * Checks whether the entered sequence is correct.
     */
    private void checkGameComplete() {
        System.out.println("enterdsequence" + enteredSequence.toString());
        System.out.println("correctSequence" + correctSequence.toString());
      if(enteredSequence.equals(correctSequence)){
        connectionService.endD();
      }
    }

    /**
     * This method sets the rotation for the turning part of the lock. A rotation of 9 degrees corresponds to a rotation of one number.
     *
     * @param alpha The angle in which the lock must rotate. Can be 9 or -9 for positive or negative turning respectively.
     */
    private void rotate(int alpha) {
        turnlock.setRotation((turnlock.getRotation() + alpha) % 360);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent i = new Intent(this, ConnectionService.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);

        //Change the current activity to this
        ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
    }
}

