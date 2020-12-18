package com.example.converter.free;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.converter.ConversionViewModel;
import com.example.converter.R;


public class DataFragment extends Fragment {

    private ConversionViewModel conversionViewModel;
    private TextView textFrom, textTo;
    private Spinner spinnerFrom, spinnerTo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversionViewModel = ViewModelProviders.of(getActivity()).get(ConversionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data, container, false);

        textFrom = view.findViewById(R.id.textFrom);
        textTo = view.findViewById(R.id.textTo);
        spinnerTo = view.findViewById(R.id.spinnerTo);
        spinnerFrom = view.findViewById(R.id.spinnerFrom);

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
