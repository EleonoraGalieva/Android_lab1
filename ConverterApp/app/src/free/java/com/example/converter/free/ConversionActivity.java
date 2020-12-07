package com.example.converter.free;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.converter.R;
import com.example.converter.Type;

public class ConversionActivity extends AppCompatActivity {

    Spinner spinnerFrom, spinnerTo;
    int typeOfSpinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);

        Bundle extras = getIntent().getExtras();
        Type myEnum = (Type) extras.getSerializable("type");

        switch (myEnum) {
            case currency:
                typeOfSpinnerArray = R.array.currency_array;
                break;
            case weight:
                typeOfSpinnerArray = R.array.weight_array;
                break;
            case distance:
                typeOfSpinnerArray = R.array.dist_array;
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
