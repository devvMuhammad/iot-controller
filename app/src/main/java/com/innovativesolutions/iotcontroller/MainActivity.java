package com.innovativesolutions.iotcontroller;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSIONS_REQUEST_CODE = 2;
    private static final int BLUETOOTH_CONNECT_REQUEST_CODE = 3;
    public static BluetoothSocket socket;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                System.out.println(device);
                if (
                        ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.BLUETOOTH_CONNECT") != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            "android.permission.BLUETOOTH_CONNECT"
                    }, PERMISSIONS_REQUEST_CODE);
                }
                if(device != null){
                    if(device.getName() != null){
                    if(device.getName().equals("HC-05")){
                        System.out.println("apna banda mil gaya");
                    try {
                        UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                        socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                        socket.connect();
                    } catch (IOException e) {
                        e.printStackTrace();}
                }
                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // access the card of each device

        // for fan
        CardView fanCard = (CardView) findViewById(R.id.fanCard);
        fanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FanActivity.class);
                startActivity(intent);
            }
        });
        // for rgb
        CardView rgbCard = (CardView) findViewById(R.id.rgbCard);
        rgbCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RgbActivity.class);
                startActivity(intent);
            }
        });
        // for garage
        CardView garageCard = (CardView) findViewById(R.id.garageCard);
        garageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GarageActivity.class);
                startActivity(intent);
            }
        });
        Button button = (Button) findViewById(R.id.connectButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothEnableRequest();
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.BLUETOOTH_SCAN") != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.BLUETOOTH_CONNECT") != PackageManager.PERMISSION_GRANTED) {
                        // If not, request them
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                android.Manifest.permission.BLUETOOTH,
                                android.Manifest.permission.BLUETOOTH_ADMIN,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                "android.permission.BLUETOOTH_SCAN",
                                "android.permission.BLUETOOTH_CONNECT"
                        }, PERMISSIONS_REQUEST_CODE);
                    }
                    bluetoothAdapter.startDiscovery();
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(receiver, filter);
                }
            }
        });
        CardView bulbCard = (CardView) findViewById(R.id.lightBulb);
        bulbCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LightBulb.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void bluetoothEnableRequest() {
        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
            finishAffinity();
        } else {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_SCAN") != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_CONNECT") != PackageManager.PERMISSION_GRANTED){
                    // If not, request them
                    ActivityCompat.requestPermissions(this, new String[]{
                            android.Manifest.permission.BLUETOOTH,
                            android.Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            "android.permission.BLUETOOTH_SCAN",
                            "android.permission.BLUETOOTH_CONNECT"
                    }, PERMISSIONS_REQUEST_CODE);
                }
            if (!bluetoothAdapter.isEnabled()) {
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
                BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
                    // Bluetooth is not enabled, request to enable it
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // If not, request it
                        ActivityCompat.requestPermissions(this, new String[]{
                                android.Manifest.permission.BLUETOOTH_CONNECT
                        }, BLUETOOTH_CONNECT_REQUEST_CODE);
                    } else {
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }
                }
            } else {
                Toast.makeText(this, "Please grant permissions to use the app", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }
    }
}
