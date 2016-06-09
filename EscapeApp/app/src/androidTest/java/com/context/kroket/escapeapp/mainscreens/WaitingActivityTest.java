package com.context.kroket.escapeapp.mainscreens;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Harvey van Veltom on 10/06/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class WaitingActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule
            = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {
        MainActivity.TestActivity = MainActivity.ActivitySwitch.waiting;
        MainActivity.TestActivity.switchToActivity();
    }

    /**
     * Check if we can activate the coin game.
     */
    @Test
    public void clickCoinGame() {
        // Click the coin button.
        onView(ViewMatchers.withId(R.id.buttonCoin)).perform(click());

        // Check if the connection message has been updated.
        onView(withId(R.id.coinAmount)).check(matches(withHint("Score: 0")));
    }

    /**
     * Check if we can activate the squash game.
     */
    @Test
    public void clickSquashGame() {
        // Click the coin button.
        onView(ViewMatchers.withId(R.id.buttonSquash)).perform(click());

        // Check if the connection message has been updated.
        onView(withId(R.id.squashtext)).check(matches(isDisplayed()));

    }
}
