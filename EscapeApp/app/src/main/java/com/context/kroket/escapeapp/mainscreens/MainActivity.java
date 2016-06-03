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

import com.context.kroket.escapeapp.minigames.F_Lock;
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
        if(TestActivity != ActivitySwitch.notest){
            checkConditions();
        }

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
     * ActivitySwitch is an enum used by the test classes to
     * quickly switch between all the views.
     */
    public enum ActivitySwitch {
        startEn(MainActivity.class),
        acode(A_CodeCrackerCodeview.class),
        apic(A_Code_Cracker_Pictureview.class),
        btap(B_TapGame.class),
        csequence(C_ColorSequence.class),
        notest(MainActivity.class);

        private Class ClassAC;

        ActivitySwitch(Class aCLass){
            this.ClassAC = aCLass;
        }

        public Class returnClass() {
            return ClassAC;
        }
    }

    //The ActivitySwitch used by the testclasses.
    public static ActivitySwitch TestActivity = ActivitySwitch.notest;


    /**
     * checkConditions checks if the ActivitySwitch is startEn.
     * if so it enables the startButton. if that isn't the case we
     * switch to the activity specified in ActivitySwitch.
     */
    private void checkConditions() {
        if(TestActivity == ActivitySwitch.startEn){
            Button start = (Button) findViewById(R.id.startButton);
            start.setEnabled(true);
        }
        else {
            Intent dialogIntent = new Intent(this, TestActivity.returnClass());
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }
    }
}
