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
import android.view.View;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;

public class D_Gyroscope extends AppCompatActivity {
    private SensorManager motionSensorManager;
    private Sensor motionSensor;
    private SensorEventListener sensorEventListener;

    public void onSensorChanged(SensorEvent event){
//        values[0]: x*sin(θ/2)
//        values[1]: y*sin(θ/2)
//        values[2]: z*sin(θ/2)
//        values[3]: cos(θ/2)
        System.out.println("x: " +event.values[0]);
        System.out.println("y: " +event.values[1]);
        System.out.println("z: " +event.values[2]);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d__gyroscope);
    }

    protected void onStart() {
        super.onStart();
        motionSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        motionSensor = motionSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                onSensorChanged(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };
        motionSensorManager.registerListener(sensorEventListener,motionSensor,SensorManager.SENSOR_DELAY_NORMAL);
        //Change the current activity.
        ((ActivityManager)this.getApplicationContext()).setCurrentActivity(this);
    }


}
