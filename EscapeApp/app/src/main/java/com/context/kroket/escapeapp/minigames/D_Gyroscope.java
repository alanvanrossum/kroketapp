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

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;

public class D_Gyroscope extends AppCompatActivity implements SensorEventListener{
    private SensorManager motionSensorManager;
    private Sensor motionSensor;
    private ImageView gold,silver,bronze;

    @Override
    public void onSensorChanged(SensorEvent event){
//        event.values[0]: x*sin(θ/2)
//        event.values[1]: y*sin(θ/2)
//        event.values[2]: z*sin(θ/2)
//        event.values[3]: cos(θ/2)
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float screenHeight= metrics.heightPixels;
        float screenWidth = metrics.widthPixels;

        ImageView s = (ImageView) findViewById(R.id.gyroimage);

        float oldX = s.getX();
        float oldY = s.getY();

        s.setX(oldX+event.values[0]*-35);
        s.setY(oldY+event.values[1]*35);
        float newX = s.getX();
        float newY = s.getY();

        float minX = 0-s.getWidth();
        float maxX = screenWidth;
        float minY = 0-s.getHeight();
        float maxY = screenHeight;

        if(newX<minX){
           s.setX(minX);
        }
        if(newX>maxX){
            s.setX(maxX);
        }
        if(newY<minY){
            s.setY(minY);
        }
        if(newY>maxY){
            s.setY(maxY);
        }
    }

    private void placeCoinsRandomly(int i) {
        
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

        //set the coins
        ImageView gold = (ImageView) findViewById(R.id.goldcoin);
        ImageView silver = (ImageView) findViewById(R.id.silvercoin);
        ImageView bronze = (ImageView) findViewById(R.id.bronzecoin);
        placeCoinsRandomly(0);

    }



    @Override
    protected void onStart() {
        super.onStart();
        //Change the current activity.
        ((ActivityManager)this.getApplicationContext()).setCurrentActivity(this);
    }


}
