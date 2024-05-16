package com.innovativesolutions.iotcontroller;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LightBulb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.light_bulb);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        Switch switch1 = findViewById(R.id.switch1);
        ImageView imgview = findViewById(R.id.imageView2);
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.light_bulb_12009589);
                imgview.setImageDrawable(drawable);
            } else {
                // Set to another drawable or null when the switch is not checked
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.light_bulb_12005728);
                imgview.setImageDrawable(drawable);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lightBulb), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
