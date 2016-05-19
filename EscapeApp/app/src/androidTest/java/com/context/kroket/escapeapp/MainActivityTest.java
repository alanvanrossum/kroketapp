package com.context.kroket.escapeapp;

/**
 * Created by Harvey van Veltom on 18/05/2016.
 */
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
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
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * Tests whether the connection message changes when no name is entered.
     */
    @Test
    public void clickWithoutNameTest() {
        //click the connect button.
        onView(withId(R.id.connectButton)).perform(click());

        //check if the connection message has been updated.
        onView(withId(R.id.connectionMessage)).check(matches(withText("Enter your name first!")));
    }


    /**
     * test used to see what happens if you enter a name and connect. code is commented out because
     * the android app gets timed out. Issue lies probably with the use of intending.
     */
    @Test
    public void clickWithNameTest() {

        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(hasComponent(hasShortClassName(".ConnectionService"))).respondWith(result);

        onView(withId(R.id.player_name)).perform(typeText("MobileTest"), closeSoftKeyboard());
        onView(withId(R.id.connectButton)).perform(click());

        onView(withId(R.id.connectionMessage)).check(matches(withText("connected")));

    }

    /**
     * method to see if the start button works correctly.
     */
    @Test
    public void clickStartButtonTest() {
        //enable the start button
        MainActivity.switchStart(true);
        onView(withId(R.id.connectButton)).perform(click());

        //click the start button
        onView(withId(R.id.startButton)).perform(click());

        //check if we started a waiting activity.
        intended(hasComponent(hasShortClassName(".WaitingActivity")));
        MainActivity.switchStart(false);
    }

}
