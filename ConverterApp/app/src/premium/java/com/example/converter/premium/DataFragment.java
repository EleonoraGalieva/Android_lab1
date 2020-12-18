package com.example.converter.premium;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.converter.ConversionViewModel;
import com.example.converter.R;

import java.util.Objects;


public class DataFragment extends Fragment {

    private ConversionViewModel conversionViewModel;
    private TextView textFrom, textTo;
    private Spinner spinnerFrom, spinnerTo;
    private Button btnCopyFrom, btnCopyTo, btnSwap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversionViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ConversionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data, container, false);

        textFrom = view.findViewById(R.id.textFrom);
        textTo = view.findViewById(R.id.textTo);
        spinnerTo = view.findViewById(R.id.spinnerTo);
        spinnerFrom = view.findViewById(R.id.spinnerFrom);
        btnCopyFrom = view.findViewById(R.id.btnCopyFrom);
        btnCopyTo = view.findViewById(R.id.btnCopyTo);
        btnSwap = view.findViewById(R.id.btnSwap);

        btnCopyFrom.setOnClickListener(item -> conversionViewModel.copyFrom((ClipboardManager) Objects.requireNonNull(getContext()).getSystemService(Context.CLIPBOARD_SERVICE)));
        btnCopyTo.setOnClickListener(item -> conversionViewModel.copyTo((ClipboardManager) Objects.requireNonNull(getContext()).getSystemService(Context.CLIPBOARD_SERVICE)));
        btnSwap.setOnClickListener(v -> {
            conversionViewModel.swap();
            String temp = spinnerFrom.getSelectedItem().toString();
            spinnerFrom.setSelection(((ArrayAdapter) spinnerFrom.getAdapter()).getPosition(spinnerTo.getSelectedItem().toString()));
            spinnerTo.setSelection(((ArrayAdapter) spinnerFrom.getAdapter()).getPosition(temp));
            conversionViewModel.setPercent(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString());
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                conversionViewModel.setPercent(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                conversionViewModel.setPercent(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        conversionViewModel.getNumber().observe(requireActivity(), item -> textFrom.setText(item));
        conversionViewModel.getResult().observe(requireActivity(), item -> textTo.setText(item));

        return view;
    }
}
