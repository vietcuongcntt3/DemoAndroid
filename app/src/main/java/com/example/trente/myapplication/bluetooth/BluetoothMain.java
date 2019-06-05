package com.example.trente.myapplication.bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.App;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.base.BaseGetRequest;
import com.example.trente.myapplication.base.OnResponseListener;
import com.example.trente.myapplication.model.Utils;
import com.example.trente.myapplication.user.UserModel;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by cuongnv on 5/30/19.
 */

public class BluetoothMain extends AppCompatActivity {
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private BluetoothAdapter BTAdapter;
    public static int REQUEST_BLUETOOTH = 1;
    public static String DEVICE_ADDRESS = "deviceAddress";

    public ListView listView;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;
    private ChatService chatService = null;

    private final BroadcastReceiver discoveryFinishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            }
        }
    };

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
//                    switch (msg.arg1) {
//                        case ChatService.STATE_CONNECTED:
//                            setStatus(getString(R.string.title_connected_to,
//                                    connectedDeviceName));
////                            chatArrayAdapter.clear();
//                            break;
//                        case ChatService.STATE_CONNECTING:
//                            setStatus(R.string.title_connecting);
//                            break;
//                        case ChatService.STATE_LISTEN:
//                        case ChatService.STATE_NONE:
//                            setStatus(R.string.title_not_connected);
//                            break;
//                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;

//                    writeMessage = new String(writeBuf);
//                    chatArrayAdapter.add(new ChatMessage(false, writeMessage));
//                    etMain.setText("");

                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;

//                    readMessage = new String(readBuf, 0, msg.arg1);
//                    chatArrayAdapter.add(new ChatMessage(true, readMessage));

                    break;
                case MESSAGE_DEVICE_NAME:


//                    connectedDeviceName = msg.getData().getString(DEVICE_NAME);
//                    Toast.makeText(getApplicationContext(),
//                            "Connected to " + connectedDeviceName,
//                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            Toast.makeText(getApplicationContext(),
                    msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_deviceitem_list);
        listView = (ListView)findViewById(R.id.listblue);
        pairedDevicesArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.device_name);
        listView.setAdapter(pairedDevicesArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BTAdapter.cancelDiscovery();

                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);
                connectDevice(address, true);
//                Intent intent = new Intent();
//                intent.putExtra(DEVICE_ADDRESS, address);
//
//                setResult(Activity.RESULT_OK, intent);
//                finish();
            }
        });
        Button bt = (Button) findViewById(R.id.scan);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processScan();
            }
        });

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        BroadcastReceiver bReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent
                            .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        pairedDevicesArrayAdapter.add(device.getName() + "\n"
                                + device.getAddress());
                        pairedDevicesArrayAdapter.notifyDataSetChanged();
                    }
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                        .equals(action)) {
                    setProgressBarIndeterminateVisibility(false);

                }
            }
        };

        registerReceiver(bReciever, filter);

        BTAdapter = BluetoothAdapter.getDefaultAdapter();

        // Phone does not support Bluetooth so let the user know and exit.
        if (BTAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else {

            if (chatService == null)
                setupChat();

            if (!BTAdapter.isEnabled()) {
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT, REQUEST_BLUETOOTH);
            }

            Set<BluetoothDevice> pairedDevices = BTAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    Log.e("cuong", device.getAddress() + "        " + device.getName());
                    pairedDevicesArrayAdapter.add(device.getName() + "\n"+ device.getAddress());
                }
                pairedDevicesArrayAdapter.notifyDataSetChanged();
            }
        }

    }
    @Override
    public synchronized void onResume() {
        super.onResume();

        if (chatService != null) {
            if (chatService.getState() == ChatService.STATE_NONE) {
                chatService.start();
            }
        }
    }

    private void setupChat() {

        chatService = new ChatService(this, handler);
//        outStringBuffer = new StringBuffer("");
    }




    public void processScan(){
        setProgressBarIndeterminateVisibility(true);
        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
        if (BTAdapter.isDiscovering()) {
            BTAdapter.cancelDiscovery();
        }
        BTAdapter.startDiscovery();

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(discoveryFinishReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(discoveryFinishReceiver, filter);


        Log.e("cuong123234 ", "actionfrfwe" + BTAdapter.isDiscovering());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (chatService != null)
            chatService.stop();
//        unregisterReceiver(mReceiver);
    }

    private void connectDevice(String address, boolean secure) {
        BluetoothDevice device = this.BTAdapter.getRemoteDevice(address);

        chatService.connect(device, secure);
    }
}






