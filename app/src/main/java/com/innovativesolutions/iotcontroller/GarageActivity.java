package com.innovativesolutions.iotcontroller;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GarageActivity extends AppCompatActivity {
    BluetoothSocket bluetoothSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_garage);
        bluetoothSocket = MainActivity.socket;

        SwitchCompat garageSwitch = findViewById(R.id.switchGarage);
        garageSwitch.setChecked(true);
        garageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    System.out.println("Garage is open");
                    sendDataToBluetooth("W");
                } else {
                    System.out.println("Garage is closed");
                    sendDataToBluetooth("X");
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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
            // Handle reconnection or notify the user about the failure
            handleBluetoothError();
        }
    }

    private void handleBluetoothError() {
        // Attempt to reconnect or notify the user
        System.err.println("Failed to send data. Please check the Bluetooth connection.");
    }
}
