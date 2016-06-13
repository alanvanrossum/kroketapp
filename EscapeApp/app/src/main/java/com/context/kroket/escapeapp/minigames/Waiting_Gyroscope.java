package com.context.kroket.escapeapp.minigames;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.minigames.coin.coins.BronzeCoin;
import com.context.kroket.escapeapp.minigames.coin.Coin;
import com.context.kroket.escapeapp.minigames.coin.coins.DeadCoin;
import com.context.kroket.escapeapp.minigames.coin.coins.GoldCoin;
import com.context.kroket.escapeapp.minigames.coin.coins.SilverCoin;
import com.context.kroket.escapeapp.network.ConnectionService;

import java.util.ArrayList;
import java.util.Random;

/**
 * Minigame that can be played in the waitingscreen.
 */
public class Waiting_Gyroscope extends AppCompatActivity implements SensorEventListener {
  private SensorManager motionSensorManager;
  private Sensor motionSensor;

  private ImageView gyro;
  float screenWidth;
  float screenHeight;
  float gyroWidth;
  float gyroHeight;
  float minX;
  float maxX;
  float minY;
  float maxY;
  private Coin gold;
  private Coin silver;
  private Coin bronze;
  private Coin dead;
  private ArrayList<Coin> coins = new ArrayList<>();;
  TextView amountView;

  /**
   * This method is called when the gyroscopic value of the mobile device
   * changes. This method sets the location of the skullball depending on the
   * current gyroscopic values and the old skullball location.
   *
   * @param event
   *          Android Object that contains a .values array which contains the
   *          gyroscopic values of the phone, a.k.a. the rotation of the phone
   *          event.values[0]: x*sin(θ/2) event.values[1]: y*sin(θ/2)
   *          event.values[2]: z*sin(θ/2) event.values[3]: cos(θ/2)
   */
  @Override
  public void onSensorChanged(SensorEvent event) {
    float oldX = gyro.getX();
    float oldY = gyro.getY();
    gyro.setX(clamp((oldX + event.values[0] * -35), minX, maxX));
    gyro.setY(clamp((oldY + event.values[1] * 35), minY, maxY));
    collide();
  }

  /**
   * This method is called everyime a new location is set for the skullball.
   * This method makes sure the skullball doesn't leave the screen, by keeping
   * its values between min and max (inclusive)
   *
   * @param value
   *          The new X or Y coordinate of the skullball
   * @param min
   *          The minimum X or Y coordinate that's still in the screen
   * @param max
   *          The maximum X or Y coordinate that's still in the screen.
   * @return the location
   */
  private float clamp(float value, float min, float max) {
    if (value < min) {
      return min;
    }
    if (value > max) {
      return max;
    }
    return value;
  }

  /**
   * This method is called after every movement of the skullball. This method
   * checks if the skullball collides with any of the coins. If it does, it
   * places the coins randomly again by calling placeCoinsRandomly()
   */
  private void collide() {
   //Check if the skullball collided with a coin.
    for (int x = 0; x < coins.size(); x++) {
     Coin tempCoin = coins.get(x);
     if(tempCoin.collideWithGyro(gyro.getX(),gyro.getY())){
       if(tempCoin.equals(dead)){
         resetCounts();
         amountView.setText("Score: " + Integer.toString(gold.getCount()
                 + silver.getCount() + bronze.getCount()));
         placeCoinsRandomly();
       } else {
         placeCoinsRandomly();
         amountView.setText("Score: " + Integer.toString(gold.getCount()
                 + silver.getCount() + bronze.getCount()));
       }
     }
   }

    // Check if the bonus time should be received.
    if (gold.getCount() + silver.getCount() + bronze.getCount() >= 50) {
      resetCounts();
      amountView.setText("BONUS TIME RECEIVED!");
      connectionService.bonusTime();
    }
  }

  /**
   * Resets the counts of the gold, silver and bronze coins.
   */
  public void resetCounts() {
    gold.setCount(0);
    silver.setCount(0);
    bronze.setCount(0);
  }

  /**
   * Basic version of placeCoinsRandomly, which calls the main method with zeros
   * as variables.
   */
  private void placeCoinsRandomly() {
    placeCoinsRandomly(0, 0);
  }

