package com.context.kroket.escapeapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ConnectionService extends Service {

    private static GameClient tcpClient;
    private static ArrayList<String> list;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String playername = "REGISTER[" + intent.getStringExtra("string_name") + "]";
        String type = "TYPE[MOBILE]";

        list = new ArrayList<String>();
        new connectTask().execute("");

        //Send the name and type of the player to the server
        while (tcpClient == null) {}
        if (tcpClient != null) {
            tcpClient.sendMessage(playername);
            tcpClient.sendMessage(type);
        }

        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {}

    public class connectTask extends AsyncTask<String, String, GameClient> {

        @Override
        protected GameClient doInBackground(String... message) {
            tcpClient = new GameClient(new GameClient.OnMessageReceived() {
                @Override
                public void messageReceived(String mes) {
                    publishProgress(mes);
                }
            });
            System.out.println("tcpClient initialized");
            tcpClient.run();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            list.add(values[0]);
            System.out.println(values[0]);
            String input = values[0];

            if (input.startsWith("INITM[")) {
                int pos = input.indexOf(']');
                String action = input.substring(6, pos);

                //start the minigame belonging to the action string
                System.out.println("Incoming action: " + action);
                if (action.contentEquals("startA")) {
                    startA();
                } else if (action.contentEquals("startB")) {
                    startB();
                }
            }

        }
    }

    public void startA() {
        Intent dialogIntent = new Intent(this, Game_AA_Activity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    public void startB() {
        Intent dialogIntent = new Intent(this, Game_B_Activity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }
}
