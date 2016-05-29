package com.context.kroket.escapeapp.minigames;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
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
        D_Gyroscope.setTestValues(3,3,250,250,450,450);
        D_Gyroscope.setTrue();
        //Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        onView(withId(R.id.connectButton)).perform(click());
    }

    @After
    public void tearDown() {
        MainActivity.switchGameD(false);
    }

    @Test
    public void testPlaceCoinsRandomlyGoldX() {

        assertEquals(153,D_Gyroscope.getGoldX());

    }

    @Test
    public void testPlaceCoinsRandomlyGoldY() {
        assertEquals(153, D_Gyroscope.getGoldY());
    }

    @Test
    public void testPlaceCoinsRandomlySilverX() {
        assertEquals(400, D_Gyroscope.getSilverX());
    }

    @Test
    public void testPlaceCoinsRandomlySilverY() {
        assertEquals(400, D_Gyroscope.getSilverY());
    }

    @Test
    public void testPlaceCoinsRandomlyBronzeX() {
        assertEquals(600, D_Gyroscope.getBronzeX());
    }

    @Test
    public void testPlaceCoinsRandomlyBronzeY() {
        assertEquals(600, D_Gyroscope.getBronzeY());
    }

    @Test
    public void testCollideWithFalse() {
        ImageView silverCoin = D_Gyroscope.getSilver();
        assertEquals(D_Gyroscope.collideWith(silverCoin), false);
    }

    @Test
    public void testCollideWithGold() {
        float gyroX = D_Gyroscope.getGyroX();
        float gyroY = D_Gyroscope.getGyroY();
        D_Gyroscope.setGold(gyroX,gyroY);
        D_Gyroscope.collide();
        assertEquals(D_Gyroscope.getGoldCount(),2);
    }

    @Test
    public void testCollideWithSilver() {
        float gyroX = D_Gyroscope.getGyroX();
        float gyroY = D_Gyroscope.getGyroY();
        D_Gyroscope.setSilver(gyroX,gyroY);
        D_Gyroscope.collide();
        assertEquals(D_Gyroscope.getSilverCount(),1);
    }

    @Test
    public void testCollideWithBronze() {
        float gyroX = D_Gyroscope.getGyroX();
        float gyroY = D_Gyroscope.getGyroY();
        D_Gyroscope.setBronze(gyroX,gyroY);
        D_Gyroscope.collide();
        assertEquals(D_Gyroscope.getBronzeCount(),1);

    }
}
