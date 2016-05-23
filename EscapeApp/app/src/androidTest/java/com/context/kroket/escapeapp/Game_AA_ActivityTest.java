package com.context.kroket.escapeapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.mainscreens.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Harvey van Veltom on 19/05/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Game_AA_ActivityTest is used to test the Game_AA_Activity class.
 */
public class Game_AA_ActivityTest {


    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);


    /**
     * Method checks the functionality of Game_AA_Activity pic button.
     */
    @Test
    public void clickPicButton() {
        //Switch from MainActivity to Game_AA_Activity.
        MainActivity.switchGameAA(true);
        onView(withId(R.id.connectButton)).perform(click());

        //Click the pic button.
        onView(withId(R.id.picButton)).perform(click());

        //Check if we instantiated a Game_A_Activity.
        intended(hasComponent(hasShortClassName(".Game_A_Activity")));
        MainActivity.switchGameAA(false);
    }
}