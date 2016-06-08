package com.context.kroket.escapeapp.minigames;

import android.content.BroadcastReceiver;
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
import android.widget.TextView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.network.ConnectionService;

import java.util.ArrayList;
import java.util.List;

public class F_Lock extends AppCompatActivity {
    ImageButton left, right;
    ImageView turnlock;
    boolean rotatingRight;
    ArrayList<Integer> enteredSequence;
    ConnectionService connectionService;
    boolean serviceIsBound = false;
    static ArrayList<Integer> correctSequence;

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
        setContentView(R.layout.f__lock);
        addListenersToArrows();
        turnlock = (ImageView) findViewById(R.id.turnlock);
        enteredSequence = new ArrayList<Integer>();
        establishConnection();
    }

    private void establishConnection() {
        ServiceConnection mConnection = new ServiceConnection() {

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
    }

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
            System.out.println(enteredSequence.toString());
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
            System.out.println(enteredSequence.toString());

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
            enteredSequence.add(Math.round(turnlock.getRotation() / -9));
        }
        if (enteredSequence.size() > 3) {
            enteredSequence = toArrayList(enteredSequence.subList(1, enteredSequence.size()));
        }
        checkGameComplete();
    }

    private ArrayList<Integer> toArrayList(List<Integer> list) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i=0;i<list.size();i++){
            result.add(list.get(i));
        }
        return result;
    }

    private void checkGameComplete() {
      if(enteredSequence.equals(correctSequence)){
        connectionService.doneF();
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

        //Change the current activity.
        ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
    }
    private static class Receiver extends BroadcastReceiver {

        /**
         * Defines what should happen when a message is received.
         *
         * @param context The Context in which the receiver is running.
         * @param intent  The Intent being received.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> params = (ArrayList<String>) intent.getExtras().get("lockSequence");
            parseSequence(params);
        }

        private void parseSequence(ArrayList<String> params) {
            correctSequence = new ArrayList<Integer>();
            for(int i=0;i<params.size();i++){
                correctSequence.add(Integer.parseInt(params.get(i)));
            }
        }
    }
}

