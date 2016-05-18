package com.context.kroket.escapeapp;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Harvey van Veltom on 18/05/2016.
 */
public class Game_B_ActivityTest {

    @Test
    public void clickPicButton() {
        onView(withId(R.id.clickButton)).perform(click());

        onView(withId(R.id.amount)).check(matches(withText("Times clicked: 1")));

    }
}
