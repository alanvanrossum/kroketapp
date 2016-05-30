package com.context.kroket.escapeapp.minigames;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;

import java.util.Random;

public class D_Gyroscope extends AppCompatActivity implements SensorEventListener{
    private SensorManager motionSensorManager;
    private Sensor motionSensor;

    private ImageView gyro;
    float screenWidth,screenHeight,gyroWidth,gyroHeight,minX,maxX,minY,maxY;
    private Coin gold, silver, bronze;


//travis
    @Override
    public void onSensorChanged(SensorEvent event){
//        event.values[0]: x*sin(θ/2)
//        event.values[1]: y*sin(θ/2)
//        event.values[2]: z*sin(θ/2)
//        event.values[3]: cos(θ/2)

        float oldX = gyro.getX();
        float oldY = gyro.getY();
        gyro.setX(clamp((oldX+event.values[0]*-35),minX,maxX));
        gyro.setY(clamp((oldY+event.values[1]*35),minY,maxY));
        collide();
    }
    private float clamp(float value, float min,float max){
        if(value<min)
            return min;
        if(value>max)
            return max;
        return value;
    }

    private void collide() {
        if(gold.collideWithGyro(gyro.getX(),gyro.getY())||silver.collideWithGyro(gyro.getX(),gyro.getY())||bronze.collideWithGyro(gyro.getX(),gyro.getY())){
            placeCoinsRandomly();
        }
    }


    private void placeCoinsRandomly(){
        placeCoinsRandomly(0,0);
    }
    private void placeCoinsRandomly(float offsetX,float offsetY) {
        //unavailable range: gyroX-gyroWidth, gyroX+2*gyroWidth, gyroY-gyroWidth, gyroY+2*gyroHeight
        float gyroX=gyro.getX()+offsetX;
        float gyroY=gyro.getY()+offsetY;
        Random rand = new Random();
        int xRange = Math.round(screenWidth-3*gyroWidth);
        int yRange = Math.round(screenHeight-3*gyroHeight);


        gold.placeRandomly(rand,xRange,yRange,gyroX,gyroY);
        silver.placeRandomly(rand,xRange,yRange,gyroX,gyroY);
        bronze.placeRandomly(rand,xRange,yRange,gyroX,gyroY);
    }
//    public static void setCollision(float x, float y) {
//        gold.setX(gyro.getX());
//        gold.setY(gyro.getY());
//        //silver.setX(x);
//        //silver.setY(y);
//        //bronze.setX(x);
//        //bronze.setY(y);
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        collide();
//    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d__gyroscope);
        motionSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        motionSensor = motionSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        motionSensorManager.registerListener(this,motionSensor,SensorManager.SENSOR_DELAY_FASTEST);
  }


//    public static int getCount() {
//        return count;
//    }

    @Override
    protected void onStart() {
        super.onStart();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight= metrics.heightPixels-150;
        screenWidth = metrics.widthPixels-30;

        //set the coins
        gold = new Coin((ImageView) findViewById(R.id.goldcoin));
        silver = new Coin((ImageView) findViewById(R.id.silvercoin));
        bronze = new Coin((ImageView) findViewById(R.id.bronzecoin));
        gyro = (ImageView) findViewById(R.id.gyroimage);
        gyroWidth=50;
        gyroHeight=50;

        minX = 0;
        maxX = screenWidth-gyroWidth;
        minY = 0;
        maxY = screenHeight-gyroHeight;

        placeCoinsRandomly(screenWidth/2-gyroWidth/2,screenHeight/2-gyroHeight/2);
        //Change the current activity.
        ((ActivityManager)this.getApplicationContext()).setCurrentActivity(this);
    }

    public static void setTestValues(int gx, int gy, int sx, int sy, int bx, int by) {
        goldXTest = gx;
        goldYTest = gy;
        silverXTest = sx;
        silverYTest = sy;
        bronzeXTest = bx;
        bronzeYTest = by;
    }

    static int goldXTest = 0;
    static int goldYTest = 0;
    static int silverXTest = 0;
    static int silverYTest = 0;
    static int bronzeXTest = 0;
    static int bronzeYTest = 0;

//    public static void setRanges() {
//        goldX = goldXTest;
//        goldY = goldYTest;
//        silverX = silverXTest;
//        silverY = silverYTest;
//        bronzeX = bronzeXTest;
//        bronzeY = bronzeYTest;
//    }
//
//    public static void setTrue() {
//        testing = true;
//    }
//    public static int getGoldX() {
//        return goldX;
//    }
//    public static int getGoldY() {
//        return goldY;
//    }
//    public static int getSilverX() {
//        return silverX;
//    }
//    public static int getSilverY() {
//        return silverY;
//    }
//    public static int getBronzeX() {
//        return bronzeX;
//    }
//    public static int getBronzeY() {
//        return bronzeY;
//    }
//    public static ImageView getSilver() {
//        return silver;
//    }
//
//    public static float getGyroX() {
//        return gyro.getX();
//    }
//
//    public static float getGyroY() {
//        return gyro.getY();
//    }
//
//    public static void setGold(float xcoord, float ycoord) {
//        gold.setX(xcoord);
//        gold.setY(ycoord);
//    }
//    public static void setSilver(float xcoord, float ycoord) {
//        silver.setX(xcoord);
//        silver.setY(ycoord);
//    }
//    public static void setBronze(float xcoord, float ycoord) {
//        bronze.setX(xcoord);
//        bronze.setY(ycoord);
//    }
//
//    public static int getGoldCount() {
//        return goldCount;
//    }
//
//    public static int getSilverCount() {
//        return silverCount;
//    }
//
//    public static int getBronzeCount() {
//        return bronzeCount;
//    }
//
//    public static void setUpCollisionGold() {
//        gold.setX(gyro.getX());
//        gold.setY(gyro.getY());
//    }
//
//    public static void setCollisionGold(boolean b) {
//        collisionGold = b;
//    }
//    public static void setCollisionSilver(boolean b) {
//        collisionSilver = b;
//    }
//    public static void setCollisionBronze(boolean b) {
//        collisionBronze = b;
//    }
//    public static boolean collisionGold = false;
//    public static boolean collisionSilver = false;
//    public static boolean collisionBronze = false;
//    public static void resetCount() {
//        count = 0;
//    }
}
