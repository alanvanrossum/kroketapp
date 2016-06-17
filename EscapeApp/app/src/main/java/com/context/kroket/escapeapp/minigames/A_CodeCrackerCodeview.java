package com.context.kroket.escapeapp.minigames;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.mainscreens.WaitingActivity;
import com.context.kroket.escapeapp.network.ConnectionService;

/**
 * This activity is part of the minigame A. Responsible for showing the
 * assignment.
 */
public class A_CodeCrackerCodeview extends AppCompatActivity {

  // The answer to this minigame.
  public final String correctCode = "2719173";
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
    setContentView(R.layout.a_code_cracker_codeview);

    findViewById(R.id.verifyMessage).setVisibility(View.INVISIBLE);
  }

  /**
   * Binds to ConnectionService.
   */
  @Override
  protected void onStart() {
    super.onStart();

    Intent intent = new Intent(this, ConnectionService.class);
    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    // Change the current activity to this
    ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
  }

  /**
   * When the picture button is clicked, the A_Code_Cracker_Pictureview is
   * started.
   *
   * @param view
   *          the view that was clicked.
   */
  public void picButton(View view) {
    Intent intent = new Intent(this, A_Code_Cracker_Pictureview.class);
    startActivity(intent);
  }

  /**
   * Verifies if the code that is entered is correct.
   *
   * @param view
   *          the view that was clicked.
   */
  public void verifyButton(View view) {
    EditText answer = (EditText) findViewById(R.id.answerA);

    // Check if the code is correct.
    if (answer.getText().toString().matches(correctCode)) {
      // Send message to server that minigame A is finished.
      if (serviceIsBound) {
        connectionService.endA();
      } else {
        System.out.println("ConnectionService not bound in CodeCrackerCodeView");
      }

      // Go to the waiting screen.
      Intent intent = new Intent(this, WaitingActivity.class);
      startActivity(intent);
    } else {
      TextView verifyMessage = (TextView) findViewById(R.id.verifyMessage);
      numberOfAttempts++;
      verifyMessage.setVisibility(View.VISIBLE);
      verifyMessage.setText("The answer you entered is incorrect! number of attempts: " 
            + numberOfAttempts);
    }
  }

  /**
   * Disables going back to the previous activity.
   */
  @Override
  public void onBackPressed() {
  }

  // ******************************************************//
  // *********** ONLY USED FOR TESTING PURPOSES ***********//
  // ******************************************************//

  public static int numberOfAttempts = 0;

  public static int getNumberOfAttempts() {
    return numberOfAttempts;
  }

}
