package com.innovativesolutions.iotcontroller;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
//1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
                            tab.setText("All Devices");
                            break;
                        case 1:
                            tab.setText("Active Devices");
                            break;
                        case 2:
                            tab.setText("Inactive Devices");
                            break;
                    }
                }
        ).attach();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}