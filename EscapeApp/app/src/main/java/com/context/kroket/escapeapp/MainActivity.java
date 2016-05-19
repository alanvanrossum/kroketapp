package com.context.kroket.escapeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Responsible for making sure the player can connect to and start the game.
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

}
