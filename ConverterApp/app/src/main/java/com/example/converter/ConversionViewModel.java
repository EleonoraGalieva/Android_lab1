package com.example.converter;

import android.content.ClipData;
import android.content.ClipboardManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class ConversionViewModel extends ViewModel {

    private final MutableLiveData<String> dataFrom = new MutableLiveData<String>();
    private final MutableLiveData<Double> percent = new MutableLiveData<Double>();
    private final MutableLiveData<String> dataTo = new MutableLiveData<String>();

    public void setNumber(String item) {
        if (dataFrom.getValue() != null && dataFrom.getValue().equals("0") && item.equals("0"))
            dataFrom.setValue(null);
        else if (dataFrom.getValue() != null && !dataFrom.getValue().equals("0"))
            dataFrom.setValue(dataFrom.getValue() + item);
        else
            dataFrom.setValue(item);
    }

    public LiveData<String> getNumber() {
        return dataFrom;
    }

    public void setPercent(String from, String to) {
        if (from.equals(to)) percent.setValue(1.0);
        switch (from) {
            case "kg": {
                switch (to) {
                    case "lb":
                        percent.setValue(2.205);
                        break;
                    case "oz":
                        percent.setValue(35.274);
                        break;
                }
            }
            break;
            case "lb": {
                switch (to) {
                    case "kg":
                        percent.setValue(0.454);
                        break;
                    case "oz":
                        percent.setValue(16.0);
                        break;
                }
            }
            break;
            case "oz": {
                switch (to) {
                    case "kg":
                        percent.setValue(0.0283);
                        break;
                    case "lb":
                        percent.setValue(0.0625);
                        break;
                }
            }
            break;
            case "km": {
                switch (to) {
                    case "mile":
                        percent.setValue(0.621);
                        break;
                    case "ft":
                        percent.setValue(3280.84);
                        break;
                }
            }
            break;
            case "mile": {
                switch (to) {
                    case "km":
                        percent.setValue(1.609);
                        break;
                    case "ft":
                        percent.setValue(5280.0);
                        break;
                }
            }
            break;
            case "ft": {
                switch (to) {
                    case "km":
                        percent.setValue(0.000305);
                        break;
                    case "mile":
                        percent.setValue(0.0001894);
                        break;
                }
            }
            break;
            case "rub": {
                switch (to) {
                    case "euro":
                        percent.setValue(0.011);
                        break;
                    case "dollars":
                        percent.setValue(0.013);
                        break;
                }
            }
            break;
            case "euro": {
                switch (to) {
                    case "rub":
                        percent.setValue(90.90);
                        break;
                    case "dollars":
                        percent.setValue(1.16);
                        break;
                }
            }
            break;
            case "dollars": {
                switch (to) {
                    case "rub":
                        percent.setValue(78.15);
                        break;
                    case "euro":
                        percent.setValue(0.86);
                        break;
                }
            }
            break;
        }
    }

    public void setDot() {
        if (Objects.requireNonNull(dataFrom.getValue()).contains("."))
            return;
        else if (dataFrom.getValue() == null)
            dataFrom.setValue("0.");
        else
            dataFrom.setValue(dataFrom.getValue() + ".");
    }

    public void convert() {
        dataTo.setValue(Double.toString(Double.parseDouble(Objects.requireNonNull(dataFrom.getValue())) * Objects.requireNonNull(percent.getValue())));
    }

    public LiveData<String> getResult() {
        return dataTo;
    }

    public void setC() {
        if (dataTo.getValue() == null || dataFrom.getValue() == null)
            return;
        else {
            dataTo.setValue("0");
            dataFrom.setValue("0");
        }
        convert();
    }

    public void copyFrom(ClipboardManager clipboardManager) {
        ClipData clipData = ClipData.newPlainText("from", dataFrom.getValue());
        clipboardManager.setPrimaryClip(clipData);
    }

    public void copyTo(ClipboardManager clipboardManager) {
        ClipData clipData = ClipData.newPlainText("to", dataTo.getValue());
        clipboardManager.setPrimaryClip(clipData);
    }

    public void swap() {
        String temp = dataFrom.getValue();
        dataFrom.setValue(dataTo.getValue());
        dataTo.setValue(temp);
    }
}
