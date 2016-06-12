package com.context.kroket.escapeapp.minigames;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.network.ConnectionService;

import java.util.ArrayList;
import java.util.Random;

public class Waiting_Squasher extends AppCompatActivity {

  ImageButton bugButton;
  TextView squashText;
  float screenHeight;
  float screenWidth;
  int squashCount;
  Random rand;
  Drawable spider;
  Drawable bug;
  Drawable beetle;
  Drawable mosquito;
  Drawable sowbug;

  ArrayList<Drawable> bugs = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.waiting__squasher);
    addListenerToBugButton();
    DisplayMetrics metrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    screenHeight = metrics.heightPixels;
    screenHeight *= 0.7;
    screenWidth = metrics.widthPixels;
    screenWidth *= 0.7;
    squashCount = 0;
    squashText = (TextView) findViewById(R.id.squashtext);
    squashText.setText("Squash the Bugs!");

    rand = new Random();
    spider = ContextCompat.getDrawable(this, R.drawable.spider);
    bug = ContextCompat.getDrawable(this, R.drawable.bug);
    beetle = ContextCompat.getDrawable(this, R.drawable.beetle);
    sowbug = ContextCompat.getDrawable(this, R.drawable.sowbug);
    mosquito = ContextCompat.getDrawable(this, R.drawable.mosquito);
//    spider = getResources().getDrawable(
//            R.drawable.spider, getApplicationContext().getTheme());
//
//    bug = getResources().getDrawable(
//            R.drawable.bug, getApplicationContext().getTheme());
//
//    beetle = getResources().getDrawable(
//            R.drawable.beetle, getApplicationContext().getTheme());
//
//    mosquito = getResources().getDrawable(
//            R.drawable.mosquito, getApplicationContext().getTheme());
//
//    sowbug = getResources().getDrawable(
//            R.drawable.sowbug, getApplicationContext().getTheme());

    bugs.add(spider);
    bugs.add(bug);
    bugs.add(beetle);
    bugs.add(mosquito);
    bugs.add(sowbug);

    // Bind this service.
    Intent intent = new Intent(this, ConnectionService.class);
    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
  }

  /**
   * Adds a listerner to the bug button.
   */
  private void addListenerToBugButton() {
    bugButton = (ImageButton) findViewById(R.id.bugbutton);
    bugButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        squashCount++;
        squashText = (TextView) findViewById(R.id.squashtext);

        if(squashCount >= 50) {
          squashCount = 0;
          squashText.setText("BONUS TIME RECEIVED!");
          connectionService.bonusTime();
        } else {
          squashText.setText("Bugs squashed: " + squashCount);
        }
        setRandomBugLocation();

      }

    });
  }

  /**
   * Picks a random location and sets the bug at this location.
   */
  private void setRandomBugLocation() {
    Random rand = new Random();
    int newX = rand.nextInt((int) screenWidth);
    int newY = rand.nextInt((int) screenHeight);
    bugButton.setX(newX);
    bugButton.setY(newY);
    setRandomImageSource();
    setRandomBugRotation();
  }

  /**
   * Chooses a random bug picture and sets it.
   */
  private void setRandomImageSource() {
   int randInt = rand.nextInt(bugs.size());
    Drawable drawable = bugs.get(randInt);
    if(drawable == null) {
      System.out.println("the drawable was null the corresponding int was:" + randInt);
      squashText.setText("Sorry but an error occurred during this minigame");
      onBackPressed();
    }
    bugButton.setImageDrawable(bugs.get(randInt));

//    switch (rand.nextInt(5)) {
//      case 0:
//        bugButton.setImageDrawable(getResources().getDrawable(
//            R.drawable.spider, getApplicationContext().getTheme()));
//        break;
//      case 1:
//        bugButton.setImageDrawable(getResources().getDrawable(
//            R.drawable.bug, getApplicationContext().getTheme()));
//        break;
//      case 2:
//        bugButton.setImageDrawable(getResources().getDrawable(
//            R.drawable.beetle, getApplicationContext().getTheme()));
//        break;
//      case 3:
//        bugButton.setImageDrawable(getResources().getDrawable(
//            R.drawable.mosquito, getApplicationContext().getTheme()));
//        break;
//      case 4:
//        bugButton.setImageDrawable(getResources().getDrawable(
//            R.drawable.sowbug, getApplicationContext().getTheme()));
//        break;
//      default:
//        break;
//    }

  }

  /**
   * Sets a random rotation for the bug.
   */
  private void setRandomBugRotation() {
    Random rand = new Random();
    int rotation = rand.nextInt(359);
    bugButton.setRotation(bugButton.getRotation() + (rotation));
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
