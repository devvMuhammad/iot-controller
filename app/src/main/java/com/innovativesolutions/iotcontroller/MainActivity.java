package com.innovativesolutions.iotcontroller;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

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
        for(int i = 0;i<mainTabLayout.getTabCount();i++) {
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
}