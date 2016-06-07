package com.context.kroket.escapeapp.network;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.context.kroket.escapeapp.R;
import com.context.kroket.escapeapp.application.ActivityManager;
import com.context.kroket.escapeapp.mainscreens.MainActivity;
import com.context.kroket.escapeapp.mainscreens.WaitingActivity;
import com.context.kroket.escapeapp.minigames.A_CodeCrackerCodeview;
import com.context.kroket.escapeapp.minigames.B_TapGame;
import com.context.kroket.escapeapp.minigames.C_ColorSequence;
import com.context.kroket.escapeapp.minigames.D_Gyroscope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.List;
import android.util.Log;
import android.widget.TextView;

/**
 * This service is responsible for registering players by sending information
 * to the server, and for receiving and acting on messaged sent by the server.
 */
public class ConnectionService extends Service {

    private static GameClient tcpClient;
    private static ArrayList<String> list;
    public ArrayList<String> colorParams;
    public ArrayList<String> buttonParams;
    public String BTActionString;
    public String BTExtraString;
    public ArrayList<String> BTExtraArray;
    //Binder given to clients.
    public final IBinder binder = new myBinder();
    private static final String TAG = "ConnectionService";
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
        String registerString = "REGISTER[" + intent.getStringExtra("string_name") + "][MOBILE]";



        list = new ArrayList<String>();
        new connectTask().execute(intent.getStringExtra("remote_address"));

        //Wait for the tcpClient to be instantiated
        try {
            Thread.sleep(1000,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (tcpClient == null)
        {
//            setLabelText("Connection failed, yo.");
        }
        else {
            tcpClient.sendMessage(registerString);
        }

        updateMain();

        return START_STICKY;
    }

    /**
     * Check if current activity is waiting activity.
     */
    private boolean inWaitingActivity() {
        String current_activity = ((ActivityManager)this.getApplicationContext())
                .getCurrentActivity().getComponentName().getClassName();
        String waiting_activity = WaitingActivity.class.getName();
        String game_waiting_activity = D_Gyroscope.class.getName();
        return (current_activity.equals(waiting_activity) || current_activity.equals(game_waiting_activity));
    }

    /**
     * Starts a minigame.
     *
     * @param minigameclass the class of the minigame that should be started.
     */
    private void startMinigame(Class minigameclass) {
        //Broadcast the colorsequence if necessary.
        if (minigameclass.equals(C_ColorSequence.class)) {
            BTActionString = "colorBroadcast";
            BTExtraString = "colorSequence";
            BTExtraArray = colorParams;
            BroadcastThread myThreadC = new BroadcastThread();
            myThreadC.start();
        }
        if (minigameclass.equals(B_TapGame.class)) {
            BTActionString = "buttonBroadcast";
            BTExtraString = "buttonSequence";
            BTExtraArray = buttonParams;
            BroadcastThread myThreadB = new BroadcastThread();
            myThreadB.start();
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
//                in.setAction("colorBroadcast");
//                in.putExtra("colorSequence", colorParams);
                in.setAction(BTActionString);
                in.putExtra(BTExtraString, BTExtraArray);
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
    public void startB() {
        tcpClient.sendMessage("INITVR[startB]");
    }

    public void setLabelText(String message) {
        Activity current = ((ActivityManager)this.getApplicationContext())
                .getCurrentActivity();

        if (current instanceof MainActivity) {
            TextView connectMessage = (TextView) current.findViewById(R.id.connectionMessage);
            connectMessage.setText(message);
            ((MainActivity) current).update();
        }
    }

    public void updateMain() {
        Activity current = ((ActivityManager)this.getApplicationContext())
                .getCurrentActivity();

        if (current instanceof MainActivity) {
            ((MainActivity) current).update();
        }
    }

    /**
     * Sends a message to the server if bonus time should be added.
     */
    public void bonusD() {
        //System.out.println("bonus message sent");
        tcpClient.sendMessage("DONE[D]");
    }

    /**
     * Sends a message to the server that minigame B is solved and needs a verification.
     */
    public void verifyB() {
        tcpClient.sendMessage("INITVR[verifyB]");
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



        /**
         * Method to run the GameClient dataInputStream a background thread.
         *
         * @param message The parameters of the task.
         * @return null. Return object not used.
         */
        @Override
        protected GameClient doInBackground(String... message) {
            try {

                Log.i(TAG, "Creating GameClient...");

                tcpClient = new GameClient(message[0], new GameClient.OnMessageReceived() {

                    @Override
                    public void messageReceived(String mes) {
                        publishProgress(mes);
                    }
                }
                );

                tcpClient.run();



            } catch (Exception e) {
                this.cancel(true);
            }

            return tcpClient;
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

            updateMain();

            if (GameClient.isConnected())
                setLabelText("Connected.");
            else
                setLabelText("Connection failed...");

            updateMain();


            HashMap<String, String> command = CommandParser.parseInput(input);
            parseInput(command, input);
        }

        /**
         * Parses the input received from the server.
         *
         * @param command the hashmap which contains the parsed input.
         */
        public void parseInput(HashMap<String, String> command, String input) {

            //Command is for the mobile client
            if (command.get("command").equals("INITM")) {

                String action = command.get("param_0");

                //Only start a minigame if dataInputStream WaitingActivity.
                if (inWaitingActivity()) {
                    Class minigameclass = getMinigameClassFromInput(action, CommandParser.parseParams(input));
                    startMinigame(minigameclass);
//                } else if (action.contentEquals("minigameDone")) {
//                    endMinigame();
                } else if (action.contentEquals("doneC")) {
                    endMinigame();
                } else if (action.contentEquals("doneB")) {
                    B_TapGame.done = true;
                    endMinigame();
                }
                //Minigame B can be restarted. so it can be activated outside of the waiting activity.
                else if (action.contentEquals("startB")) {
                    buttonParams = CommandParser.parseParams(input);
                    startMinigame(B_TapGame.class);
                }
            }
        }

        /**
         * Returns the class of the minigame that should be started corresponding to the action.
         * @param action the received action.
         * @return the class corresponding to the action.
         */
        public Class getMinigameClassFromInput(String action, ArrayList<String> params) {
            Class minigameclass = null;
            if (action.contentEquals("startA")) {
                minigameclass = A_CodeCrackerCodeview.class;
            } else if (action.contentEquals("startB")) {
                minigameclass = B_TapGame.class;
                buttonParams = params;
            } else if (action.contentEquals("startC")) {
                minigameclass = C_ColorSequence.class;
                //Set the command for the colorSequence, to be broadcasted later on.
                colorParams = params;
            }
            return minigameclass;
        }
    }

}
