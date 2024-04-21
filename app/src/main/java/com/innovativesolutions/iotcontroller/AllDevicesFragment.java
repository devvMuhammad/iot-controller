package com.innovativesolutions.iotcontroller;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
public class AllDevicesFragment extends Fragment {
    private TextView txtTab;

    public AllDevicesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_devices_fragment, container, false);
        txtTab = view.findViewById(R.id.txt1);
        return view;
    }
    public void onResume() {
        super.onResume();
        updateViewPager();
    }
    private void updateViewPager(){
        String dummyText = getString(R.string.dummyText1);
        txtTab.setText(dummyText);
    }
}