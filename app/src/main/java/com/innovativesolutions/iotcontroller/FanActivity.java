package com.innovativesolutions.iotcontroller;

import android.bluetooth.BluetoothSocket;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.OutputStream;

public class FanActivity extends AppCompatActivity {
    BluetoothSocket bluetoothSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fan);
        bluetoothSocket = MainActivity.socket;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        AppCompatSeekBar seekBar = findViewById(R.id.speedSlider);
        TextView currentSpeed = findViewById(R.id.currentSpeed);
        SwitchCompat fanSwitch = findViewById(R.id.fanSwitch);
        seekBar.setEnabled(false);
        // when switch off, the slider is disabled and vice versa
        fanSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            seekBar.setEnabled(isChecked);
            if (isChecked) {
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.light_bulb_12009589);
                seekBar.setProgress(100);
                sendDataToBluetooth("9");
            } else {
                // Set to another drawable or null when the switch is not checked
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.light_bulb_12005728);
                seekBar.setProgress(0);
                sendDataToBluetooth("0");
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentSpeed.setText(String.valueOf(progress));
                char progressChar = (char) (progress + 48);
                System.out.println(progressChar);
                sendDataToBluetooth(String.valueOf(progressChar));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        // slider change listener


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