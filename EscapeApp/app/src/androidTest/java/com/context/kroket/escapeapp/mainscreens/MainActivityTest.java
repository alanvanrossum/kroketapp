package com.context.kroket.escapeapp.mainscreens;

/**
 * Created by Harvey van Veltom on 19/05/2016.
 */
import android.support.test.espresso.intent.rule.IntentsTestRule;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;


@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * MainActivityTest is used to test the MainActivity class.
 */
public class MainActivityTest{



    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * Tests whether the connection message changes when no name is entered.
     */
    @Test
    public void clickWithoutNameTest() {
        //Click the connect button.
        onView(ViewMatchers.withId(R.id.connectButton)).perform(click());

        //Check if the connection message has been updated.
        onView(withId(R.id.connectionMessage)).check(matches(withText("Enter your name first!")));
    }

    /**
     * Method to see if the start button works correctly.
     */
    @Test
    public void clickStartButtonTest() {
        //Enable the start button.
        MainActivity.TestActivity = MainActivity.ActivitySwitch.startEn;

        onView(withId(R.id.connectButton)).perform(click());
        onView(withId(R.id.startButton)).perform(click());

        //Check if we started a waiting activity.
        onView(withId(R.id.waiting)).check(matches(isDisplayed()));


        //MainActivity.TestActivity = MainActivity.ActivitySwitch.notest;
    }

}
