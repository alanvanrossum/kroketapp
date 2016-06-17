package com.context.kroket.escapeapp.minigames;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Test the A_CodeCrackerCodeview minigame with Espresso tests.
 */
public class A_CodCrackerCodeviewEspressoTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule
            = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * Method checks the functionality of A_Code_Cracker_Pictureview code button.
     */
    @Test
    public void clickPicButton() {
        MainActivity.TestActivity = MainActivity.ActivitySwitch.acode;
        MainActivity.TestActivity.switchToActivity();

        // Click the code button.
        onView(withId(R.id.picButton)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // assert that we are now in the A_CodeCrackerCodeview activity.
        onView(withId(R.id.codeButton)).check(matches(isDisplayed()));
    }
}
