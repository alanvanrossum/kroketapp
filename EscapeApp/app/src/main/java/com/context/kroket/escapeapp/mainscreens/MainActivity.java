package com.context.kroket.escapeapp.mainscreens;

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
import com.context.kroket.escapeapp.minigames.D_Gyroscope;
import com.context.kroket.escapeapp.minigames.C_ColorSequence;
import com.context.kroket.escapeapp.minigames.E_Squasher;

import com.context.kroket.escapeapp.network.ConnectionService;

/**
 * Responsible for making sure the player can connect to and start the game.
 * 
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Method that makes the calls necessary to connect the players to the server.
     * @param view is the view that was clicked.
     */
    public void connectButton(View view) {
        EditText name = (EditText) findViewById(R.id.player_name);
        TextView connectMessage = (TextView) findViewById(R.id.connectionMessage);
        Button start = (Button) findViewById(R.id.startButton);
        boolean connect = false;

        //Only used in testing, to quickly forward to another view.
        checkConditions();

        //First check if the player has entered his/her name.
        if (name.getText().toString().matches("")) {
            connectMessage.setText("Enter your name first!");
            return;
        }

        //Connect to server, if this succeeds set connect boolean to true.
        Intent intent = new Intent(this, ConnectionService.class);
        intent.putExtra("string_name", name.getText().toString());
        startService(intent);

        connect = true;

        //Change connect message and enable start button.
        if (connect) {
            connectMessage.setText("connected");
            if (start != null) {
                start.setEnabled(true);
            }
        }
    }


    /**
     * Method that starts the game.
     *
     * @param view the view that was clicked.
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

        setContentView(R.layout.main_activity);
    }

    /**
     * Executed when this activity is started. Disables the start button.
     */
    @Override
    public void onStart() {
        super.onStart();
        //Intent intent = new Intent(this, E_Squasher.class);
        //startActivity(intent);
        
        Button start = (Button) findViewById(R.id.startButton);
        if (start != null) {
            start.setEnabled(false);
        }

        //Change the current activity to this
        ((ActivityManager)this.getApplicationContext()).setCurrentActivity(this);
    }



    //******************************************************//
    //*********** ONLY USED FOR TESTING PURPOSES ***********//
    //******************************************************//

    /**
     * Boolean used to start game_AA_Activity intent.
     */
    private static boolean gameAA = false;

    /**
     * Boolean used to start game_A_Activity intent.
     */
    private static boolean gameA = false;

    /**
     * Boolean used to start game_B_Activity intent.
     */
    private static boolean gameB = false;

    /**
     * Boolean used to start game_C_Activity intent.
     */
    private static boolean gameC = false;

    /**
     * Boolean used to enable the start button.
     */
    private static boolean enableStart = false;

    /**
     * SwitchGameA allows us to change the gameA boolean.
     * This GameA boolean is used to quickly switch to A_Code_Cracker_Pictureview.
     * This method is only used for testing.
     *
     * @param b Boolean.
     */
    public static void switchGameA(Boolean b) {
        gameA = b;
    }

    /**
     * SwitchGameAA allows us to change the gameAA boolean.
     * This GameAA boolean is used to quickly switch to A_CodeCrackerCodeview.
     * This method is only used for testing.
     *
     * @param b Boolean.
     */
    public static void switchGameAA(Boolean b) { gameAA = b; }

    /**
     * SwitchGameB allows us to change the gameB boolean.
     * This GameB boolean is used to quickly switch to B_TapGame.
     * This method is only used for testing.
     *
     * @param b Boolean.
     */
    public static void switchGameB(Boolean b) { gameB = b; }

    /**
     * SwitchGameB allows us to change the gameB boolean.
     * This GameB boolean is used to quickly switch to B_TapGame.
     * This method is only used for testing.
     *
     * @param b Boolean.
     */
    public static void switchGameC(Boolean b) { gameC = b; }

    /**
     * SwitchGameA allows us to change the enableStart boolean.
     * This enableStart boolean is used to set the startbutton to enabled
     * so it can be tested. This method is only used for testing.
     *
     * @param b Boolean.
     */
    public static void switchStart(Boolean b) { enableStart = b; }

    /**
     * CheckConditions is a method used for testing only. for testing purposes we need to
     * be able to switch between activities quickly. checkConditions enables us to do that.
     */
    private void checkConditions() {
        if(gameAA == true) {
            Intent dialogIntent = new Intent(this, A_CodeCrackerCodeview.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);

        }
        if(gameA == true) {
            Intent dialogIntent = new Intent(this, A_Code_Cracker_Pictureview.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }
        if(gameB == true) {
            Intent dialogIntent = new Intent(this, B_TapGame.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }

        if(gameC == true) {
            Intent dialogIntent = new Intent(this, C_ColorSequence.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }

        if(enableStart == true){
            Button start = (Button) findViewById(R.id.startButton);
            start.setEnabled(true);
        }


    }

}
