package com.context.kroket.escapeapp.minigames;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.R;

import java.util.ArrayList;

public class C_ColorSequence extends AppCompatActivity {

    //The color sequence
    public static ArrayList<Integer> colorSequence;

    //Receiver for broadcastst
    Receiver updateReceiver;

    //Handler for the timer
    Handler timeHandler = new android.os.Handler();

    //Counter for position in the arraylist
    public int counter = 0;

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
        setContentView(R.layout.c_color_sequence);
    }

    @Override
    protected void onStart() {

        updateReceiver = new Receiver();
        registerReceiver(updateReceiver, new IntentFilter("colorBroadcast"));

        super.onStart();

        //Change the current activity to this
        ((ActivityManager)this.getApplicationContext()).setCurrentActivity(this);
    }

    /**
     * Called when the start button in minigame C is clicked.
     * Starts the color range.
     *
     * @param view
     */
    public void startC(View view) {
        //Show the color sequence
        timeHandler.postDelayed(updateColorThread, 0);
    }

    /**
     * Put the string with colors in an arraylist.
     *
     * @param c the string with colors.
     */
    public static void parseColors(String c) {
        colorSequence = new ArrayList<Integer>();
        String colors = c;
        String curCol = colors;
        int ind;

        while (!colors.equals("")) {
            ind = colors.indexOf(",");

            if (ind != -1) {
                curCol = colors.substring(0,ind);
                colors = colors.substring(ind + 1);
            } else {
                curCol = colors;
                colors = "";
            }

            switch (curCol) {
                case "RED": colorSequence.add(Color.RED);
                    break;
                case "GREEN": colorSequence.add(Color.GREEN);
                    break;
                case "BLUE": colorSequence.add(Color.BLUE);
                    break;
                case "YELLOW":  colorSequence.add(Color.YELLOW);
                    break;
            }
        }

        //Add white at the end, so the screen becomes white again
        colorSequence.add(Color.WHITE);
    }


    /**
     * Runnable to update the background color after a certain amout of time.
     */
    private Runnable updateColorThread = new Runnable() {
        public void run()
        {
            View v = findViewById(R.id.layout_rec);
            LayerDrawable bgDrawable = (LayerDrawable)v.getBackground();
            final GradientDrawable shape = (GradientDrawable)   bgDrawable.findDrawableByLayerId(R.id.shape_rec);
            shape.setColor(colorSequence.get(counter));

            counter++;

            timeHandler.postDelayed(this, 170);// will repeat after 5 seconds

            if (counter >= colorSequence.size()) {
                timeHandler.removeCallbacks(updateColorThread);

                //Set background back to white
                //shape.setColor(Color.WHITE);
                counter = 0;
                return;
            }
        }
    };


    /**
     * Class for receiving broadcasts.
     */
    private static class Receiver extends BroadcastReceiver {

        /**
         * Defines what should happen when a message is received.
         *
         * @param context The Context in which the receiver is running.
         * @param intent The Intent being received.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            String colors = intent.getStringExtra("colorSequence");
            System.out.println("colors received " + colors);
            parseColors(colors);
        }
    }

}
