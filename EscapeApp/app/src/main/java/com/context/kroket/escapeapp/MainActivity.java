package com.context.kroket.escapeapp;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.MessageFormat;
import java.util.ArrayList;

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
        Intent intent = new Intent(this, Game_AA_Activity.class);
        startActivity(intent);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //uncomment to test minigame B
//        Intent intent = new Intent(this, Game_B_Activity.class);
//        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button connect = (Button) findViewById(R.id.connectButton);
        Button start = (Button) findViewById(R.id.startButton);
        if (start != null) {
            start.setEnabled(false);
        }
//        if (connect != null) {
//            connect.setEnabled(false);
//        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.context.kroket.escapeapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        //enterName();
    }
//
//    public void enterName() {
//        EditText name = (EditText) findViewById(R.id.player_name);
//        Button connect = (Button) findViewById(R.id.connectButton);
//
//        //wait for player to enter his/her name
//        while (name.getText().toString().matches("")) {}
//        connect.setEnabled(true);
//    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.context.kroket.escapeapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
