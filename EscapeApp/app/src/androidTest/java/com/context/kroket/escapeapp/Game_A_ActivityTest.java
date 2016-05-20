package com.context.kroket.escapeapp;

/**
 * Created by Harvey van Veltom on 19/05/2016.
 */

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Game_A_ActivityTest is used to test the Game_A_Activity class.
 */
public class Game_A_ActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * Method checks the functionality of Game_A_Activity code button.
     */
    @Test
    public void clickCodeButton() {
        //Switch from MainActivity to Game_A_Activity.
        MainActivity.switchGameA(true);
        onView(withId(R.id.connectButton)).perform(click());

        //Click the code button.
        onView(withId(R.id.codeButton)).perform(click());

        //Check that we switched to Game_AA_Activity by checking the code view.
        //If the switch didn't succeed we wouldn't be able to access the code view.
        onView(withId(R.id.code)).check(matches(withHint("M I H A")));

        //intended(hasComponent(hasShortClassName(".Game_AA_Activity")));

        MainActivity.switchGameA(false);
    }
}