package com.context.kroket.escapeapp.minigames;

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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isLeftOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class D_GyroscopeTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testPlaceCoinsRandomlyGoldXandY() {

        D_Gyroscope.setTestValues(3,3,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());

        Assert.assertTrue(D_Gyroscope.getGoldX() == 3);
        Assert.assertTrue(D_Gyroscope.getGoldY() == 3);
        //onView(withId(R.id.goldcoin)).check(matches(isLeftOf(withId(R.id.silvercoin))));
        MainActivity.switchGameD(false);
    }
}
