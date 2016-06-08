package com.context.kroket.escapeapp.minigames;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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


@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * A_CodeCrackerPictureviewTest is used to test the A_Code_Cracker_Pictureview
 * class.
 * Created by Harvey van Veltom on 19/05/2016.
 */
public class A_CodeCrackerPictureviewTest {

  @Rule
  public IntentsTestRule<MainActivity> myActivityRule 
    = new IntentsTestRule<MainActivity>(MainActivity.class);

  /**
   * Sets up for the tests.
   */
  @Before
  public void setUp() {
    // Switch from MainActivity to A_Code_Cracker_PictureView.
    MainActivity.TestActivity = MainActivity.ActivitySwitch.apic;
    onView(ViewMatchers.withId(R.id.connectButton)).perform(click());
  }

  /**
   * Method checks the functionality of A_Code_Cracker_Pictureview code button.
   */
  @Test
  public void clickCodeButton() {
    // Click the code button.
    onView(withId(R.id.codeButton)).perform(click());

    // assert that we are now in the A_CodeCrackerCodeview activity.
    onView(withId(R.id.verifyButton)).check(matches(isDisplayed()));
  }

}