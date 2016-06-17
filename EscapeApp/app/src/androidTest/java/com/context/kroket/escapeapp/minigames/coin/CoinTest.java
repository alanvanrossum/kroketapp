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
     * Ruleset used by espresso.
     */
    @Rule
    public IntentsTestRule<MainActivity> myActivityRule
            = new IntentsTestRule<MainActivity>(MainActivity.class);

    /**
     * The setUp method is called before every test and instantiates
     * the fields.
     */
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

    /**
     * Assert that the coin constructor works correctly.
     */
    @Test
    public void testConstructor() {
        assertNotEquals(goldCoin,null);
    }

    /**
     * Test the setter of the X coordinate.
     */
    @Test
    public void testSetX() {
        goldCoin.setX(5.0f);
        assertEquals(goldCoin.getX(),5.0f);
    }

    /**
     * Test the getter of the X coordinate.
     */
    @Test
    public void testGetX() {
        assertEquals(goldCoin.getX(),0.0f);
    }

    /**
     * Test the setter of the Y coordinate.
     */
    @Test
    public void testSetY() {
        goldCoin.setY(5.0f);
        assertEquals(goldCoin.getY(),5.0f);
    }

    /**
     * Test the getter of the Y coordinate.
     */
    @Test
    public void testGetY() {
        assertEquals(goldCoin.getY(),0.0f);
    }

    /**
     * Test the getter of the count integer.
     */
    @Test
    public void testGetCount() {
        assertEquals(goldCoin.getCount(),0);
    }

    /**
     * Test the setter of the count integer.
     */
    @Test
    public void testSetCount() {
        goldCoin.setCount(5);
        assertEquals(goldCoin.getCount(),5);
    }

    /**
     * Test the collideWith method if there is no intersection.
     */
    @Test
    public void testCollideWithFalse() {
        goldCoin.setX(80.0f);
        goldCoin.setY(80.0f);
        assertEquals(goldCoin.collideWithGyro(0f,0f),false);
    }

    /**
     * Test the collideWith method if there is an intersection.
     */
    @Test
    public void testCollideWithTrue() {
        assertEquals(goldCoin.collideWithGyro(0f,0f),true);
    }

    /**
     * Test the collideWith method of a DeadCoin if there is no intersection.
     */
    @Test
    public void testCollideWithDeadFalse() {
        deadCoin.setX(120.0f);
        deadCoin.setY(120.0f);
        assertEquals(deadCoin.collideWithGyro(0f,0f),false);
    }

    /**
     * Test the collideWith method if a DeadCoin if there is an intersection.
     */
    @Test
    public void testCollideWithDeadTrue() {
        assertEquals(deadCoin.collideWithGyro(0f,0f),true);
    }

    /**
     * Test the clamp method if the value is smaller than the gyroLoc
     * minus the size.
     */
    @Test
    public void testClampValueSmallerThanGyroLocMinusSize() {
        assertEquals(deadCoin.clamp(5,80f,0),5f);
    }

    /**
     * Test the clamp method if the value is bigger than the gyroLoc
     * minus the size.
     */
    @Test
    public void testClampValueBiggerThanGyroLocMinusSize() {
        assertEquals(deadCoin.clamp(50,80f,50),200f);
    }

    /**
     * Test the placeRandomly method for the X coordinate.
     */
    @Test
    public void testPlaceRandomlyX() {
        deadCoin.placeRandomly(rand,60,60,60,60);
        assertNotEquals(deadCoin.getX(),0f);
    }

    /**
     * Test the placeRandomly method for the Y coordinate.
     */
    @Test
    public void testPlaceRandomlyY() {
        deadCoin.placeRandomly(rand,60,60,60,60);
        assertNotEquals(deadCoin.getY(),0f);
    }

    /**
     * Test the score method for the goldCoin.
     */
    @Test
    public void scoreGold() {
        goldCoin.score();
        assertEquals(goldCoin.getCount(),3);
    }

    /**
     * Test the score method for the silverCoin.
     */
    @Test
    public void scoreSilver() {
        silverCoin.score();
        assertEquals(silverCoin.getCount(),2);
    }

    /**
     * Test the score method for the bronzeCoin.
     */
    @Test
    public void scoreBronze() {
        bronzeCoin.score();
        assertEquals(bronzeCoin.getCount(),1);
    }

    /**
     * Test the score method for the deadCoin.
     */
    @Test
    public void scoreDead() {
        deadCoin.score();
        assertEquals(deadCoin.getCount(),0);
    }


}
