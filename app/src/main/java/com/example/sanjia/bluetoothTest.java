package com.example.sanjia;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class bluetoothTest extends AppCompatActivity {

    ConnectedThread mConnectedThread;
    Button sig1, sig2, sig3, sig4, sig5;
    SeekBar brightness;
    static String address = null;
    String EXTRA_ADDRESS = "device_address";
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static ParcelUuid[] myUUID;
    String x;
    InputStream inputstream = null;
    //= UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static boolean hasfUCKED = false;

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread

    {

        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(bluetoothTest.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        public Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            Log.d("motherfucker", "bonny");
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    myUUID = dispositivo.getUuids();
                    Log.d("dadad", myUUID[0].getUuid().toString());
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID[0].getUuid());//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection

                }


            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
//            try {
//                inputstream=btSocket.getInputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if(!(inputstream==null)){
//            distance.setText("distance:"+inputstream.toString());}
            Log.d("yeah we have set text", "onPostExecute: ");
            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }

    }

    private Set pairedDevices;
    Button btnPaired;
    ListView devicelist;
    TextView distance;


    public void toMain(View v) {

        finish();
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void Disconnect() {
        if (btSocket != null) //If the btSocket is busy
        {
            try {
                Log.d("Bonny2", "sig3: ");
                btSocket.getOutputStream().write("I".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }

    }

    private void sig2() {
        if (btSocket != null) {
            try {
                Log.d("Bonny2", "sig2: ");
                btSocket.getOutputStream().write("R".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void sig4() {
        if (btSocket != null) {
            try {
                Log.d("Bonny2", "sig2: ");
                btSocket.getOutputStream().write("J".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void sig5() {
        if (btSocket != null) {
            try {
                Log.d("Bonny2", "sig2: ");
                btSocket.getOutputStream().write("C".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void sig1() {
        if (btSocket != null) {
            try {
                if (btSocket.isConnected()) {
                    Log.d("mama", "sig1: ");
                }
                Log.d("Bonny2", "sig1: ");
                btSocket.getOutputStream().write("B".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_test);
        btnPaired = (Button) findViewById(R.id.pairdevices);
        devicelist = (ListView) findViewById(R.id.listview);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        PlzConnectBT();
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toMain(v);
            }
        });
        Intent newint = getIntent();
        address = newint.getStringExtra(EXTRA_ADDRESS);

        distance = (TextView) findViewById(R.id.ID);
        sig1 = (Button) findViewById(R.id.signal1);
        sig2 = (Button) findViewById(R.id.signal2);
        sig3 = (Button) findViewById(R.id.signal3);
        sig4 = (Button) findViewById(R.id.signal4);
        sig5 = (Button) findViewById(R.id.signal5);
        brightness = (SeekBar) findViewById(R.id.seekBar2);
        brightness.setProgress(50);
        sig1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sig1();      //method to turn on
            }
        });

        sig2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sig2();   //method to turn off
            }
        });

        sig3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect(); //close connection
            }
        });
        sig4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sig4();      //method to turn on
            }
        });
        sig5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sig5();      //method to turn on
            }
        });
//        if (hasfUCKED == true) {
//            new ConnectBT().execute();
//        }
        //Thread a = new ConnectedThread(btSocket);
        //a.run();
//        while (true){
//            a;
//        }
        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    TextView lumn = (TextView) findViewById(R.id.lumn);
                    lumn.setText(String.valueOf(progress));

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });
    }
//    }  Thread t= new Thread(){
//            @Override
//            public void run(){
//                try{
//                    Log.d("n", "run: ");
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try{
//                                    if (!(btSocket==null)){
//                                     x =btSocket.getInputStream().toString();
//                                    distance.setText(x);}}
//                                catch (IOException e) {}
//                            }
//                        });
//                }
//                catch(InterruptedException e){}
//            }
//        };
//        t.start();

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            address = info.substring(info.length() - 17);
            // Make an intent to start next activity.
            //Intent i = new Int    ent(bluetoothTest.this,bluetoothTest.class);
            //Change the activity.
            // i.putExtra(EXTRA_ADDRESS, address);
            hasfUCKED = true;
            Log.d("hello", address);
            //startActivity(i);
            if (hasfUCKED == true) {
                new ConnectBT().execute();
                mConnectedThread = new ConnectedThread(btSocket);
                mConnectedThread.start();
//                final Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        byte[] buffer = new byte[1024];
//                        int bytes;
//
//                        while (true) {
//                            try {
//
//                                if (btSocket == null) {
//                                }
//                                else {
//                                    bytes = btSocket.getInputStream().read(buffer);
//
//                                    String inMessage = new String(buffer, 0, bytes);
//                                    Log.d("hello", "Incomming Message is: " + inMessage);
//                                }
//
//                            } catch (IOException e) {
//                                break;
//                            }
//
//                        }
//                    }
//
//                    //a.run();
//                });
//                thread.run();
            }

        }
    };

    public void PlzConnectBT() {
        if (myBluetooth == null) {
            //Show a message. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            //finish apk
            //finish();
        } else {
            if (myBluetooth.isEnabled()) {
            } else {
                //Ask to the user turn the bluetooth on
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }
        }
    }

    private void pairedDevicesList() {
        Set<BluetoothDevice> pairedDevices;
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }


    public void startThread() {
        ConnectedThread ct = new ConnectedThread(btSocket);
        new Thread(ct).start();
    }
}
class ConnectedThread extends Thread  {
    bluetoothTest bt;
    private final InputStream mInStream;
    private final OutputStream mOutStream;


    public ConnectedThread(BluetoothSocket socket) {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mInStream = tmpIn;
        mOutStream = tmpOut;
    }

    public void run() {

        byte[] buffer = new byte[1024];
        int bytes;

        while (true){
            try {
                if (bt.btSocket==null){}
                else{
                    bytes = mInStream.read(buffer);

                String inMessage = new String(buffer, 0, bytes);
                Log.d("hello", "Incomming Message is: " + inMessage);}

            }catch (IOException e){
                break;
            }
        }

    }

    public void cancel() {
        try {
            bt.btSocket.close();
        } catch (IOException e) {
        }
    }
}