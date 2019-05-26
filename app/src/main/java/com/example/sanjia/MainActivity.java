package com.example.sanjia;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.Set;

import static android.media.ToneGenerator.MAX_VOLUME;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private static TextView text_view;
    private static SeekBar seek_bar;
    private int progressValue;
    MediaPlayer mySong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seek_bar=(SeekBar)findViewById(R.id.seekBar);
        text_view=(TextView)findViewById(R.id.textView);
        progressValue=seek_bar.getProgress();
        mySong = MediaPlayer.create(MainActivity.this,R.raw.aaa);
        if(mySong!=null) {
            mySong.setLooping(true);
            seek_bar.setProgress(50);
            seekbar();
        }
    }

    public void toThemes(View v){
        Intent BT = new Intent(getBaseContext(),bluetoothTest.class);
        startActivity(BT);
    }
//    private void pairedDevicesList()
//    {
//        pairedDevices = myBluetooth.getBondedDevices();
//        ArrayList list = new ArrayList();
//
//        if (pairedDevices.size()>0)
//        {
//            for(BluetoothDevice bt : pairedDevices)
//            {
//                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
//            }
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
//        }
//
//        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
//        devicelist.setAdapter(adapter);
//        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked
//
//    }

    public void playMusic(View v){
        if (mySong.isPlaying()==false) {
            mySong.start();
        }
        else{
            mySong.pause();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mySong.release();
    }

    public void Raining(View v){
        //mySong = MediaPlayer.create(MainActivity.this,R.raw.raining);
    }
    public void Sunny(View v){
        mySong = null;
    }

    public void seekbar(){

        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        text_view.setText(progressValue+"%");
                        Toast.makeText(MainActivity.this,"adjusting volume", Toast.LENGTH_LONG).show();
                        float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                        mySong.setVolume(volume, volume);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        ;

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        text_view.setText(progressValue+"%");


                    }
                }
        );
    }

}
