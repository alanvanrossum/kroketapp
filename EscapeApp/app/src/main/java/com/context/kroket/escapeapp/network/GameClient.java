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

import android.util.Log;

/**
 * Client class responsible for incoming and outgoing messages to the server.
 */
public class GameClient {
<<<<<<< HEAD
    public static final String SERVERIP = "192.168.1.50"; //"145.94.177.139"; //"192.168.178.19"; //the ip adress of the server  Alan: "145.94.178.99."
    public static final int SERVERPORT = 1234; //The port we will be listening.
=======

    /** The remote host we will be connecting to. */
    public static final String SERVERIP = "80.112.155.241";

    private String remoteHost;

    /** The remote port we will be connecting to. */
    public static final int SERVERPORT = 1234;

>>>>>>> 6ace7ba6581152d9f19f9f81ac7bac5d8bf73fd0
    private String serverMessage;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    private static boolean connected = false;
    private static boolean connecting = false;

    PrintWriter dataOutputStream;
    BufferedReader dataInputStream;
    Socket serverSocket;


    private static final String TAG = "GameClient";


    /**
     * Constructor for the GameClient.
     *
     * @param listener the listerener we want to use.
     */
    public GameClient(String remoteHost, OnMessageReceived listener) {
        messageListener = listener;
        connected = false;


        this.remoteHost = (remoteHost.isEmpty() ? SERVERIP : remoteHost);
    }

    /**
     * Send message to the server.
     *
     * @param message the message to be send.
     */
    public void sendMessage(String message) {

        if (!connected)
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
        connecting = true;
        connected = false;

        createSocket();
    }

    public static boolean isConnecting() {
        return connecting;
    }

    public static boolean isConnected() {
        return connected;
    }

    /*
     * Creates the serversocket with the IP adress and port.
     */
    private void createSocket() {


        try {

            connecting = true;
            connected = false;

            InetAddress serverAddress = InetAddress.getByName(this.remoteHost);
            SocketAddress socketAddress = new InetSocketAddress(serverAddress, SERVERPORT);
            serverSocket = new Socket();
            serverSocket.setReuseAddress(true);
            serverSocket.connect(socketAddress, 2000);

            connected = true;

            createStreams();

            return;
        } catch (UnknownHostException e) {
            Log.e(TAG, "Unknown host: " + e);
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "Timeout occured during connect to " + remoteHost + ":" + SERVERPORT);
        } catch (ConnectException e) {
            Log.e(TAG, "Failed to connect to " + remoteHost + ":" + SERVERPORT);
        } catch (SocketException e) {
            Log.e(TAG, "SocketException");
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }
        finally {
            connected = false;
            connecting = false;
        }
    }


    /**
     * Closes the server socket.
     */
    private void closeSocket() {

        connected = false;
        connecting = false;

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
