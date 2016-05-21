package com.context.kroket.escapeapp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

/**
 * This service is responsible for registering players by sending information
 * to the server, and for receiving and acting on messaged sent by the server.
 */
public class ConnectionService extends Service {

    private static GameClient tcpClient;
    private static ArrayList<String> list;
    // Binder given to clients
    private final IBinder binder = new myBinder();
    public String colorSeq;

    /**
     * Called by the system every time a client explicitly starts the service.
     * This method registers the player by sending the player's name and the
     * type to the server.
     *
     * @param intent The Intent supplied to {@link android.content.Context#startService},
     * as given.  This may be null if the service is being restarted after
     * its process has gone away, and it had previously returned anything
     * except {@link #START_STICKY_COMPATIBILITY}.
     * @param flags Additional data about this start request. Currently either
     * 0, {@link #START_FLAG_REDELIVERY}, or {@link #START_FLAG_RETRY}.
     * @param startId A unique integer representing this specific request to
     * start.
     * @return The return value indicates what semantics the system should
     * use for the service's current started state.  It may be one of the
     * constants associated with the {@link #START_CONTINUATION_MASK} bits.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String playername = "REGISTER[" + intent.getStringExtra("string_name") + "]";
        String type = "TYPE[MOBILE]";

        list = new ArrayList<String>();
        new connectTask().execute("");

        //Send the name and type of the player to the server
        while (tcpClient == null) {}
        tcpClient.sendMessage(playername);
        tcpClient.sendMessage(type);

        return START_STICKY;
    }

    /**
     * Check if current activity is waiting activity.
     */
    private boolean inWaitingActivity() {
        return (((App)this.getApplicationContext())
                    .getCurrentActivity().getLocalClassName()
                    .equals(WaitingActivity.class.getSimpleName()));
    }

    /**
     * Starts the minigame A: Game_AA_Activity
     */
    private void startA() {
        Intent dialogIntent = new Intent(this, Game_AA_Activity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    /**
     * Starts the minigame B: Game_B_Activity
     */
    private void startB() {
        Intent dialogIntent = new Intent(this, Game_B_Activity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    /**
     * Starts the minigame C: Game_C_Activity
     */
    private void startC() {
        BroadcastThread myThread = new BroadcastThread();
        myThread.start();

        Intent dialogIntent = new Intent(this, Game_C_Activity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    /**
     * Thread for sending information to activities.
     */
    public class BroadcastThread extends Thread{

        /**
         * Run the thread: broadcast information.
         */
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                Intent in = new Intent();
                in.setAction("colorBroadcast");
                in.putExtra("colorSequence", colorSeq);
                sendBroadcast(in);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stopSelf();
        }
    }

    /**
     * Ends any minigame. Returns to the waiting screen.
     */
    public void endMinigame() {
        Intent dialogIntent = new Intent(this, WaitingActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    /**
     * Sends a message to the server that minigame B is solved.
     */
    public void endB() {
        tcpClient.sendMessage("INITVR[doneB]");
    }

    /**
     * Sends a message to the server that minigame A is solved.
     */
    public void endA() {
        tcpClient.sendMessage("INITVR[doneA]");
    }

    /**
     * Class used for the client Binder.
     */
    public class myBinder extends Binder {
        ConnectionService getService() {
            return ConnectionService.this;
        }
    }

    /**
     * Return the communication channel to the service.
     *
     * @param intent The Intent that was used to bind to this service.
     * @return the binder
     */
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * Helper class for connection.
     */
    private class connectTask extends AsyncTask<String, String, GameClient> {

        /**
         * Method to run the GameClient in a background thread.
         *
         * @param message The parameters of the task.
         * @return null. Return object not used.
         */
        @Override
        protected GameClient doInBackground(String... message) {
            try {
                tcpClient = new GameClient(new GameClient.OnMessageReceived() {

                    @Override
                    public void messageReceived(String mes) {
                        publishProgress(mes);
                    }
                });

                tcpClient.run();

            } catch (Exception e) {
                System.out.println("no connection");
                this.cancel(true);
            }

//            if (tcpClient.connection == false) {
//                System.out.println("no connection");
//                this.cancel(true);
//            }
            return null;
        }

        /**
         * Runs on the UI thread after {@link #publishProgress} is invoked.
         * The specified values are the values passed to {@link #publishProgress}.
         * <p/>
         * This method listens for messages from the server, and acts accordingly
         * upon them.
         *
         * @param values The values indicating progress.
         */
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            list.add(values[0]);
            String input = values[0];

            if (input.startsWith("INITM[")) {
                int pos = input.indexOf(']');
                String action = input.substring(6, pos);

                //start the minigame belonging to the action string
                System.out.println("Incoming action: " + action + "sub " + action.substring(0,6));
                //Only start a minigame if in WaitingActivity
                if (inWaitingActivity()) {
                    if (action.contentEquals("startA")) {
                        startA();
                    } else if (action.contentEquals("startB")) {
                        startB();
                    } else if (action.substring(0,6).contentEquals("startC")) {
                        colorSeq = action.substring(7);
                        startC();
                    }
                } else if (action.contentEquals("minigameDone")) {
                    endMinigame();
                }

            }
        }
    }

}
