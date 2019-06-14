package com.example.trente.myapplication.serversocket;

/**
 * Created by cuongnv on 6/12/19.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.trente.myapplication.R;

public class Server extends Activity {

    private ServerSocket serverSocket;

    Handler updateConversationHandler;

    Thread serverThread = null;

    private TextView text;

    public static final int SERVERPORT = 6000;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_socket_layout);

        text = (TextView) findViewById(R.id.text2);

        updateConversationHandler = new Handler();
        WifiP2pManager manager = (WifiP2pManager) getSystemService(getApplicationContext().WIFI_P2P_SERVICE);

        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerThread implements Runnable {

        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(SERVERPORT);
                InetAddress address = serverSocket.getInetAddress();
                String ip = address.getAddress().toString();
                Log.e("cuong - server address", ip);
                byte[] ipByte = ip.getBytes();
                InetAddress address1 = InetAddress.getByAddress(ipByte);
                if(address.equals(address1)){
                    Log.e("cuong - server address", "true");
                }else {
                    Log.e("cuong - server address", "false");
                }

                Log.e("cuong - server address", serverSocket.getLocalSocketAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {

                try {

                    socket = serverSocket.accept();

                    CommunicationThread commThread = new CommunicationThread(socket);
                    new Thread(commThread).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {

                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    String read = input.readLine();

                    updateConversationHandler.post(new updateUIThread(read));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            text.setText(text.getText().toString()+"Client Says: "+ msg + "\n");
        }
    }
}
