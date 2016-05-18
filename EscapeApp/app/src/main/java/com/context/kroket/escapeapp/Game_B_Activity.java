package com.context.kroket.escapeapp;

import android.content.Intent;
import android.os.Handler;
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
                    ((TextView) findViewById(R.id.amount)).setText("Great job! You unlocked the following part of the code: 548");
                } else {
                    //Goal not reached. Able to start game again.
                    ((TextView) findViewById(R.id.amount)).setText("Too bad! \nClick restart to try again.");
                    ((Button) findViewById(R.id.restartButton)).setVisibility(View.VISIBLE);
                    ((Button) findViewById(R.id.restartButton)).setEnabled(true);
                }
            }
        }
    };

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
        if (amount == -1) {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
            ((Button) view).setText("CLICK ME!");
            amount++;
        } else if (seconds >= 0){
            amount++;
            ((TextView) findViewById(R.id.amount)).setText("Times clicked: " + amount);
        }
    }
}
