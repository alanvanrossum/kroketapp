package com.context.kroket.escapeapp.minigames;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isLeftOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class D_GyroscopeTest {

    @Rule
    public IntentsTestRule<MainActivity> myActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        MainActivity.switchGameD(false);
    }

    @Test
    public void testPlaceCoinsRandomlyGoldX() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());

        assertEquals(100,D_Gyroscope.getGoldX());

    }

    @Test
    public void testPlaceCoinsRandomlyGoldY() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());
        assertEquals(100, D_Gyroscope.getGoldY());
    }

    @Test
    public void testPlaceCoinsRandomlySilverX() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());
        assertEquals(250, D_Gyroscope.getSilverX());
    }

    @Test
    public void testPlaceCoinsRandomlySilverY() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());
        assertEquals(250, D_Gyroscope.getSilverY());
    }

    @Test
    public void testPlaceCoinsRandomlyBronzeX() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());
        assertEquals(450, D_Gyroscope.getBronzeX());
    }

    @Test
    public void testPlaceCoinsRandomlyBronzeY() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());
        assertEquals(450, D_Gyroscope.getBronzeY());
    }

    @Test
    public void testCollideWithFalse() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());
        ImageView silverCoin = D_Gyroscope.getSilver();
        assertEquals(D_Gyroscope.collideWith(silverCoin), false);
        D_Gyroscope.resetCount();
    }

    @Test
    public void testCollideWithGold() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        D_Gyroscope.setCollisionGold(true);
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());
        //getActivity().runOnUiThread();
        //D_Gyroscope.setUpCollisionGold();
        //D_Gyroscope.collide();
        //assertEquals(1,D_Gyroscope.getCount());
        assertEquals(D_Gyroscope.getGoldCount(),1);
        D_Gyroscope.setCollisionGold(false);
        D_Gyroscope.resetCount();
    }

    @Test
    public void testCollideWithSilver() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        D_Gyroscope.setCollisionSilver(true);
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());

        assertEquals(D_Gyroscope.getSilverCount(),1);

        D_Gyroscope.setCollisionSilver(false);
        D_Gyroscope.resetCount();
    }

    @Test
    public void testCollideWithBronze() {
        D_Gyroscope.setTestValues(100,100,250,250,450,450);
        D_Gyroscope.setTrue();
        D_Gyroscope.setCollisionBronze(true);
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());

        assertEquals(D_Gyroscope.getBronzeCount(),1);

        D_Gyroscope.setCollisionSilver(false);
        D_Gyroscope.resetCount();
    }
}
