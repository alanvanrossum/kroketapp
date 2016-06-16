package com.context.kroket.escapeapp.minigames.coin;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;

import com.context.kroket.escapeapp.mainscreens.MainActivity;
import com.context.kroket.escapeapp.minigames.coin.coins.BronzeCoin;
import com.context.kroket.escapeapp.minigames.coin.coins.DeadCoin;
import com.context.kroket.escapeapp.minigames.coin.coins.GoldCoin;
import com.context.kroket.escapeapp.minigames.coin.coins.SilverCoin;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
/**
 * Test class that tests the Coin class and subclasses.
 */
public class CoinTest {

    View view;
    ImageView image;
    Coin goldCoin;
    Coin bronzeCoin;
    Coin silverCoin;
    Coin deadCoin;
    Random rand;
    /**
     * ruleset used by espresso.
     */
    @Rule
    public IntentsTestRule<MainActivity> myActivityRule
            = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {
        view = new ImageView(MainActivity.getContext());
        image =  new ImageView(view.getContext());
        goldCoin = new GoldCoin(image);
        bronzeCoin = new BronzeCoin(image);
        silverCoin = new SilverCoin(image);
        deadCoin = new DeadCoin(image);
        rand = new Random();
    }

    @Test
    public void testConstructor() {
        assertNotEquals(goldCoin,null);
    }

    @Test
    public void testSetX() {

        goldCoin.setX(5.0f);
        assertEquals(goldCoin.getX(),5.0f);
    }

    @Test
    public void testGetX() {
        assertEquals(goldCoin.getX(),0.0f);
    }

    @Test
    public void testSetY() {
        goldCoin.setY(5.0f);
        assertEquals(goldCoin.getY(),5.0f);
    }

    @Test
    public void testGetY() {
        assertEquals(goldCoin.getY(),0.0f);
    }

    @Test
    public void testGetCount() {
        assertEquals(goldCoin.getCount(),0);
    }

    @Test
    public void testSetCount() {
        goldCoin.setCount(5);
        assertEquals(goldCoin.getCount(),5);
    }

    @Test
    public void testCollideWithFalse() {
        goldCoin.setX(80.0f);
        goldCoin.setY(80.0f);
        assertEquals(goldCoin.collideWithGyro(0f,0f),false);
    }

    @Test
    public void testCollideWithTrue() {
        assertEquals(goldCoin.collideWithGyro(0f,0f),true);
    }

    @Test
    public void testCollideWithDeadFalse() {
        deadCoin.setX(120.0f);
        deadCoin.setY(120.0f);
        assertEquals(deadCoin.collideWithGyro(0f,0f),false);
    }

    @Test
    public void testCollideWithDeadTrue() {
        assertEquals(deadCoin.collideWithGyro(0f,0f),true);
    }

    @Test
    public void testClampValueSmallerThanGyroLocMinusSize() {
        assertEquals(deadCoin.clamp(5,80f,0),5f);
    }

    @Test
    public void testClampValueBiggerThanGyroLocMinusSize() {
        assertEquals(deadCoin.clamp(50,80f,50),200f);
    }

    @Test
    public void testPlaceRandomlyX() {
        deadCoin.placeRandomly(rand,60,60,60,60);
        assertNotEquals(deadCoin.getX(),0f);
    }

    @Test
    public void testPlaceRandomlyY() {
        deadCoin.placeRandomly(rand,60,60,60,60);
        assertNotEquals(deadCoin.getY(),0f);
    }

    @Test
    public void scoreGold() {
        goldCoin.score();
        assertEquals(goldCoin.getCount(),3);
    }

    @Test
    public void scoreSilver() {
        silverCoin.score();
        assertEquals(silverCoin.getCount(),2);
    }

    @Test
    public void scoreBronze() {
        bronzeCoin.score();
        assertEquals(bronzeCoin.getCount(),1);
    }

    @Test
    public void scoreDead() {
        deadCoin.score();
        assertEquals(deadCoin.getCount(),0);
    }


}
