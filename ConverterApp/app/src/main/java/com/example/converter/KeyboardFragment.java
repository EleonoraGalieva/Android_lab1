package com.example.converter;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

public class KeyboardFragment extends Fragment {

    private ConversionViewModel conversionViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversionViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ConversionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_keyboard, container, false);

        view.findViewById(R.id.button0).setOnClickListener(item -> conversionViewModel.setNumber("0"));
        view.findViewById(R.id.button1).setOnClickListener(item -> conversionViewModel.setNumber("1"));
        view.findViewById(R.id.button2).setOnClickListener(item -> conversionViewModel.setNumber("2"));
        view.findViewById(R.id.button3).setOnClickListener(item -> conversionViewModel.setNumber("3"));
        view.findViewById(R.id.button4).setOnClickListener(item -> conversionViewModel.setNumber("4"));
        view.findViewById(R.id.button5).setOnClickListener(item -> conversionViewModel.setNumber("5"));
        view.findViewById(R.id.button6).setOnClickListener(item -> conversionViewModel.setNumber("6"));
        view.findViewById(R.id.button7).setOnClickListener(item -> conversionViewModel.setNumber("7"));
        view.findViewById(R.id.button8).setOnClickListener(item -> conversionViewModel.setNumber("8"));
        view.findViewById(R.id.button9).setOnClickListener(item -> conversionViewModel.setNumber("9"));
        view.findViewById(R.id.buttonDot).setOnClickListener(item -> conversionViewModel.setDot());
        view.findViewById(R.id.buttonConv).setOnClickListener(item -> conversionViewModel.convert());
        view.findViewById(R.id.buttonC).setOnClickListener(item -> conversionViewModel.setC());

        return view;
    }
}
