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
    public static ImageView gyro,gold,silver,bronze;
    public static float screenWidth,screenHeight,gyroWidth,gyroHeight,coinWidth,coinHeight;
    public static int goldCount,silverCount,bronzeCount;
    public static int goldX, goldY, bronzeX, bronzeY, silverX, silverY;
    public static boolean testing = false;
    public static int count = 0;
//travis
    @Override
    public void onSensorChanged(SensorEvent event){
//        event.values[0]: x*sin(θ/2)
//        event.values[1]: y*sin(θ/2)
//        event.values[2]: z*sin(θ/2)
//        event.values[3]: cos(θ/2)

        float oldX = gyro.getX();
        float oldY = gyro.getY();

        gyro.setX(oldX+event.values[0]*-35);
        gyro.setY(oldY+event.values[1]*35);
        float newX = gyro.getX();
        float newY = gyro.getY();

        float minX = 0;
        float maxX = screenWidth-gyroWidth;
        float minY = 0;
        float maxY = screenHeight-gyroHeight;

        if(newX<minX){
           gyro.setX(minX);
        }
        if(newX>maxX){
            gyro.setX(maxX);
        }
        if(newY<minY){
            gyro.setY(minY);
        }
        if(newY>maxY){
            gyro.setY(maxY);
        }
        collide();
    }

    public static void collide() {
        if(collideWith(gold)){
            goldCount++;
            System.out.println("goldcoins are now: "+ goldCount);
            placeCoinsRandomly();
        } else if(collideWith(silver)){
            silverCount++;
            System.out.println("silver coins are now: "+ silverCount);

            placeCoinsRandomly();
        } else if(collideWith(bronze)){
            bronzeCount++;
            System.out.println("bronze coins are now: "+ bronzeCount);

            placeCoinsRandomly();
        }
    }

    public static boolean collideWith(ImageView coin) {
        if(Math.abs(coin.getX()-gyro.getX())<50 &&Math.abs(coin.getY()-gyro.getY())<50){
            return true;
        }
        return false;
    }

    public static void placeCoinsRandomly(){
        placeCoinsRandomly(0,0);
    }
    public static void placeCoinsRandomly(float offsetX,float offsetY) {
        //unavailable range: gyroX-gyroWidth, gyroX+2*gyroWidth, gyroY-gyroWidth, gyroY+2*gyroHeight
        float gyroX=gyro.getX()+offsetX;
        float gyroY=gyro.getY()+offsetY;
        Random rand = new Random();
        int xRange = Math.round(screenWidth-3*gyroWidth);
        int yRange = Math.round(screenHeight-3*gyroHeight);
        //test
        //
         goldX = rand.nextInt(xRange);
         goldY = rand.nextInt(yRange);

         silverX = rand.nextInt(xRange);
         silverY = rand.nextInt(yRange);


         bronzeX = rand.nextInt(xRange);
         bronzeY = rand.nextInt(yRange);


        if(goldX>gyroX-gyroWidth){
            goldX+=3*gyroWidth;
        }
        if(goldY>gyroY-gyroHeight){
            goldY+=3*gyroHeight;
        }

        if(silverX>gyroX-gyroWidth){
            silverX+=3*gyroWidth;
        }
        if(silverY>gyroY-gyroHeight){
            silverY+=3*gyroHeight;
        }
//        if(silverY>gyroY-gyroHeight){
//            silverY+=3*gyroHeight;
//        }
//        if(silverY>goldY){
//            silverY+=coinHeight;
//        }
        if(bronzeX>gyroX-gyroWidth){
            bronzeX+=3*gyroWidth;
        }
//        if(bronzeX>goldX){
//            bronzeX+=coinWidth;
//        }
//        if(bronzeX>silverX){
//            bronzeX+=coinWidth;
//        }
        if(bronzeY>gyroY-gyroHeight){
            bronzeY+=3*gyroHeight;
        }
//        if(bronzeY>goldY){
//            bronzeY+=coinHeight;
//        }
//        if(bronzeY>silverY){
//            bronzeY+=coinHeight;
//        }

        if(testing){
            setRanges();
        }


        gold.setX(goldX);
        gold.setY(goldY);
        silver.setX(silverX);
        silver.setY(silverY);
        bronze.setX(bronzeX);
        bronze.setY(bronzeY);

        if(collision && count == 0){
            count++;
            setCollision(gyroX,gyroY);
        }

    }
    public static void setCollision(float x, float y) {
        gold.setX(x);
        gold.setY(y);
        silver.setX(x);
        silver.setY(y);
        bronze.setX(x);
        bronze.setY(y);
        collide();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d__gyroscope);
        motionSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        motionSensor = motionSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        motionSensorManager.registerListener(this,motionSensor,SensorManager.SENSOR_DELAY_FASTEST);
        goldCount=0;
        silverCount=0;
        bronzeCount=0;

        //set screenwidth/height



    }



    @Override
    protected void onStart() {
        super.onStart();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight= metrics.heightPixels-150;
        screenWidth = metrics.widthPixels-30;

        //set the coins
        gold = (ImageView) findViewById(R.id.goldcoin);
        silver = (ImageView) findViewById(R.id.silvercoin);
        bronze = (ImageView) findViewById(R.id.bronzecoin);
        gyro = (ImageView) findViewById(R.id.gyroimage);
        gyroWidth=50;
        gyroHeight=50;
        coinWidth=50;
        coinHeight=50;
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

    public static void setRanges() {
        goldX = goldXTest;
        goldY = goldYTest;
        silverX = silverXTest;
        silverY = silverYTest;
        bronzeX = bronzeXTest;
        bronzeY = bronzeYTest;
    }

    public static void setTrue() {
        testing = true;
    }
    public static int getGoldX() {
        return goldX;
    }
    public static int getGoldY() {
        return goldY;
    }
    public static int getSilverX() {
        return silverX;
    }
    public static int getSilverY() {
        return silverY;
    }
    public static int getBronzeX() {
        return bronzeX;
    }
    public static int getBronzeY() {
        return bronzeY;
    }
    public static ImageView getSilver() {
        return silver;
    }

    public static float getGyroX() {
        return gyro.getX();
    }

    public static float getGyroY() {
        return gyro.getY();
    }

    public static void setGold(float xcoord, float ycoord) {
        gold.setX(xcoord);
        gold.setY(ycoord);
    }
    public static void setSilver(float xcoord, float ycoord) {
        silver.setX(xcoord);
        silver.setY(ycoord);
    }
    public static void setBronze(float xcoord, float ycoord) {
        bronze.setX(xcoord);
        bronze.setY(ycoord);
    }

    public static int getGoldCount() {
        return goldCount;
    }

    public static int getSilverCount() {
        return silverCount;
    }

    public static int getBronzeCount() {
        return bronzeCount;
    }

    public static void setUpCollisionGold() {
        gold.setX(gyro.getX());
        gold.setY(gyro.getY());
    }

    public static void setCollisionTrue(boolean b) {
        collision = b;
    }
    public static boolean collision = false;
}
