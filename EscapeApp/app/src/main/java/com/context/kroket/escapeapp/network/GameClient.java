package com.context.kroket.escapeapp.network;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client class responsible for incoming and outgoing messages
 * to the server.
 */
public class GameClient {
    public static final String SERVERIP = "145.94.142.195"; //"145.94.177.139"; //"192.168.178.19"; //the ip adress of the server  Alan: "145.94.178.99."
    public static final int SERVERPORT = 1234; //the port we will be listening
    private String serverMessage;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    public boolean connection = false;

    PrintWriter dataOutputStream;
    BufferedReader dataInputStream;
    Socket serverSocket;

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
        while(dataOutputStream == null || dataOutputStream.checkError()) {}
        if(dataOutputStream != null && !dataOutputStream.checkError()){
            dataOutputStream.println(message);
            dataOutputStream.flush();
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

        createSocket();
        createStreams();
    }

    /**
     * Creates the serversocket with the IP adress and port.
     */
    private void createSocket() {
        try {
            InetAddress serverAddress = InetAddress.getByName(SERVERIP);
            serverSocket = new Socket(serverAddress, SERVERPORT);
            connection = true;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in making serverSocket");
            this.stopClient();
            return;
        }
    }

    /**
     * Closes the server socket.
     */
    private void closeSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the input and output streams.
     */
    private void createStreams() {
        try {
            dataOutputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream())));
            dataInputStream = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            //while loop that lasts until 'stopclient is called'
            while(running){
                serverMessage = dataInputStream.readLine();

                //A message was received
                if(serverMessage != null) {
                    messageListener.messageReceived(serverMessage);
                }
                serverMessage = null;
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeSocket();
        }
    }

    /**
     * Interface for receiving messages
     */
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
