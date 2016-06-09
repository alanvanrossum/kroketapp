package com.context.kroket.escapeapp.mainscreens;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Harvey van Veltom on 09/06/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class GameWonTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule
            = new IntentsTestRule<MainActivity>(MainActivity.class);

    //Test if the game won xml is displayed correctly
    @Test
    public void testGameWonText() {
        MainActivity.TestActivity = MainActivity.ActivitySwitch.gamewon;
        MainActivity.TestActivity.switchToActivity();

        onView(withId(R.id.textView)).check(matches(withText("GAME WON")));
    }
}
