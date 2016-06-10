package com.context.kroket.escapeapp.minigames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;

import java.util.Random;

public class E_Squasher extends AppCompatActivity {

  ImageButton bugButton;
  TextView tv;
  float screenHeight;
  float screenWidth;
  int squashCount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.e__squasher);
    addListenerToBugButton();
    DisplayMetrics metrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    screenHeight = metrics.heightPixels;
    screenHeight *= 0.7;
    screenWidth = metrics.widthPixels;
    screenWidth *= 0.7;
    squashCount = 0;
  }

  private void addListenerToBugButton() {
    bugButton = (ImageButton) findViewById(R.id.bugbutton);
    bugButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        squashCount++;
        tv = (TextView) findViewById(R.id.squashtext);

        tv.setText("Bugs squashed: " + squashCount);
        setRandomBugLocation();
      }

    });
  }

  private void setRandomBugLocation() {
    Random rand = new Random();
    int newX = rand.nextInt((int) screenWidth);
    int newY = rand.nextInt((int) screenHeight);
    bugButton.setX(newX);
    bugButton.setY(newY);
    setRandomImageSource();
    setRandomBugRotation();
  }

  private void setRandomImageSource() {
    Random rand = new Random();
    switch (rand.nextInt(5)) {
      case 0:
        bugButton.setImageDrawable(getResources().getDrawable(
            R.drawable.spider, getApplicationContext().getTheme()));
        break;
      case 1:
        bugButton.setImageDrawable(getResources().getDrawable(
            R.drawable.bug, getApplicationContext().getTheme()));
        break;
      case 2:
        bugButton.setImageDrawable(getResources().getDrawable(
            R.drawable.beetle, getApplicationContext().getTheme()));
        break;
      case 3:
        bugButton.setImageDrawable(getResources().getDrawable(
            R.drawable.mosquito, getApplicationContext().getTheme()));
        break;
      case 4:
        bugButton.setImageDrawable(getResources().getDrawable(
            R.drawable.sowbug, getApplicationContext().getTheme()));
        break;
      default:
        break;
    }

  }

  private void setRandomBugRotation() {
    Random rand = new Random();
    int rotation = rand.nextInt(359);
    bugButton.setRotation(bugButton.getRotation() + (rotation));
  }
}
