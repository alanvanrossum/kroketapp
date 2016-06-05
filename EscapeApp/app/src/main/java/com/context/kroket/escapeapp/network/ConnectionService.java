package com.context.kroket.escapeapp.network;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.mainscreens.WaitingActivity;
import com.context.kroket.escapeapp.minigames.A_CodeCrackerCodeview;
import com.context.kroket.escapeapp.minigames.B_TapGame;
import com.context.kroket.escapeapp.minigames.C_ColorSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This service is responsible for registering players by sending information
 * to the server, and for receiving and acting on messaged sent by the server.
 */
public class ConnectionService extends Service {

    private static GameClient tcpClient;
    private static ArrayList<String> list;
    public ArrayList<String> colorParams;
    //Binder given to clients.
    public final IBinder binder = new myBinder();

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
        String playername = "REGISTER[" + intent.getStringExtra("string_name") + "][MOBILE]";

        list = new ArrayList<String>();
        new connectTask().execute("");

        //Wait for the tcpClient to be instantiated
        try {
            Thread.sleep(1000,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Send the name and type of the player to the server.
        tcpClient.sendMessage(playername);

        return START_STICKY;
    }

    /**
     * Check if current activity is waiting activity.
     */
    private boolean inWaitingActivity() {
        String current_activity = ((ActivityManager)this.getApplicationContext())
                .getCurrentActivity().getComponentName().getClassName();
        String waiting_activity = WaitingActivity.class.getName();
        return (current_activity.equals(waiting_activity));
    }

    /**
     * Starts a minigame.
     *
     * @param minigameclass the class of the minigame that should be started.
     */
    private void startMinigame(Class minigameclass) {
        //Broadcast the colorsequence if necessary.
        if (minigameclass.equals(C_ColorSequence.class)) {
            BroadcastThread myThread = new BroadcastThread();
            myThread.start();
        }

        //start the activity belonging to the minigame
        if (minigameclass != null) {
            Intent dialogIntent = new Intent(this, minigameclass);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }
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
                in.putExtra("colorSequence", colorParams);
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
    public void goToWaitingScreen() {
        if (!inWaitingActivity()) {
            Intent dialogIntent = new Intent(this, WaitingActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }
    }

    /**
     * Sends a message to the server that minigame B is solved.
     */
    public void endB() {
        tcpClient.sendMessage("DONE[B]");
    }

    /**
     * Sends a message to the server that minigame A is solved.
     */
    public void endA() {
        tcpClient.sendMessage("DONE[A]");
    }

    /**
     * Class used for the client Binder.
     */
    public class myBinder extends Binder {
        public ConnectionService getService() {
            return ConnectionService.this;
        }
    }

    /**
     * Return the communication channel to the service.
     *
     * @param intent The Intent that was used to bind to this service.
     * @return the binder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * Helper class for connection.
     */
    private class connectTask extends AsyncTask<String, String, GameClient> {

        private static final String TAG = "connectTask";

        /**
         * Method to run the GameClient dataInputStream a background thread.
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
            Log.d(TAG, "Message received: " + input);
            HashMap<String, String> command = CommandParser.parseInput(input);
            parseInput(command, input);
        }

        /**
         * Parses the input received from the server.
         */
        public void parseInput(HashMap<String, String> parsed, String input) {
            String command = parsed.get("command");

            // Start the game
            if (command.equals("START")) {
                // Go to waiting screen
                goToWaitingScreen();
            }
            else if (command.equals("DONE")) {
                // minigame is complete
                goToWaitingScreen();
            }
            else if (command.equals("BEGIN")) {
                if (inWaitingActivity()) {
                    Class minigameclass = getMinigameClassFromInput(CommandParser.parseParams(input));
                    startMinigame(minigameclass);
                }
            }

        }

        /**
         * Returns the class of the minigame that should be started corresponding to the action.
         * @return the class corresponding to the action.
         */
        public Class getMinigameClassFromInput(List<String> params) {

            Log.i(TAG, "getMinigameClassFromInput");

            Class minigameclass = null;

            String game = params.get(0);

            if (game.equals("A")) {
                minigameclass = A_CodeCrackerCodeview.class;
            }
            else if (game.equals("B")) {
                minigameclass = B_TapGame.class;
            }
            else if (game.equals("C")) {
                minigameclass = C_ColorSequence.class;

                colorParams = new ArrayList<String>();

                Log.i(TAG, "params.size = " +  params.size());

                for (String param : params.subList(1, params.size())) {
                    Log.i(TAG, "param : " + param);
                    colorParams.add(param);
                }

                // BEGIN[C][RED][BLUE][GREEN] etc
            }

            return minigameclass;
        }
    }

}
