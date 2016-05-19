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
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Game_B_ActivityTest is used to test the Game_B_Activity class.
 */
public class Game_B_ActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setup() {
        MainActivity.switchWakeUp();
    }

    /**
     * Method checks the functionality of clicking the Game_B_Activity start button once.
     */
    @Test
    public void clickStartButtonOnce() {
        //Switch from MainActivity to Game_B_Activity.
        MainActivity.switchGameB(true);
        onView(withId(R.id.connectButton)).perform(click());

        //Click the start button.
        onView(withId(R.id.clickButton)).perform(click());

        //Check if the start button's text has been updated.
        onView(withId(R.id.clickButton)).check(matches(withText("CLICK ME!")));
        MainActivity.switchGameB(false);
    }

    /**
     * Method checks the functionality of clicking the Game_B_Activity start button twice.
     */
    @Test
    public void clickStartButtonTwice() {
        //Switch from MainActivity to Game_B_Activity.
        MainActivity.switchGameB(true);
        onView(withId(R.id.connectButton)).perform(click());

        //Double click the start button.
        onView(withId(R.id.clickButton)).perform(doubleClick());

        //Check if the amount text has been updated to Times clicked: 1.
        //This only happens if you press the button twice.
        onView(withId(R.id.amount)).check(matches(withText("Times clicked: 1")));
        MainActivity.switchGameB(false);
    }
}
