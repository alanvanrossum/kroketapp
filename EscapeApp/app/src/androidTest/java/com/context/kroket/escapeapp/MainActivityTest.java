package com.context.kroket.escapeapp;

/**
 * Created by Harvey van Veltom on 18/05/2016.
 */
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickWithoutNameTest() {

        onView(withId(R.id.connectButton)).perform(click());

        onView(withId(R.id.connectionMessage)).check(matches(withText("Enter your name first!")));
    }

//    @Test
//    public void clickWithNameTest() {
//
//
//
//        Intent resultData = new Intent();
//        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//        intending(hasComponent(hasShortClassName(".ConnectionService"))).respondWith(result);
//
//        onView(withId(R.id.player_name)).perform(typeText("MobileTest"), closeSoftKeyboard());
//        onView(withId(R.id.connectButton)).perform(click());
//
//        onView(withId(R.id.connectionMessage)).check(matches(withText("connected")));
//
//    }

//currently fails the assert however if startbutton is enabled it will succeed.
    @Test
    public void clickStartButtonTest() {
        MainActivity.swithOnStart();
        onView(withId(R.id.connectButton)).perform(click());

        onView(withId(R.id.startButton)).perform(click());

        intended(hasComponent(hasShortClassName(".WaitingActivity")));

    }

}
