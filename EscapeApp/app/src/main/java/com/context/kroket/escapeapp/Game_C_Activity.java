package com.context.kroket.escapeapp;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game_C_Activity extends AppCompatActivity {


    UpdateReceiver updateReceiver;

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
        setContentView(R.layout.activity_game__c_);
    }

    @Override
    protected void onStart() {

        updateReceiver = new UpdateReceiver();
        registerReceiver(updateReceiver, new IntentFilter("colorBroadcast"));

        super.onStart();

        //Change the current activity to this
        ((App)this.getApplicationContext()).setCurrentActivity(this);
    }

    /**
     * Called when the start button in minigame C is clicked.
     * Starts the color range.
     *
     * @param view
     */
    public void startC(View view) {

        //setColor(Color.WHITE, 1000);
        setColor(Color.RED, 200);
        setColor(Color.BLUE, 200);
        setColor(Color.YELLOW, 200);

//        shape.setColor(Color.BLACK);
//        shape.clearColorFilter();
//        shape.setColor(Color.WHITE);
    }

    /**
     * Sets a color for a specific amount of time.
     * @param color the color to be set
     * @param time the time the color should be shown in ms
     */
    public void setColor(int color, long time) {
        View v = findViewById(R.id.layout_rec);
        LayerDrawable bgDrawable = (LayerDrawable)v.getBackground();
        final GradientDrawable shape = (GradientDrawable)   bgDrawable.findDrawableByLayerId(R.id.shape_rec);

//        long startTime = System.currentTimeMillis();
//        long currentTime = System.currentTimeMillis();
//
//
//        shape.setColor(color);
//        while (currentTime - startTime < time) {
//            currentTime = System.currentTimeMillis();
//        }


    }

    /**
     * Class for receiving broadcasts.
     */
    private static class UpdateReceiver extends BroadcastReceiver {

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
        }
    }

}
