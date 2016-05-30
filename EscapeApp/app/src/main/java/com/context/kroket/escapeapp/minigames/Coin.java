package com.context.kroket.escapeapp.minigames;

import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Swift on 30-5-2016.
 */
public class Coin {
    static int count;
    static ImageView iv;

    public Coin(ImageView newiv){
        iv=newiv;
        count=0;
    }
    public float getX(){
        return iv.getX();
    }

    public static int getCount() {
        return count;
    }
    public float getY(){
        return iv.getY();
    }
    public static void setX(float newX){
        iv.setX(newX);
    }
    public static void setY(float newY){
        iv.setY(newY);
    }
    public boolean collideWithGyro(float gyroX,float gyroY){
        if(Math.abs(iv.getX()-gyroX)<50 &&Math.abs(iv.getY()-gyroY)<50){
            count++;
            return true;
        }
        return false;
    }

    public void placeRandomly(Random rand, int xRange, int yRange, float gyroX, float gyroY) {
        setX(clamp(rand.nextInt(xRange),gyroX,50));
        setY(clamp(rand.nextInt(yRange),gyroY,50));
    }

    public static float clamp(int value, float gyroLoc, int size) {
        if(value>gyroLoc-size)
            return value+3*size;
        return value;
    }

    public static void placeCoin(int xVal, int yVal, float gyroX, float gyroY) {
        setX(clamp(xVal,gyroX,50));
        setY(clamp(yVal,gyroY,50));
    }
}
