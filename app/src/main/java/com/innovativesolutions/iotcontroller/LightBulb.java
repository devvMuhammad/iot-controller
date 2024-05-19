package com.innovativesolutions.iotcontroller;

import android.bluetooth.BluetoothSocket;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class LightBulb extends AppCompatActivity {
        BluetoothSocket bluetoothSocket;
        private AtomicInteger currentProgress = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.light_bulb);
        bluetoothSocket = MainActivity.socket;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Switch switch1 = findViewById(R.id.switch1);
        ImageView imgview = findViewById(R.id.imageView2);
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setEnabled(false);
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            seekBar.setEnabled(isChecked);
            if (isChecked) {
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.light_bulb_12009589);
                imgview.setImageDrawable(drawable);
                sendDataToBluetooth("1");
            } else {
                // Set to another drawable or null when the switch is not checked
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.light_bulb_12005728);
                imgview.setImageDrawable(drawable);
                sendDataToBluetooth("0");
            }
        });
            ;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    char progressChar = (char) (progress + 65);
                    System.out.println(progressChar);
                    sendDataToBluetooth(String.valueOf(progressChar));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Do nothing here
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lightBulb), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void sendDataToBluetooth(String data) {
        try {
                OutputStream outputStream = bluetoothSocket.getOutputStream();
                outputStream.write(data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

