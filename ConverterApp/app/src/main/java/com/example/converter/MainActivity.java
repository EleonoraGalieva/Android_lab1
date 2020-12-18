package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.converter.free.ConversionActivity;

public class MainActivity extends AppCompatActivity {

    Button btnDistance;
    Button btnWeight;
    Button btnCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCurrency = findViewById(R.id.btnCurrency);
        btnWeight = findViewById(R.id.btnWeight);
        btnDistance = findViewById(R.id.btnDistance);
        Intent intent = new Intent(this, ConversionActivity.class);
        btnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", Type.distance);
                startActivity(intent);
            }
        });

        btnWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", Type.weight);
                startActivity(intent);
            }
        });

        btnCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", Type.currency);
                startActivity(intent);
            }
        });
    }
}
