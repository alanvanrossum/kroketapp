package com.context.kroket.escapeapp;

/**
 * Created by Harvey van Veltom on 19/05/2016.
 */
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

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
public class Game_B_ActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickStartButtonOnce() {
        MainActivity.swithOnGameB();
        onView(withId(R.id.connectButton)).perform(click());

        onView(withId(R.id.clickButton)).perform(click());

        onView(withId(R.id.clickButton)).check(matches(withText("CLICK ME!")));

    }

    @Test
    public void clickStartButtonTwice() {
        MainActivity.swithOnGameB();
        onView(withId(R.id.connectButton)).perform(click());

        onView(withId(R.id.clickButton)).perform(doubleClick());
        //onView(withId(R.id.clickButton)).perform(click());

        onView(withId(R.id.amount)).check(matches(withText("Times clicked: 1")));

    }
}
