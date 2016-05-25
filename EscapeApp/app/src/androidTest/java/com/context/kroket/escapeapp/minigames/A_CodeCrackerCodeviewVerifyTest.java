package com.context.kroket.escapeapp.minigames;

import android.test.ActivityInstrumentationTestCase2;
import android.test.FlakyTest;
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
 * Created by Harvey van Veltom on 25/05/2016.
 */
public class A_CodeCrackerCodeviewVerifyTest  extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public A_CodeCrackerCodeviewVerifyTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testWrongAnswer() {
        MainActivity.switchGameAA(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        solo.hideSoftKeyboard();

        Button buttonVerify = (Button) solo.getView(R.id.verifyButton);
        solo.clickOnView(buttonVerify);

        TextView verify = (TextView) solo.getView(R.id.verifyMessage);
        assertEquals("view should be visible", verify.getVisibility(), View.VISIBLE);

        MainActivity.switchGameAA(false);
    }

    @Test
    public void testRightAnswer() {
        MainActivity.switchGameAA(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        EditText answer = (EditText) solo.getView(R.id.answerA);
        solo.clearEditText(answer);
        solo.typeText(answer, "1234");

        solo.hideSoftKeyboard();

        A_CodeCrackerCodeview.setServiceIsBound(false);

        Button buttonVerify = (Button) solo.getView(R.id.verifyButton);
        solo.clickOnView(buttonVerify);

        solo.assertCurrentActivity("should be waiting", WaitingActivity.class);
        //TextView verify = (TextView) solo.getView(R.id.verifyMessage);
        //assertEquals("view should be visible", verify.getVisibility(), View.VISIBLE);

        MainActivity.switchGameAA(false);
        A_CodeCrackerCodeview.setServiceIsBound(true);
    }


    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
