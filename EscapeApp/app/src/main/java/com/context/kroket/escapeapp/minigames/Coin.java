package com.context.kroket.escapeapp.minigames;

import android.widget.ImageView;

import java.util.Random;

/**
 *
 */
public abstract class Coin {
    int count;
    ImageView iv;

    /**
     * This method created a new Coin Object, which contains a count and an imageView.
     * The ImageView corresponds to the viewID of the coin, and contains information such as location and rotation.
     * count contains the amount of coins of this type collected in total by the player.
     * @param newiv
     */
    public Coin(ImageView newiv){
        iv = newiv;
        count = 0;
    }

    /**
     * Adds the amount to the score belonging to the type of coin.
     */
    public abstract void score();

    /**
     * Set the count for the coin.
     *
     * @param count the number to be set.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Get the count for this coin.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * This method sets the location on the x-axis of the coin.
     * @param newX The new Xvalue of the view.
     */
    public void setX(float newX){
        iv.setX(newX);
    }

    /**
     * This method sets the location on the y-axis of the coin.
     * @param newY The new Yvalue of the view
     */
    public void setY(float newY){
        iv.setY(newY);
    }

    /**
     * This method is called when checking for collision.
     * @param gyroX The location of the skullball on the x-axis
     * @param gyroY The location of the skullball on the y-axis
     * @return  True if the skullball overlaps with the coin
     *          False if it doesn't.
     */
    public boolean collideWithGyro(float gyroX, float gyroY){
        if(Math.abs(iv.getX() - gyroX) < 50 && Math.abs(iv.getY() - gyroY) < 50){
            //count++;
            this.score();
            return true;
        }
        return false;
    }

    /**
     * This method is called for each coin when a coin is collected.
     * This method places the coin on a random location on the screen.
     * This method makes sure the coin does not initially overlap with the skullball
     * @param rand A Java Math.Random object
     * @param xRange The width of the screen minus three times the width of the skullball
     * @param yRange The height of the screen minus three times the height of the skullball
     * @param gyroX The location of the skullball on the x-axis
     * @param gyroY The location of the skullball on the y-axis
     */
    public void placeRandomly(Random rand, int xRange, int yRange, float gyroX, float gyroY) {
        setX(clamp(rand.nextInt(xRange),gyroX,50));
        setY(clamp(rand.nextInt(yRange),gyroY,50));
    }

    /**
     * This method is called to make sure the coins do not overlap with the skullball.
     * @param value The random x or y coordinate initally generated in placeRandomly()
     * @param gyroLoc The x respectively y coordinate containing the location of the skullball
     * @param size The width/height of the skullball
     * @return The new x or y location for the coin.
     */
    private float clamp(int value, float gyroLoc, int size) {
        if(value>gyroLoc-size)
            return value+3*size;
        return value;
    }
}
