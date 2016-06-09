package com.context.kroket.escapeapp.mainscreens;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.context.kroket.escapeapp.R;
import com.robotium.solo.Solo;

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
public class WaitingActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {


    /**
     * Solo is used by robotium to interact with the game's ui elements.
     */
    private Solo solo;

    /**
     * Constructor for the test class. Requirement of robotium.
     */
    public WaitingActivityTest() {
        super(MainActivity.class);
    }

    /**
     * Set's up solo object that is used by robotium to interact with ui elements.
     *
     * @throws Exception Exception
     */
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Check if we can activate the coin game.
     */
    @Test
    public void testclickCoinGame() {

        MainActivity.TestActivity = MainActivity.ActivitySwitch.waiting;
        MainActivity.TestActivity.switchToActivity();
        solo.hideSoftKeyboard();
        // Click the coin button.
        onView(ViewMatchers.withId(R.id.buttonCoin)).perform(click());

        // Check if the connection message has been updated.
        onView(withId(R.id.coinAmount)).check(matches(withHint("Score: 0")));
    }

    /**
     * Check if we can activate the squash game.
     */
    @Test
    public void testclickSquashGame() {

        MainActivity.TestActivity = MainActivity.ActivitySwitch.waiting;
        MainActivity.TestActivity.switchToActivity();
        solo.hideSoftKeyboard();
        // Click the coin button.
        onView(ViewMatchers.withId(R.id.buttonSquash)).perform(click());

        // Check if the connection message has been updated.
        onView(withId(R.id.squashtext)).check(matches(isDisplayed()));

    }

    /**
     * Close all activities of solo at the end of a test.
     *
     * @throws Exception Exception
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
