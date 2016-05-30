package com.context.kroket.escapeapp.minigames;

/**
 * Created by Harvey van Veltom on 19/05/2016.
 */
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;

import org.junit.After;
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
 * B_TapGameTest is used to test the B_TapGame class.
 */
public class B_TapGameTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setUp() {
        MainActivity.TestActivtiy = MainActivity.ActivitySwitch.btap;
        onView(ViewMatchers.withId(R.id.connectButton)).perform(click());
    }

    @After
    public void tearDown() {
        MainActivity.TestActivtiy = MainActivity.ActivitySwitch.notest;
    }

    /**
     * Method checks the functionality of clicking the B_TapGame start button once.
     */
    @Test
    public void clickStartButtonOnce() {


        //Click the start button.
        onView(withId(R.id.clickButton)).perform(click());

        //Check if the start button's text has been updated.
        onView(withId(R.id.clickButton)).check(matches(withText("CLICK ME!")));

    }

    /**
     * Method checks the functionality of clicking the B_TapGame start button twice.
     */
    @Test
    public void clickStartButtonTwice() {

        //Double click the start button.
        onView(withId(R.id.clickButton)).perform(doubleClick());

        //Check if the amount text has been updated to Times clicked: 1.
        //This only happens if you press the button twice.
        onView(withId(R.id.amount)).check(matches(withText("Times clicked: 1")));

    }
}
