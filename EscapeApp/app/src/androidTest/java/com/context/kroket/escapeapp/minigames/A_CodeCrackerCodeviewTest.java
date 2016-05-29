package com.context.kroket.escapeapp.minigames;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;
import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertTrue;

/**
 * Created by Harvey van Veltom on 19/05/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * A_CodeCrackerCodeviewTest is used to test the A_CodeCrackerCodeview class.
 */
public class A_CodeCrackerCodeviewTest extends
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



    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);


    /**
     * Method checks the functionality of A_CodeCrackerCodeview pic button.
     */
   // @Test
   // public void clickPicButton() {
//        //Switch from MainActivity to A_CodeCrackerCodeview.
//        MainActivity.switchGameAA(true);
//        onView(ViewMatchers.withId(R.id.connectButton)).perform(click());
//
//        //Click the pic button.
//        onView(withId(R.id.picButton)).perform(click());
//
//        //Check if we instantiated a A_Code_Cracker_Pictureview.
//        intended(hasComponent(hasShortClassName(".minigames.A_Code_Cracker_Pictureview")));
//        MainActivity.switchGameAA(false);
 //   }

    @Test
    public void testWrongAnswer() {
        MainActivity.switchGameAA(true);
        onView(ViewMatchers.withId(R.id.connectButton)).perform(click());

        //onView(withId(R.id.verifyMessage)).check(matches( isDisplayed()));

        solo.hideSoftKeyboard();

        onView(withId(R.id.verifyButton)).perform(click());

        onView(withId(R.id.verifyMessage)).check(matches( isDisplayed()));
        //assertTrue(A_CodeCrackerCodeview.getHit() == true);

        MainActivity.switchGameAA(false);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}