  /**
   * Method that places all coins randomly, and makes sure they don't collide
   * with the skullball initially
   *
   * @param offsetX
   *          Used in the initial randomisation which is a little different than
   *          the others
   * @param offsetY
   *          Idem.
   */
  private void placeCoinsRandomly(float offsetX, float offsetY) {
    // unavailable range: gyroX-gyroWidth, gyroX+2*gyroWidth, gyroY-gyroWidth,
    // gyroY+2*gyroHeight
    float gyroX = gyro.getX() + offsetX;
    float gyroY = gyro.getY() + offsetY;
    Random rand = new Random();
    int width = Math.round(screenWidth - 3 * gyroWidth);
    int height = Math.round(screenHeight - 3 * gyroHeight);

    gold.placeRandomly(rand, width, height, gyroX, gyroY);
    silver.placeRandomly(rand, width, height, gyroX, gyroY);
    bronze.placeRandomly(rand, width, height, gyroX, gyroY);
    dead.placeRandomly(rand, width, height, gyroX, gyroY);
  }

  /**
   * Unused Method, but has to be implemented when Implementing
   * SensorEventListener.
   *
   * @param sensor the sensor
   * @param accuracy the accuracy
   */
  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

  /**
   * This method created the motionSensorManager and MotionSensor, and sets the
   * update speed to FASTEST, to ensure smooth gameplay
   *
   * @param savedInstanceState
   *          If the activity is being re-initialized after previously being
   *          shut down then this Bundle contains the data it most recently
   *          supplied.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.waiting_gyroscope);
    motionSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    motionSensor = motionSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    motionSensorManager.registerListener(this, motionSensor, SensorManager.SENSOR_DELAY_FASTEST);
    amountView = ((TextView) findViewById(R.id.coinAmount));
  }

  /**
   * This method initalizes the screenWidth and screenHeight variables, which
   * are used to place the coins and keep the skullball in the screen. After
   * this it gets the coins by their viewID, so they can be easily placed later.
   * It also calls for the inital randomisation of the coin locations, which
   * should make sure they don't spawn on top of the skullball.
   */
  @Override
  protected void onStart() {
    super.onStart();

    initCoins();
    initGyro();
    initScreen();

    // Bind this service.
    Intent intent = new Intent(this, ConnectionService.class);
    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    // Change the current activity.
    ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
  }

  /**
   * Initialize the gyro image and properties.
   */
  private void initGyro() {
    gyro = (ImageView) findViewById(R.id.gyroimage);
    gyroWidth = 50;
    gyroHeight = 50;
  }

  /**
   * Initialize the screen properties and places the coins randomly.
   */
  private void initScreen() {
    DisplayMetrics metrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    screenHeight = metrics.heightPixels;
    screenHeight *= 0.8;
    screenWidth = metrics.widthPixels;
    screenWidth *= 0.8;

    minX = 0;
    maxX = screenWidth - gyroWidth;
    minY = 0;
    maxY = screenHeight - gyroHeight;

    placeCoinsRandomly(screenWidth / 2 - gyroWidth / 2, screenHeight / 2 - gyroHeight / 2);
  }

  /**
   * Initialize the coins.
   */
  private void initCoins() {
    gold = new GoldCoin((ImageView) findViewById(R.id.goldcoin));
    silver = new SilverCoin((ImageView) findViewById(R.id.silvercoin));
    bronze = new BronzeCoin((ImageView) findViewById(R.id.bronzecoin));
    dead = new DeadCoin((ImageView) findViewById(R.id.deadcoin));

    coins.add(gold);
    coins.add(silver);
    coins.add(bronze);
    coins.add(dead);
  }

  /* Methods for the connection. */

  ConnectionService connectionService;
  boolean serviceIsBound = false;

  // Defines callbacks for service binding, used in bindService().
  private ServiceConnection mConnection = new ServiceConnection() {

    /**
     * Called when a connection to the Service has been established.
     *
     * @param className
     *          The concrete component name of the service that has been
     *          connected.
     * @param service
     *          The IBinder of the Service's communication channel, which you
     *          can now make calls on.
     */
    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
      ConnectionService.myBinder binder = (ConnectionService.myBinder) service;
      connectionService = binder.getService();
      serviceIsBound = true;
    }

    /**
     * Called when a connection to the Service has been lost.
     *
     * @param arg0
     *          The concrete component name of the service whose connection has
     *          been lost.
     */
    @Override
    public void onServiceDisconnected(ComponentName arg0) {
      serviceIsBound = false;
    }
  };

  /**
   * Unbind the service and go back.
   */
  @Override
  public void onBackPressed() {
    unbindService(mConnection);

    super.onBackPressed();
  }
}
