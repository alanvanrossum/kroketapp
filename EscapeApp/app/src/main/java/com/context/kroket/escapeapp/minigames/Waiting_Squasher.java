package com.context.kroket.escapeapp.minigames;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.network.ConnectionService;

import java.util.ArrayList;
import java.util.Random;

public class Waiting_Squasher extends AppCompatActivity {

    ImageButton bugButton;
    TextView squashText;
    float screenHeight;
    float screenWidth;
    int squashCount;
    Random rand;
    Drawable spider;
    Drawable bug;
    Drawable beetle;
    Drawable mosquito;
    Drawable sowbug;

    ArrayList<Drawable> bugs = new ArrayList<>();

    @TargetApi(21)
    private Drawable getDrawableObject(int id) {
        return (android.os.Build.VERSION.SDK_INT >= 21 ? getResources().getDrawable(id,
                getApplicationContext().getTheme()) : getResources().getDrawable(id));
    }

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting__squasher);

        initScreen();
        initBugs();

        rand = new Random();

        // Bind this service.
        Intent intent = new Intent(this, ConnectionService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
    }

    /**
     * Initializes the screen properties.
     */
    private void initScreen() {
        addListenerToBugButton();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenHeight *= 0.7;
        screenWidth = metrics.widthPixels;
        screenWidth *= 0.7;
        squashCount = 0;
        squashText = (TextView) findViewById(R.id.squashtext);
        squashText.setText("Squash the Bugs!");
    }

    /**
     * Initializes the bugs by getting the images of the bugs.
     */
    private void initBugs() {
        spider = getDrawableObject(R.drawable.spider);
        bug = getDrawableObject(R.drawable.bug);
        beetle = getDrawableObject(R.drawable.beetle);
        mosquito = getDrawableObject(R.drawable.mosquito);
        sowbug = getDrawableObject(R.drawable.sowbug);

        bugs.add(spider);
        bugs.add(bug);
        bugs.add(beetle);
        bugs.add(mosquito);
        bugs.add(sowbug);
    }

    /**
     * Adds a listerner to the bug button.
     */
    private void addListenerToBugButton() {
        bugButton = (ImageButton) findViewById(R.id.bugbutton);
        bugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                squashCount++;
                squashText = (TextView) findViewById(R.id.squashtext);

                if (squashCount >= 50) {
                    squashCount = 0;
                    squashText.setText("BONUS TIME RECEIVED!");
                    connectionService.bonusTime();
                } else {
                    squashText.setText("Bugs squashed: " + squashCount);
                }
                setRandomBugLocation();

            }

        });
    }

    /**
     * Picks a random location and sets the bug at this location.
     */
    private void setRandomBugLocation() {
        Random rand = new Random();
        int newX = rand.nextInt((int) screenWidth);
        int newY = rand.nextInt((int) screenHeight);
        bugButton.setX(newX);
        bugButton.setY(newY);
        setRandomImageSource();
        setRandomBugRotation();
    }

    /**
     * Chooses a random bug picture and sets it.
     */
    @TargetApi(21)
    private void setRandomImageSource() {
        int randInt = rand.nextInt(bugs.size());
        bugButton.setImageDrawable(bugs.get(randInt));
    }

    /**
     * Sets a random rotation for the bug.
     */
    private void setRandomBugRotation() {
        Random rand = new Random();
        int rotation = rand.nextInt(359);
        bugButton.setRotation(bugButton.getRotation() + (rotation));
    }

  /* Methods for the connection. */

    ConnectionService connectionService;
    boolean serviceIsBound = false;

    // Defines callbacks for service binding, used in bindService().
    private ServiceConnection mConnection = new ServiceConnection() {

        /**
         * Called when a connection to the Service has been established.
         *
         * @param className
         *          The concrete component name of the service that has been connected.
         * @param service
         *          The IBinder of the Service's communication channel, which you can now make calls on.
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
         * @param arg0
         *          The concrete component name of the service whose connection has been lost.
         */
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serviceIsBound = false;
        }
    };

    /**
     * Unbind the service and go back.
     */
    @Override 
    public void onBackPressed() {
        unbindService(mConnection);

        super.onBackPressed();
    }

}
