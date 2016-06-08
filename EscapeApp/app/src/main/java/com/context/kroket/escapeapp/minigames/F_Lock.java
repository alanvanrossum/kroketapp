package com.context.kroket.escapeapp.minigames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;

import java.util.ArrayList;

public class F_Lock extends AppCompatActivity {
  ImageButton left;
  ImageButton right;
  ImageView turnlock;
  boolean rotatingRight;
  ArrayList<Integer> enteredSequence;

  /**
   * This method adds listeners to the arrow pictures by calling
   * addListenersToArrows() This method also sets the global variable imageView
   * to match the turning lock, so it can be easily rotated. Finally, this
   * method creates a simple arraylist of integers to store the current value of
   * the lock everytime the direction is changed.
   *
   * @param savedInstanceState
   *          If the activity is being re-initialized after previously being
   *          shut down then this Bundle contains the data it most recently
   *          supplied
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.f__lock);
    addListenersToArrows();
    turnlock = (ImageView) findViewById(R.id.turnlock);
    enteredSequence = new ArrayList<Integer>();
  }

  /**
   * This is an initializing method that is called once during the creation of
   * the activity. This method adds a button function to the two arrows in the
   * bottom of the screen, which make calls to rotateRight() and rotateLeft()
   */
  private void addListenersToArrows() {
    left = (ImageButton) findViewById(R.id.arrowleft);
    left.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        rotateLeft();
      }

    });
    right = (ImageButton) findViewById(R.id.arrowright);
    right.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        rotateRight();
      }

    });
  }

  /**
   * This method is called when the RightArrow is pressed This method adds 9 to
   * the rotation, and if the direction is changed it calls to addToSequence().
   */
  private void rotateRight() {
    if (!rotatingRight) {
      rotatingRight = true;
      addToSequence();
      System.out.println(enteredSequence.toString());
    }
    rotate(9);
  }

  /**
   * This method is called when the LeftArrow is pressed. This method subtracts
   * 9 from the rotation, and if the direction is changed it calls to
   * addToSequence()
   */
  private void rotateLeft() {
    if (rotatingRight) {
      rotatingRight = false;
      addToSequence();
      System.out.println(enteredSequence.toString());

    }
    rotate(-9);
  }

  /**
   * This method is called when the user switches from turning Left to turning
   * Right or vice versa. This method adds the number the lock is currently at
   * to the sequence.
   */
  private void addToSequence() {
    if (turnlock.getRotation() > 0) {
      enteredSequence.add(Math.round(40 - (turnlock.getRotation() / 9)));
    } else {
      enteredSequence.add(Math.round(turnlock.getRotation() / -9));
    }
  }

  /**
   * This method sets the rotation for the turning part of the lock. A rotation
   * of 9 degrees corresponds to a rotation of one number.
   *
   * @param alpha
   *          The angle in which the lock must rotate. Can be 9 or -9 for
   *          positive or negative turning respectively.
   */
  private void rotate(int alpha) {
    turnlock.setRotation((turnlock.getRotation() + alpha) % 360);
  }

}
