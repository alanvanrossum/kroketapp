package com.context.kroket.escapeapp.mainscreens;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * MainActivityTest is used to test the MainActivity class. Created by Harvey
 * van Veltom on 19/05/2016.
 */
public class MainActivityTest {

  @Rule
  public IntentsTestRule<MainActivity> myActivityRule 
      = new IntentsTestRule<MainActivity>(MainActivity.class);

  /**
   * Tests whether the connection message changes when no name is entered.
   */
  @Test
  public void clickWithoutNameTest() {
    // Click the connect button.
    onView(ViewMatchers.withId(R.id.connectButton)).perform(click());

    // Check if the connection message has been updated.
    onView(withId(R.id.connectionMessage)).check(matches(withText("Enter your name first!")));
  }

}
