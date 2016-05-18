package com.context.kroket.escapeapp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client class responsible for incoming and outgoing messages
 * to the server.
 */
public class GameClient {
    public static final String SERVERIP = "145.94.140.247"; //the ip adress of the server  Alan: "145.94.178.99."
    public static final int SERVERPORT = 1234; //the port we will be listening
    private String serverMessage;
    private OnMessageReceived messageListener = null;
    private boolean running = false;

    PrintWriter out;
    BufferedReader in;

    /**
     * Constructor for the GameClient
     *
     * @param listener the listerener we want to use
     */
    public GameClient(OnMessageReceived listener){
        messageListener = listener;
    }

    /**
     * Send message to the server
     *
     * @param message the message to be send
     */
    public void sendMessage(String message){
        //wait for the PrintWriter to be initialized
        while(out == null || out.checkError()) {}
        if(out != null && !out.checkError()){
            out.println(message);
            out.flush();
        }
    }

    /**
     * Method to change the run-ability of the client
     */
    public void stopClient(){
        running = false;
    }

    /**
     * Initialize the PrintWriter and BufferedReader and read the incoming messages
     */
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

                    if(serverMessage!=null) {
                        //A message was received
                        messageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Interface for receiving messages
     */
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
