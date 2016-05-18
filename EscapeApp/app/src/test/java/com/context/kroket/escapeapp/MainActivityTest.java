package com.context.kroket.escapeapp;

/**
 * Created by Harvey van Veltom on 18/05/2016.
 */
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import org.junit.Test;

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


//@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  // @Rule
//   public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void clickWithoutNameTest() {

        onView(withId(R.id.connectButton)).perform(click());

        onView(withId(R.id.connectionMessage)).check(matches(withText("Enter your name first!")));
    }

    @Test
    public void clickWithNameTest() {


        // an activity result that will be used during stubbing. if an intent of class Main activity is activated it will be stubbed with. result.
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(hasComponent(hasShortClassName(".MainActivity"))).respondWith(result);

        onView(withId(R.id.player_name)).perform(typeText("MobileTest"), closeSoftKeyboard());
        onView(withId(R.id.connectButton)).perform(click());

        onView(withId(R.id.connectionMessage)).check(matches(withText("connected")));

    }


    @Test
    public void clickStartButtonTest() {
        onView(withId(R.id.startButton)).perform(click());

        intended(hasComponent(hasShortClassName(".WaitingActivity")));

    }

}
