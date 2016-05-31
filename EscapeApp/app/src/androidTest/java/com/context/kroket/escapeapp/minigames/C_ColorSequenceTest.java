package com.context.kroket.escapeapp.minigames;

import android.graphics.Color;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;
import com.context.kroket.escapeapp.network.CommandParser;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Class used to test C_ColorSequence class.
 */
public class C_ColorSequenceTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * Check if the parseColors method functions correctly.
     */
    @Test
    public void testParseColors() {
        //Parse a stream of colors.
        ArrayList<String> params = CommandParser.parseParams("INITM[startC][RED][BLUE][YELLOW][GREEN]");
        C_ColorSequence.parseColors(params);
        ArrayList<Integer> parsedArrayList = C_ColorSequence.getSequence();

        //Create an ArrayList testArrayList with the same
        //colors as parsedArrayList.
        ArrayList<Integer> testArrayList = new ArrayList<Integer>();
        testArrayList.add(Color.RED);
        testArrayList.add(Color.BLUE);
        testArrayList.add(Color.YELLOW);
        testArrayList.add(Color.GREEN);
        testArrayList.add(Color.WHITE);

        //Assert the two ArrayLists are equal.
        Assert.assertEquals(parsedArrayList,testArrayList);
    }

    /**
     * Check if we can press start correctly
     */
    @Test
    public void testStart() {
        //Parse in a stream of colors.
        ArrayList<String> params = CommandParser.parseParams("INITM[startC][RED][BLUE][YELLOW][GREEN]");
        C_ColorSequence.parseColors(params);

        //Switch to C_ColorSequence activity.
        MainActivity.switchGameC(true);
        onView(withId(R.id.connectButton)).perform(click());

        //Click the startC button.
        onView(withId(R.id.startC)).perform(click());

        //Assert we have made one runtrough meaning the color stream
        //has been shown once successfully.
        assertTrue(C_ColorSequence.getRunthroughs() == 1);
    }
}
