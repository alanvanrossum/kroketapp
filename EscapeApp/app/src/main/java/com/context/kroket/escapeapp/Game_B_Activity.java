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

    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = timeLimit - (System.currentTimeMillis() - startTime);
            seconds = (int) (millis / 1000);
            seconds = seconds % 60;
            System.out.println("sec: " + seconds);

            if (seconds >= 0) {
                timer.setText(String.format("%2d", seconds));
                timerHandler.postDelayed(this, 1000);
            } else {
                timerHandler.removeCallbacks(timerRunnable);
                ((TextView) findViewById(R.id.clickButton)).setText("TIME'S UP!");
                if (amount >= goal) {       //beide mobile players moeten een andere code krijgen?
                    ((TextView) findViewById(R.id.amount)).setText("Great job! You unlocked the following part of the code: 548");
                } else {
                    ((TextView) findViewById(R.id.amount)).setText("Too bad! \nTry again in a few seconds.");
                    restart();
                }
            }
        }
    };

    //Restart the minigame
    public void restart() {
//        long current = System.currentTimeMillis();
//        while ((System.currentTimeMillis() - current) < 5000) {}
        Intent intent = new Intent(this, Game_B_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__b_);

        timer = (TextView) findViewById(R.id.timer);
    }

    //Increase the count by 1 when the button is clicked
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
