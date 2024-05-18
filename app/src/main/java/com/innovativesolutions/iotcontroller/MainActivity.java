package com.innovativesolutions.iotcontroller;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSIONS_REQUEST_CODE = 2;
    private static final int BLUETOOTH_CONNECT_REQUEST_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bluetoothEnableRequest();
        TabLayout mainTabLayout = findViewById(R.id.mainTabLayout);
        ViewPager2 mainViewPager = findViewById(R.id.mainViewerPage);
        MyFragmentAdapter fragmentMainAdapter = new MyFragmentAdapter(this);
        mainViewPager.setAdapter(fragmentMainAdapter);
        mainTabLayout.addTab(mainTabLayout.newTab().setText("All Devices"));
        mainTabLayout.addTab(mainTabLayout.newTab().setText("Active Devices"));
        mainTabLayout.addTab(mainTabLayout.newTab().setText("Inactive Devices"));
        new TabLayoutMediator(mainTabLayout, mainViewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("All");
                            tab.setIcon(R.drawable.ic_launcher_foreground);
                            break;
                        case 1:
                            tab.setText("Active");
                            tab.setIcon(R.drawable.ic_launcher_foreground);

                            break;
                        case 2:
                            tab.setText("Inactive");
                            tab.setIcon(R.drawable.ic_launcher_foreground);

                            break;
                    }
                }
        ).attach();
        for (int i = 0; i < mainTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mainTabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(R.layout.custom_tab_resource);
            TextView tabTextView = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.customTabView);
            tabTextView.setText(tab.getText());
        }
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
