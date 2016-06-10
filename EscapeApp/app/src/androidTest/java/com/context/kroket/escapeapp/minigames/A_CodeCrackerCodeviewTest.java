package com.context.kroket.escapeapp.minigames;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;
import com.context.kroket.escapeapp.mainscreens.WaitingActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class tests the A_CodeCrackerCodeView class. mainly the verify button and
 * message. test class uses robotium instead of espresso because we need to
 * close android's keyboard.
 */
public class A_CodeCrackerCodeviewTest extends ActivityInstrumentationTestCase2<MainActivity> {

  /**
   * Solo is used by robotium to interact with the game's ui elements.
   */
  private Solo solo;

  /**
   * Constructor for the test class. Requirement of robotium.
   */
  public A_CodeCrackerCodeviewTest() {
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
   * Method checks the functionality of A_CodeCrackerCodeview pic button.
   */
  @Test
  public void testclickPicButton() {
    // enable and switch to the A_CodeCrackerCodeview class.
    MainActivity.TestActivity = MainActivity.ActivitySwitch.acode;
    MainActivity.TestActivity.switchToActivity();

    // click the pic button
    Button picButton = (Button) solo.getView(R.id.picButton);
    solo.clickOnView(picButton);

    // assert that we are now in the A_Code_Cracker_Pictureview activity.
    solo.assertCurrentActivity("should be pictureView", A_Code_Cracker_Pictureview.class);

  }

  /**
   * The method checks to see what happens when we enter the wrong answer in
   * A_CodeCrackerCodeView.
   */
  @Test
  public void testWrongAnswer() {

    // enable and switch to the A_CodeCrackerCodeview class.
    MainActivity.TestActivity = MainActivity.ActivitySwitch.acode;
    MainActivity.TestActivity.switchToActivity();

    // Hide the android keyboard.
    solo.hideSoftKeyboard();

    // Click the verify button of A_CodeCrackerCodeView when
    // we don't have an answer filled in.
    Button buttonVerify = (Button) solo.getView(R.id.verifyButton);
    solo.clickOnView(buttonVerify);

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // assert that the number of attempts is now 1.
    assertEquals(A_CodeCrackerCodeview.getNumberOfAttempts(), 1);
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
