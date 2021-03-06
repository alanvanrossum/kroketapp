package com.context.kroket.escapeapp.minigames;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.R;

import java.util.ArrayList;

public class C_ColorSequence extends AppCompatActivity {

  // The color sequence.
  public static ArrayList<Integer> colorSequence;

  // Receiver for broadcastst.
  Receiver updateReceiver;

  // Handler for the timer.
  Handler timeHandler = new android.os.Handler();

  // Counter for position in the arraylist.
  public int counter = 0;

  // Counter for the number of times the colors
  // have been shown correctly. Mainly used for
  // testing.
  public static int runtroughs = 0;

  // Returns the colorSequence ArrayList.
  // Mainly used for testing.
  public static ArrayList<Integer> getSequence() {
    return colorSequence;
  }

  /**
   * Initializes the layout.
   *
   * @param savedInstanceState
   *          If the activity is being re-initialized after previously being
   *          shut down then this Bundle contains the data it most recently
   *          supplied.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.c_color_sequence);
  }

  /**
   * Register to the broadcast for the colorsequence. Change the current
   * activity to this one.
   */
  @Override
  protected void onStart() {

    updateReceiver = new Receiver();
    registerReceiver(updateReceiver, new IntentFilter("colorBroadcast"));

    super.onStart();

    // Change the current activity to this.
    ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
  }

  /**
   * Called when the start button in minigame C is clicked. Starts the color
   * range.
   *
   * @param view
   *          the view that was clicked.
   */
  public void startC(View view) {
    // Show the color sequence.
    timeHandler.postDelayed(updateColorThread, 0);
    runtroughs++;
  }

  /**
   * Put the command with colors in an arraylist.
   *
   * @param params
   *          the parameters of the command, which contains the colors.
   */
  public static void parseColors(ArrayList<String> params) {
    colorSequence = new ArrayList<Integer>();

    int pointer = 0;
    while (pointer < params.size()) {

      String colorString = params.get(pointer);
      int color;
      switch (colorString) {
      case "RED":
        colorSequence.add(Color.RED);
        break;
      case "GREEN":
        colorSequence.add(Color.GREEN);
        break;
      case "BLUE":
        colorSequence.add(Color.BLUE);
        break;
      case "YELLOW":
        colorSequence.add(Color.YELLOW);
        break;
      default:
        break;
      }
      pointer++;
    }

    // Add white at the end, so the screen becomes white again.
    colorSequence.add(Color.WHITE);
  }

  /**
   * Runnable to update the background color after a certain amout of time.
   */
  private Runnable updateColorThread = new Runnable() {
    public void run() {
      View view = findViewById(R.id.layout_rec);
      LayerDrawable bgDrawable = (LayerDrawable) view.getBackground();
      final GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.shape_rec);
      shape.setColor(colorSequence.get(counter));

      counter++;

      timeHandler.postDelayed(this, 200); // Will repeat after 200 ms.

      if (counter >= colorSequence.size()) {
        timeHandler.removeCallbacks(updateColorThread);

        counter = 0;
        return;
      }
    }
  };

  /**
   * Class for receiving broadcasts.
   */
  private static class Receiver extends BroadcastReceiver {

    /**
     * Defines what should happen when a message is received.
     *
     * @param context
     *          The Context in which the receiver is running.
     * @param intent
     *          The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
      ArrayList<String> params = (ArrayList<String>) intent.getExtras().get("colorSequence");
      parseColors(params);
    }
  }

  /**
   * Return the runtroughs Integer used to see the number of successful times
   * the stream of colours has passed the screen. Mainly used for testing.
   *
   * @return int runtroughs
   */
  public static int getRunthroughs() {
    return runtroughs;
  }

  /**
   * Disables going back to the previous activity.
   */
  @Override
  public void onBackPressed() {
  }
}
