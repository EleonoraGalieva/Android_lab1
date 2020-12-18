package com.example.converter.premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.converter.R;

public class ConversionActivity extends AppCompatActivity {

    Spinner spinnerFrom, spinnerTo;
    int typeOfSpinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);

        Intent intent = getIntent();
        String action = intent.getAction();

        assert action != null;
        switch (action) {
            case "android.intent.action.Weight": {
                typeOfSpinnerArray = R.array.weight_array;
            }
            break;
            case "android.intent.action.Distance": {
                typeOfSpinnerArray = R.array.dist_array;
            }
            break;
            case "android.intent.action.Currency": {
                typeOfSpinnerArray = R.array.currency_array;
            }
            break;
        }

        ArrayAdapter<CharSequence> adapterFrom = ArrayAdapter.createFromResource(this,
                typeOfSpinnerArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterTo = ArrayAdapter.createFromResource(this,
                typeOfSpinnerArray, android.R.layout.simple_spinner_item);

        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapterFrom);
        spinnerTo.setAdapter(adapterTo);
    }
}
