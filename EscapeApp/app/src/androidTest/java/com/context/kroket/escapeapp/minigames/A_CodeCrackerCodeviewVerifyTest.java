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

import org.junit.Test;

/**
 * Class tests the A_CodeCrackerCodeView class. mainly the verify button and message.
 * test class uses robotium instead of espresso because we need to close android's
 * keyboard.
 */
public class A_CodeCrackerCodeviewVerifyTest  extends
        ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * Solo is used by robotium to interact with the game's ui elements.
     */
    private Solo solo;

    /**
     * Constructor for the test class.
     * Requirement of robotium.
     */
    public A_CodeCrackerCodeviewVerifyTest() {
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
     * The method checks to see what happens when we enter the wrong
     * answer in A_CodeCrackerCodeView.
     */
    @Test
    public void testWrongAnswer() {
        //Switch from MainActivity to A_CodeCrackerCodeView.
        MainActivity.switchGameAA(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

      //  Fill in the correct answer in the answerA view.
        //EditText answer = (EditText) solo.getView(R.id.answerA);
       // solo.clearEditText(answer);
       // solo.typeText(answer, "1234");

        //Hide the android keyboard.
        solo.hideSoftKeyboard();

        //Click the verify button of A_CodeCrackerCodeView when
        //we don't have an answer filled in.
        Button buttonVerify = (Button) solo.getView(R.id.verifyButton);
        solo.clickOnView(buttonVerify);

        //Check if the verifyMessage view is visible(only becomes visible)
        //if we have filled in a wrong answer.
        //solo.hideSoftKeyboard();
        //TextView verify = (TextView) solo.getView(R.id.verifyMessage);
        //assertEquals("view should be visible", verify.getVisibility(), View.VISIBLE);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        assertTrue(A_CodeCrackerCodeview.getAttemptAmount() == 1);

        MainActivity.switchGameAA(false);
        //create counter for verifymessage and check that counter instead of visible view.
        //verify view doesn't work on travis.
    }

    /**
     * The method checks to see what happens when we enter the right
     * answer in A_CodeCrackerCodeView.
//     */
//    @Test
//    public void testRightAnswer() {
//        //Switch from MainActivity to A_CodeCrackerCodeView.
//        MainActivity.switchGameAA(true);
//        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
//        solo.clickOnView(buttonConnect);
//
//        //Fill in the correct answer in the answerA view.
//        EditText answer = (EditText) solo.getView(R.id.answerA);
//        solo.clearEditText(answer);
//        solo.typeText(answer, "2719173");
//
//        //Hide the android keyboard.
//        solo.hideSoftKeyboard();
//
//        //Temporarily switch of serviceIsBound.
//        A_CodeCrackerCodeview.setServiceIsBound(false);
//
//        //Click the verify button of A_CodeCrackerCodeView when
//        //we have the right answer filled in.
//        Button buttonVerify = (Button) solo.getView(R.id.verifyButton);
//        solo.clickOnView(buttonVerify);
//
//        //Assert we are now in the WaitingActivity class.
//        solo.assertCurrentActivity("should be waiting", WaitingActivity.class);
//
//        MainActivity.switchGameAA(false);
//        A_CodeCrackerCodeview.setServiceIsBound(true);
//    }


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
