package com.context.kroket.escapeapp.mainscreens;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Test class that tests the introActivity class.
 */
public class IntroActivityTest {


    /**
     * ruleset used by espresso.
     */
    @Rule
    public IntentsTestRule<MainActivity> myActivityRule
            = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * tests that the first text is displayed correctly when
     * starting the introActivity.
     */
    @Test
    public void testDisplayText() {
        MainActivity.TestActivity = MainActivity.ActivitySwitch.intro;
        MainActivity.TestActivity.switchToActivity();

        onView(withId(R.id.introText)).check(matches(withText("You are a CIA agent.\n\n" +
                "Together with your colleagues you are investigating the disappearance of a fellow CIA agent.")));
    }

    /**
     * tests that the skip button skips the activity correctly.
     */
    @Test
    public void testSkipButton() {
        MainActivity.TestActivity = MainActivity.ActivitySwitch.intro;
        MainActivity.TestActivity.switchToActivity();

        onView(withId(R.id.SkipButton)).perform(click());
        onView(withId(R.id.waiting)).check(matches(isDisplayed()));
    }
}
