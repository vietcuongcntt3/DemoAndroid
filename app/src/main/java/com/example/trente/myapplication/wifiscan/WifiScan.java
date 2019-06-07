package com.example.trente.myapplication.wifiscan;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trente.myapplication.R;
import com.example.trente.myapplication.bluetooth.ChatService;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by cuongnv on 6/6/19.
 */

public class WifiScan extends AppCompatActivity {
    public Button btnOnOff, btnDiscovery, btnSend;
    public TextView txtConnectStatus, txtChatMessage;
    public EditText edtMessage;
    public ListView listView;

    public WifiManager wifiManager;

    private final IntentFilter intentFilter = new IntentFilter();
    public WifiP2pManager.Channel channel;
    public WifiP2pManager manager;
    public BroadcastReceiver receiver;

    private List<WifiP2pDevice> peers = new ArrayList<>();
    public String[] deviceNameArray;
    public WifiP2pDevice[] deviceArray;

    public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            if (!peerList.getDeviceList().equals(peers)) {
                peers.clear();
                peers.addAll(peerList.getDeviceList());
                deviceNameArray = new String[peers.size()];
                deviceArray = new WifiP2pDevice[peers.size()];
                for(int index = 0;index <peers.size();index ++){
                    deviceArray[index] = peers.get(index);
                    deviceNameArray[index] = peers.get(index).deviceName;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.device_name, deviceNameArray);
                listView.setAdapter(adapter);
                // If an AdapterView is backed by this data, notify it
                // of the change. For instance, if you have a ListView of
                // available peers, trigger an update.
//                ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();

                // Perform any other updates needed based on the new list of
                // peers connected to the Wi-Fi P2P network.
            }

            if (peers.size() == 0) {
                Log.e("cuong", "No devices found");
                return;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnOnOff = (Button) findViewById(R.id.onOff);
        btnDiscovery = (Button) findViewById(R.id.discover);
        btnSend = (Button) findViewById(R.id.btn_send);
        txtChatMessage = (TextView) findViewById(R.id.readMsg);
        txtConnectStatus = (TextView) findViewById(R.id.status_text);
        edtMessage = (EditText) findViewById(R.id.writeMsg);

        listView = (ListView)findViewById(R.id.peerListView);

        wifiManager = (WifiManager)
                getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(false);
                    btnOnOff.setText("ON");
                }else {
                    wifiManager.setWifiEnabled(true);
                    btnOnOff.setText("OFF");
                }
            }
        });



        btnDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("cuong", "scan");
                scanWifi();
            }
        });

        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
    }

    public void scanWifi(){
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank. Code for peer discovery goes in the
                // onReceive method, detailed below.
//                txtConnectStatus.setText("Discovery started");
                Log.e("cuong", "success");
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                Log.e("cuong", "fail");
                txtConnectStatus.setText("Discovery fail");
            }
        });
    }


    /** register the BroadcastReceiver with the intent values to be matched */
    @Override
    public void onResume() {
        super.onResume();

        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


}


