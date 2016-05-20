package com.context.kroket.escapeapp;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Responsible for making sure the player can connect to and start the game.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * boolean used to start game_AA_Activity intent.
	 
     */
    private static boolean gameAA = false;

    /**
     * boolean used to start game_A_Activity intent.
     */
    private static boolean gameA = false;

    /**
     * boolean used to start game_B_Activity intent.
     */
    private static boolean gameB = false;

    /**
     * boolean used to enable the start button.
     */
    private static boolean enableStart = false;

    /**
     * Method that makes the calls necessary to connect the players to the server.
     * @param view is the view that was clicked.
     */
    public void connectButton(View view) {
        EditText name = (EditText) findViewById(R.id.player_name);
        TextView connectMessage = (TextView) findViewById(R.id.connectionMessage);
        Button start = (Button) findViewById(R.id.startButton);
        boolean connect = false;
        checkConditions();
        //first check if the player has entered his/her name
        if (name.getText().toString().matches("")) {
            connectMessage.setText("Enter your name first!");
            return;
        }

        //connect to server, if this succeeds set connect boolean to true
        Intent intent = new Intent(this, ConnectionService.class);
        intent.putExtra("string_name", name.getText().toString());
        startService(intent);

        connect = true;

        //Change connect message and enable start button
        if (connect) {
            connectMessage.setText("connected");
            if (start != null) {
                start.setEnabled(true);
            }
        }
    }

    /**
     * checkConditions is a method used for testing only. for testing purposes we need to
     * be able to switch between activities quickly. checkConditions enables us to do that.
     */
    private void checkConditions() {
        if(gameAA == true) {
            Intent dialogIntent = new Intent(this, Game_AA_Activity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);

        }
        if(gameA == true) {
            Intent dialogIntent = new Intent(this, Game_A_Activity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }
        if(gameB == true) {
            Intent dialogIntent = new Intent(this, Game_B_Activity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }

        if(enableStart == true){
            Button start = (Button) findViewById(R.id.startButton);
            start.setEnabled(true);
        }
    }

    /**
     * Method that starts the game.
     *
     * @param view is the view that was clicked.
     */
    public void startButton(View view) {
        Intent intent = new Intent(this, WaitingActivity.class);
        startActivity(intent);
    }

    /**
     * Initializes the layout.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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
    }

    /**
     * switchGameA allows us to change the gameA boolean.
     * This GameA boolean is used to quickly switch to Game_A_Activity.
     * This method is only used for testing.
     *
     * @param b Boolean
     */
    public static void switchGameA(Boolean b) {
        gameA = b;
    }

    /**
     * switchGameAA allows us to change the gameAA boolean.
     * This GameAA boolean is used to quickly switch to Game_AA_Activity.
     * This method is only used for testing.
     *
     * @param b Boolean
     */
    public static void switchGameAA(Boolean b) { gameAA = b; }

    /**
     * switchGameB allows us to change the gameB boolean.
     * This GameB boolean is used to quickly switch to Game_B_Activity.
     * This method is only used for testing.
     *
     * @param b Boolean
     */
    public static void switchGameB(Boolean b) { gameB = b; }

    /**
     * switchGameA allows us to change the enableStart boolean.
     * This enableStart boolean is used to set the startbutton to enabled
     * so it can be tested. This method is only used for testing.
     *
     * @param b Boolean
     */
    public static void switchStart(Boolean b) { enableStart = b; }

}
