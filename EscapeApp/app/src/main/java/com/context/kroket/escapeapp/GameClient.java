package com.context.kroket.escapeapp;

import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Swift on 10-5-2016.
 */
public class GameClient {
    public static final String SERVERIP = "192.168.178.19"; //"145.94.197.238"; //"145.94.210.124"; //"145.94.178.99"; //"145.94.213.233"; //the ip adress of the server  Alan: "145.94.178.99."
    public static final int SERVERPORT = 1234; //the port we will be listening //
    private String serverMessage;
    private OnMessageReceived messageListener = null;
    private boolean running = false;

    PrintWriter out;
    BufferedReader in;

    public GameClient(OnMessageReceived listener){
        messageListener = listener;
    }
    public void sendMessage(String message){
        while(out == null || out.checkError()) {}
        if(out != null && !out.checkError()){
            out.println(message);
            out.flush();
        }
    }

    public void stopClient(){
        running = false;
    }

    public void run(){
        running = true;
        try{
            InetAddress serverAddress = InetAddress.getByName(SERVERIP);
            //connecting
            Socket serverSocket = new Socket(serverAddress,SERVERPORT);

            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream())));
                in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

                //while loop that lasts until 'stopclient is called'
                while(running){
                    serverMessage = in.readLine();
                    if(serverMessage!=null) {//&&mmmesgaleisnter!=null
                        //message received, do something!
                        messageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally{
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
