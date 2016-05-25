package com.context.kroket.escapeapp.minigames;

import android.graphics.Color;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.test.MoreAsserts.assertEquals;
import static junit.framework.Assert.assertTrue;


/**
 * Created by Harvey van Veltom on 25/05/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class C_ColorSequenceTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testParseColors() {
        C_ColorSequence.parseColors("RED,BLUE,YELLOW,GREEN");
        ArrayList<Integer> al = C_ColorSequence.getSequence();

        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(Color.RED);
        test.add(Color.BLUE);
        test.add(Color.YELLOW);
        test.add(Color.GREEN);
        test.add(Color.WHITE);

        Assert.assertEquals(al,test);
    }

    @Test
    public void testStart() {
        C_ColorSequence.parseColors("RED,BLUE,YELLOW,GREEN");
        MainActivity.switchGameC(true);
        onView(withId(R.id.connectButton)).perform(click());

        onView(withId(R.id.gameC)).check(matches(isDisplayed()));

        onView(withId(R.id.startC)).perform(click());

        assertTrue(C_ColorSequence.getRunthroughs() == 1);
    }
}
