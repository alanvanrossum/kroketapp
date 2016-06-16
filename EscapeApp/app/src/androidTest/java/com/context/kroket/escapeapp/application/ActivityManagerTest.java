package com.context.kroket.escapeapp.application;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.context.kroket.escapeapp.mainscreens.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Test class that tests the ActivityManager class.
 */
public class ActivityManagerTest {


    /**
     * ruleset used by espresso.
     */
    @Rule
    public IntentsTestRule<MainActivity> myActivityRule
            = new IntentsTestRule<MainActivity>(MainActivity.class);


    /**
     * tests the ActivityManager getter. creates a new ActivyManager.
     * and tests if the getter is equal to null.
     */
    @Test
    public void testActivityManagerGetter() {
        ActivityManager activityManager = new ActivityManager();
        assertEquals(activityManager.getCurrentActivity(),null);
    }

    /**
     * tests the ActivityManager setter. creates a new ActivityManager.
     * sets the ActivityManager to myActivityRule.getActivity and asserts if
     * the setting was successful.
     */
    @Test
    public void testActivityManagerSetter() {
        ActivityManager activityManager = new ActivityManager();


        activityManager.setCurrentActivity(myActivityRule.getActivity());
        assertEquals(activityManager.getCurrentActivity(),myActivityRule.getActivity());
    }
}
