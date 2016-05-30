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
 * Class tests the A_CodeCrackerCodeView class. mainly the verify button and message.
 * test class uses robotium instead of espresso because we need to close android's
 * keyboard.
 */
public class A_CodeCrackerCodeviewTest  extends
        ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * Solo is used by robotium to interact with the game's ui elements.
     */
    private Solo solo;

    /**
     * Constructor for the test class.
     * Requirement of robotium.
     */
    public A_CodeCrackerCodeviewTest() {
        super(MainActivity.class);
    }

    /**
     * Set's up solo object that is used by robotium
     * to interact with ui elements.
     *
     * @throws Exception
     */
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }



    /**
     * Method checks the functionality of A_CodeCrackerCodeview pic button.
     */
    @Test
    public void testclickPicButton() {
        MainActivity.TestActivtiy = MainActivity.ActivitySwitch.acode;
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        Button picButton = (Button) solo.getView(R.id.picButton);
        solo.clickOnView(picButton);

        solo.assertCurrentActivity("should be pictureView", A_Code_Cracker_Pictureview.class);
        MainActivity.TestActivtiy = MainActivity.ActivitySwitch.notest;

    }

    /**
     * The method checks to see what happens when we enter the wrong
     * answer in A_CodeCrackerCodeView.
     */
    @Test
    public void testWrongAnswer() {
        MainActivity.TestActivtiy = MainActivity.ActivitySwitch.acode;
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        //Hide the android keyboard.
        solo.hideSoftKeyboard();

        //Click the verify button of A_CodeCrackerCodeView when
        //we don't have an answer filled in.
        Button buttonVerify = (Button) solo.getView(R.id.verifyButton);
        solo.clickOnView(buttonVerify);

        //Check if the verifyMessage view is visible(only becomes visible)
        //if we have filled in a wrong answer.
        //TextView verify = (TextView) solo.getView(R.id.verifyMessage);
        //assertEquals("view should be visible", verify.getVisibility(), View.VISIBLE);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(A_CodeCrackerCodeview.getNumberOfAttempts(), 1);
        MainActivity.TestActivtiy = MainActivity.ActivitySwitch.notest;

    }

    /**
     * The method checks to see what happens when we enter the right
     * answer in A_CodeCrackerCodeView.
     */
    @Test
    public void testRightAnswer() {
        //Switch from MainActivity to A_CodeCrackerCodeView.
        MainActivity.TestActivtiy = MainActivity.ActivitySwitch.acode;
        A_CodeCrackerCodeview.enableTesting(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        //Fill in the correct answer in the answerA view.
        EditText answer = (EditText) solo.getView(R.id.answerA);
        solo.clearEditText(answer);
        solo.typeText(answer, "2719173");

        //Hide the android keyboard.
        solo.hideSoftKeyboard();

        //Temporarily switch of serviceIsBound.
        //A_CodeCrackerCodeview.setServiceIsBound(false);

        //Click the verify button of A_CodeCrackerCodeView when
        //we have the right answer filled in.
        Button buttonVerify = (Button) solo.getView(R.id.verifyButton);
        solo.clickOnView(buttonVerify);

        //Assert we are now in the WaitingActivity class.
        solo.assertCurrentActivity("should be waiting", WaitingActivity.class);

        MainActivity.TestActivtiy = MainActivity.ActivitySwitch.notest;
        A_CodeCrackerCodeview.enableTesting(false);

        // A_CodeCrackerCodeview.setServiceIsBound(true);
    }


    /**
     * Close all activities of solo at the end of a test.
     *
     * @throws Exception
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
