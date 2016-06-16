package com.context.kroket.escapeapp.mainscreens;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.context.kroket.escapeapp.R;
import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * MainActivityTest is used to test the MainActivity class. Created by Harvey
 * van Veltom on 19/05/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

  /**
   * Solo is used by robotium to interact with the game's ui elements.
   */
  private Solo solo;

  /**
   * Constructor for the test class. Requirement of robotium.
   */
  public MainActivityTest() {
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
   * Tests whether the connection message changes when no name is entered.
   */
  @Test
  public void testclickWithoutName() {
    // Click the connect button.
    Button connectButton = (Button) solo.getView(R.id.connectButton);
    solo.clickOnView(connectButton);

    // Check if the connection message has been updated.
    onView(withId(R.id.connectionMessage)).check(matches(withText("Enter your name first!")));
  }

  /**
   * Tests if the connection fails if we aren't able to connect.
   */
  @Test
  public void testclickWithoutConnection() {
    EditText playerName = (EditText) solo.getView(R.id.player_name);
    solo.clearEditText(playerName);
    solo.typeText(playerName, "Name");

    solo.hideSoftKeyboard();

    onView(ViewMatchers.withId(R.id.connectButton)).perform(click());

    onView(withId(R.id.connectionMessage)).check(matches(withText("Connection failed. :(")));
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
