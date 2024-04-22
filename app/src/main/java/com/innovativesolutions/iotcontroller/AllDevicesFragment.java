package com.innovativesolutions.iotcontroller;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
public class AllDevicesFragment extends Fragment {
    private TextView txtTab;
    private Button addButton;
    int xPosition;
    int initialHorizontalPosition = xPosition;
    int yPosition;
    public AllDevicesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_devices_fragment, container, false);
        txtTab = view.findViewById(R.id.txt1);
        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> popUpMenu());
        int[] addButtonPosition = new int[2];
        xPosition = addButtonPosition[0];
        yPosition = addButtonPosition[1];
        return view;
    }
    private void popUpMenu(){
        PopupMenu popupMenu = new PopupMenu(requireContext(),addButton);
        popupMenu.inflate(R.menu.add_device_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.newDevice){
                addDevice("Device1");
                return true;
            }
            else{
                return false;
            }
        });
        popupMenu.show();
    }
    private void addDevice(String deviceType){
            updateAddButtonPosition();
    }
    private void updateAddButtonPosition(){
        ConstraintLayout layout = (ConstraintLayout) addButton.getParent();
        int layoutWidth = layout.getWidth();
        int layoutHeight = layout.getHeight();
        if(xPosition+addButton.getWidth()*2 >= layoutWidth){
            yPosition+=layoutHeight/2;
            xPosition = initialHorizontalPosition;
        }
        else{
            xPosition+=layoutWidth/2+addButton.getWidth();
        }
//        yPosition+=100;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) addButton.getLayoutParams();
        layoutParams.leftMargin = xPosition;
        layoutParams.topMargin = yPosition;
        addButton.setLayoutParams(layoutParams);
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