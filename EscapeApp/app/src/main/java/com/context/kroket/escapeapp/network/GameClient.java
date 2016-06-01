package com.context.kroket.escapeapp.network;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.SocketTimeoutException;

import android.os.AsyncTask;

import android.util.Log;

/**
 * Client class responsible for incoming and outgoing messages to the server.
 */
public class GameClient {
    public static final String SERVERIP = "192.168.1.208";

    public static final int SERVERPORT = 1234; // The port we will be listening.

    private String serverMessage;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    public boolean connection = false;

    PrintWriter dataOutputStream;
    BufferedReader dataInputStream;
    Socket serverSocket;

    private static final String TAG = "GameClient";


    /**
     * Constructor for the GameClient.
     *
     * @param listener the listerener we want to use.
     */
    public GameClient(OnMessageReceived listener) {
        messageListener = listener;
    }

    /**
     * Send message to the server.
     *
     * @param message the message to be send.
     */
    public void sendMessage(String message) {

        if (!connection)
            return;

        // Wait for the PrintWriter to be initialized.
        while (dataOutputStream == null || dataOutputStream.checkError()) {
        }
        if (dataOutputStream != null && !dataOutputStream.checkError()) {
            dataOutputStream.println(message);
            dataOutputStream.flush();
        }
    }

    /**
     * Method to change the run-ability of the client.
     */
    public void stopClient() {
        running = false;
    }


    public void run() {
        running = true;

        createSocket();
    }

    /*
     * Creates the serversocket with the IP adress and port.
     */
    private void createSocket() {


        try {

            connection = false;

            InetAddress serverAddress = InetAddress.getByName(SERVERIP);
            SocketAddress socketAddress = new InetSocketAddress(serverAddress, SERVERPORT);
            serverSocket = new Socket();
            serverSocket.setReuseAddress(true);
            serverSocket.connect(socketAddress, 2000);

            connection = true;


            createStreams();

            return;
        } catch (UnknownHostException e) {
            Log.e(TAG, "Unknown host: " + e);
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "Timeout occured during connect to " + SERVERIP + ":" + SERVERPORT);
        } catch (ConnectException e) {
            Log.e(TAG, "Failed to connect to " + SERVERIP + ":" + SERVERPORT);
        } catch (SocketException e) {
            Log.e(TAG, "SocketException");
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }
    }


    /**
     * Closes the server socket.
     */
    private void closeSocket() {

        if (serverSocket == null)
            return;

        if (serverSocket.isClosed())
            return;

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverSocket = null;
    }

    /**
     * Creates the input and output streams.
     */
    private void createStreams() {
        try {
            dataOutputStream = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream())));
            dataInputStream = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            // While loop that lasts until 'stopclient is called'.
            while (running) {
                serverMessage = dataInputStream.readLine();

                // A message was received.
                if (serverMessage != null) {
                    messageListener.messageReceived(serverMessage);
                }
                serverMessage = null;
            }

        } catch (Exception e) {

            Log.e(TAG, "IOException");

            e.printStackTrace();
        } finally {
            closeSocket();
        }
    }

    /**
     * Interface for receiving messages.
     */
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
