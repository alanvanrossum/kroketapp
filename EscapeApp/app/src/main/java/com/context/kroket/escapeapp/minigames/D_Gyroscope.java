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
}
