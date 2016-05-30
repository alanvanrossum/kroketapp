package com.context.kroket.escapeapp.minigames;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.mainscreens.MainActivity;
import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class CoinTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * Solo is used by robotium to interact with the game's ui elements.
     */
    private Solo solo;

    /**
     * Constructor for the test class.
     * Requirement of robotium.
     */
    public CoinTest() {
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

    @Test
    public void testConstructor() {
        MainActivity.switchGameD(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        ImageView goldCoinImage = (ImageView) solo.getView(R.id.goldcoin);
        Coin goldCoin = new Coin(goldCoinImage);

        assertEquals(goldCoin.getCount(),0);
    }
    @Test
    public void testSetX(){
       // Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        ImageView goldCoinImage = (ImageView) solo.getView(R.id.goldcoin);
        Coin goldCoin = new Coin(goldCoinImage);
        //float testfloat = 50.0f;
        goldCoin.setX(50.0f);

        assertEquals(goldCoin.getX(),50.0f, 0.01);
        //assertEquals(goldCoin.getX(),50.0);
    }

    @Test
    public void testSetY(){
        // Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        ImageView goldCoinImage = (ImageView) solo.getView(R.id.goldcoin);
        Coin goldCoin = new Coin(goldCoinImage);
        //goldCoin.placeCoin(50,50,500,500);

        goldCoin.setY(50.0f);
        assertEquals(goldCoin.getY(),50.0f, 0.01);
        //assertEquals(goldCoin.getX(),50.0);
    }

    @Test
    public void testCollideWithFalse() {
        // Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        ImageView goldCoinImage = (ImageView) solo.getView(R.id.goldcoin);
        Coin goldCoin = new Coin(goldCoinImage);

        goldCoin.setY(50.0f);
        goldCoin.setY(50.0f);

        assertEquals(goldCoin.collideWithGyro(100.0f,100.0f),false);
    }

    @Test
    public void testCollideWithTrue() {
        // Switch from MainActivity to D.
        MainActivity.switchGameD(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        ImageView goldCoinImage = (ImageView) solo.getView(R.id.goldcoin);
        Coin goldCoin = new Coin(goldCoinImage);

        goldCoin.setY(50.0f);
        goldCoin.setY(50.0f);

        assertEquals(goldCoin.collideWithGyro(50.0f,50.0f),false);
    }

    @Test
    public void testClampOriginal() {
        MainActivity.switchGameD(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        ImageView goldCoinImage = (ImageView) solo.getView(R.id.goldcoin);
        Coin goldCoin = new Coin(goldCoinImage);

        assertEquals(goldCoin.clamp(50, 100.0f, 10),50.0f, 0.1);
    }

    @Test
    public void testClampAdjusted() {
        MainActivity.switchGameD(true);
        Button buttonConnect = (Button) solo.getView(R.id.connectButton);
        solo.clickOnView(buttonConnect);

        ImageView goldCoinImage = (ImageView) solo.getView(R.id.goldcoin);
        Coin goldCoin = new Coin(goldCoinImage);

        assertEquals(goldCoin.clamp(50, 10.0f, 10),80.0f, 0.1);
    }
}
