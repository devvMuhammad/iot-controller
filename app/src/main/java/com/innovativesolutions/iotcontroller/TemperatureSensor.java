package com.innovativesolutions.iotcontroller;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TemperatureSensor extends AppCompatActivity {
    BluetoothSocket bluetoothSocket;
    private TextView textView;  // Changed to private and moved to onCreate initialization
    private Handler handler;
    private Thread bluetoothThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature_sensor);

        // Initialize views after setContentView
        textView = findViewById(R.id.Temperature);
        bluetoothSocket = MainActivity.socket;
        handler = new Handler();  // Initialize the handler
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SwitchCompat sensorSwitch = findViewById(R.id.switchTemperatureSensor);
        sensorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    System.out.println("Hello");
                    sendDataToBluetooth("Z");
                    startListeningForData();
                } else {
                    textView.setText(" ");
                    stopListeningForData();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sensorActivity), (v, insets) -> {
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
            e.printStackTrace();
        }
    }

    private void startListeningForData() {
        bluetoothThread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream;
                try {
                    inputStream = bluetoothSocket.getInputStream();
                    byte[] buffer = new byte[1024];
                    int bytes;
                    while (!Thread.currentThread().isInterrupted()) {
                        bytes = inputStream.read(buffer);
                        if (bytes > 0) {
                            final String receivedMessage = new String(buffer, 0, bytes);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(receivedMessage);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        bluetoothThread.start();
    }

    private void stopListeningForData() {
        if (bluetoothThread != null && bluetoothThread.isAlive()) {
            bluetoothThread.interrupt();
            bluetoothThread = null;
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
