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
//        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.activity_main, null);
//
//        EditText name = (EditText) layout.findViewById(R.id.player_name);
//        System.out.println("naaaaaame " + name.getText().toString());
//        MainActivity.class.getField(playername);
        String playername = "REGISTER[" + intent.getStringExtra("string_name") + "]";
        String type = "TYPE[MOBILE]";

        list = new ArrayList<String>();
        new connectTask().execute("");

//        String message = "hallohallo";
//        list.add("client: " + message);
//        System.out.println("message: " + message);

        while (tcpClient == null) {}
        if (tcpClient != null) {
            System.out.println("send");
            //tcpClient.sendMessage(message);
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
        }
    }
}
