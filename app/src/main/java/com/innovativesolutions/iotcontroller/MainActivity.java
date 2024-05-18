package com.innovativesolutions.iotcontroller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class MainActivity extends AppCompatActivity {
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
    }
}