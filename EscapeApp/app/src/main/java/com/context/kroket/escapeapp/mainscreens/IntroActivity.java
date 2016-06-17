package com.context.kroket.escapeapp.mainscreens;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;

import java.util.ArrayList;

/**
 * Activity responsible for showing the intro (storyline).
 */
public class IntroActivity extends AppCompatActivity {

    Handler timerHandler;

    ArrayList<String> texts;

    int pointer;

    TextView intoText;

    /**
     * Initializes the layout.
     *
     * @param savedInstanceState
     *          If the activity is being re-initialized after previously being
     *          shut down then this Bundle contains the data it most recently
     *          supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    /**
     * Start the activity. Change current activity to this.
     * Start the timer and init the texts list.
     */
    @Override
    protected void onStart() {
        super.onStart();

        timerHandler = new Handler();
        timerHandler.postDelayed(timerRunnable, 0);
        pointer = 0;
        intoText = (TextView) findViewById(R.id.introText);

        texts = new ArrayList<String>();
        texts.add("You are a CIA agent.\n\n" +
                "Together with your colleagues you are investigating the disappearance of a fellow CIA agent.");
        texts.add("One of your colleagues was following a lead in the woods, when suddenly he got knocked unconscious!");
        texts.add("He wakes up in a room, which is slowly filling up with a deadly gas!\n\n" +
                "A timer starts...");
        texts.add("Luckily he still has his earpiece, so you are able to communicate with him from the CIA Headquarters.\n\n" +
                "Try to help your fellow CIA agent escape!");

        // Change the current activity.
        ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
    }

    /**
     * Ends the intro and forwards to the waitingactivity.
     */
    private void endIntro() {
        timerHandler.removeCallbacks(timerRunnable);

        Intent intent = new Intent(this, WaitingActivity.class);
        startActivity(intent);
    }

    /**
     * Skips the intro by ending it.
     *
     * @param view the current view.
     */
    public void skipButton(View view) {
        endIntro();
    }

    // Handles actions that should happen when timer has certain values.
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (pointer >= texts.size()) {
                endIntro();
                return;
            }

            intoText.setText(texts.get(pointer));
            pointer++;
            timerHandler.postDelayed(timerRunnable, 7000);
        }
    };

}
