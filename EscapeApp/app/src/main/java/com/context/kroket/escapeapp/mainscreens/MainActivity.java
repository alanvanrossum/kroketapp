package com.context.kroket.escapeapp.mainscreens;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.minigames.A_CodeCrackerCodeview;
import com.context.kroket.escapeapp.minigames.A_Code_Cracker_Pictureview;
import com.context.kroket.escapeapp.minigames.B_TapGame;
import com.context.kroket.escapeapp.minigames.C_ColorSequence;

import com.context.kroket.escapeapp.network.ConnectionService;
import com.context.kroket.escapeapp.network.GameClient;

/**
 * Responsible for making sure the player can connect to and start the game.
 */
public class MainActivity extends AppCompatActivity {

  private final String TAG = this.getClass().getSimpleName();

  public static Context mContext;

  public static Button startButton;
  /**
   * Method that makes the calls necessary to connect the players to the server.
   *
   * @param view
   *          is the view that was clicked.
   */
  public void connectButton(View view) {
    EditText name = (EditText) findViewById(R.id.player_name);
    TextView connectMessage = (TextView) findViewById(R.id.connectionMessage);
    Button start = (Button) findViewById(R.id.startButton);
    TextView remoteHost = (TextView) findViewById(R.id.remoteHost);

    boolean connected = GameClient.isConnected();
    
    if (connected) {
      connectMessage.setText("Already connected.");
      view.setEnabled(false);
      remoteHost.setEnabled(false);
      start.setEnabled(true);
      return;
    }

    // First check if the player has entered his/her name.
    if (name.getText().toString().matches("")) {
      connectMessage.setText("Enter your name first!");
      return;
    }
    
    Button connectButton = (Button) findViewById(R.id.connectButton);
    connectMessage.setText("Trying to connect...");
    connectButton.setEnabled(false);
    remoteHost.setEnabled(false);
    name.setEnabled(false);

    // Connect to server, if this succeeds set connected boolean to true.
    Intent intent = new Intent(this, ConnectionService.class);
    intent.putExtra("string_name", name.getText().toString());
    intent.putExtra("remote_address", remoteHost.getText().toString());

    startService(intent);
  }

  /**
   * Updates the UI for the connection.
   */
  public void update() {
    EditText name = (EditText) findViewById(R.id.player_name);
    TextView connectMessage = (TextView) findViewById(R.id.connectionMessage);
    Button start = (Button) findViewById(R.id.startButton);
    Button connectButton = (Button) findViewById(R.id.connectButton);
    TextView remoteHost = (TextView) findViewById(R.id.remoteHost);

    boolean connected = GameClient.isConnected();

    if (connected) {
      connectMessage.setText("Connection established, tap START to begin!");
      connectButton.setEnabled(false);
      name.setEnabled(false);
      remoteHost.setEnabled(false);
      start.setEnabled(true);

    } else {
      connectMessage.setText("Connection failed. :(");
      start.setEnabled(false);

      name.setEnabled(true);
      remoteHost.setEnabled(true);

      connectButton.setEnabled(true);
    }
  }

  /**
   * Method that starts the game.
   *
   * @param view
   *          the view that was clicked.
   */
  public void startButton(View view) {
    Intent intent = new Intent(this, IntroActivity.class);
    startActivity(intent);
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

    setContentView(R.layout.main_activity);
    mContext = this;
    startButton = (Button) findViewById(R.id.startButton);

  }

  /**
   * Disables going back to the previous activity.
   */
  @Override
  public void onBackPressed() {
  }

  /**
   * Executed when this activity is started. Disables the start button.
   */
  @Override
  public void onStart() {
    super.onStart();

    Button start = (Button) findViewById(R.id.startButton);
    if (start != null) {
      start.setEnabled(false);
    }

    // Change the current activity to this
    ((ActivityManager) this.getApplicationContext()).setCurrentActivity(this);
  }

  // ******************************************************//
  // *********** ONLY USED FOR TESTING PURPOSES ***********//
  // ******************************************************//

  /**
   * ActivitySwitch is an enum used by the test classes to quickly switch
   * between all the views.
   */
  public enum ActivitySwitch {
    acode(A_CodeCrackerCodeview.class), gameover(GameOver.class),
    apic(A_Code_Cracker_Pictureview.class), btap(B_TapGame.class),
    csequence(C_ColorSequence.class), gamewon(GameWon.class),
    intro(IntroActivity.class), notest(MainActivity.class);

    private Class ClassAC;

    ActivitySwitch(Class acClass) {
      this.ClassAC = acClass;
    }

    public Class returnClass() {
      return ClassAC;
    }

    public void switchToActivity() {
      Intent dialogIntent = new Intent(mContext, TestActivity.returnClass());
      dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      mContext.startActivity(dialogIntent);
    }
  }

  // The ActivitySwitch used by the testclasses.
  public static ActivitySwitch TestActivity = ActivitySwitch.notest;
}
