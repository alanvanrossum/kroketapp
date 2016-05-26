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
    private ImageView gyro,gold,silver,bronze;
    float screenWidth,screenHeight,gyroWidth,gyroHeight,coinWidth,coinHeight;

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

        float minX = 0-gyroWidth;
        float maxX = screenWidth;
        float minY = 0-gyroHeight;
        float maxY = screenHeight;

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
    }

    private void placeCoinsRandomly(float gyroX,float gyroY) {
        //unavailable range: gyroX-gyroWidth, gyroX+2*gyroWidth, gyroY-gyroWidth, gyroY+2*gyroHeight
        Random rand = new Random();
        int xRange = Math.round(screenWidth);
        int yRange = Math.round(screenHeight);
        System.out.println(screenWidth);
        System.out.println(Math.round(screenWidth));
        System.out.println(screenHeight);
        System.out.println(Math.round(screenHeight));

        //test
        //
        int goldX = rand.nextInt(xRange);
        int goldY = rand.nextInt(yRange);
//        if(goldX>gyroX-gyroWidth){
//            goldX+=3*gyroWidth;
//        }
//        if(goldY>gyroY-gyroHeight){
//            goldY+=3*gyroHeight;
//        }
        int silverX = rand.nextInt(xRange);
        int silverY = rand.nextInt(yRange);
//        if(silverX>gyroX-gyroWidth){
//            silverX+=3*gyroWidth;
//        }
//        if(silverX>goldX){
//            silverX+=coinWidth;
//        }
//        if(silverY>gyroY-gyroHeight){
//            silverY+=3*gyroHeight;
//        }
//        if(silverY>goldY){
//            silverY+=coinHeight;
//        }
        int bronzeX = rand.nextInt(xRange);
        int bronzeY = rand.nextInt(yRange);
//        if(bronzeX>gyroX-gyroWidth){
//            bronzeX+=3*gyroWidth;
//        }
//        if(bronzeX>goldX){
//            bronzeX+=coinWidth;
//        }
//        if(bronzeX>silverX){
//            bronzeX+=coinWidth;
//        }
//        if(bronzeY>gyroY-gyroHeight){
//            bronzeY+=3*gyroHeight;
//        }
//        if(bronzeY>goldY){
//            bronzeY+=coinHeight;
//        }
//        if(bronzeY>silverY){
//            bronzeY+=coinHeight;
//        }

        gold.setX(goldX);
        gold.setY(goldY);
        silver.setX(silverX);
        silver.setY(silverY);
        bronze.setX(bronzeX);
        bronze.setY(bronzeY);
    


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

        //set screenwidth/height



    }



    @Override
    protected void onStart() {
        super.onStart();
        //Change the current activity.
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
        placeCoinsRandomly(gyro.getX(),gyro.getY());
        ((ActivityManager)this.getApplicationContext()).setCurrentActivity(this);
    }


}
