package com.example.malte.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import se.martinstengard.conversions.temperature.*;

/**
 * Created by Martin on 2018-07-13.
 */

public class FragmentTemperature extends Fragment {
    View view;
    EditText txtFrom;
    EditText txtTo;
    Spinner spinnerFrom;
    Spinner spinnerTo;
    Button btnConvert;
    TextView lblFormula;
    TextView lblExample;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_temperature,
                container,
                false);

        txtFrom = view.findViewById(R.id.editTextFromValue);
        txtTo = view.findViewById(R.id.editTextToValue);
        spinnerFrom = view.findViewById(R.id.spinnerFromScale);
        spinnerTo = view.findViewById(R.id.spinnerToScale);
        btnConvert = view.findViewById(R.id.btnConvert);
        lblFormula = view.findViewById(R.id.textFormula);
        lblExample = view.findViewById(R.id.textExample);

        // Set temperature scales.
        setScaleToSpinner(spinnerFrom);
        setScaleToSpinner(spinnerTo);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(txtFrom.getText().toString().trim().equals("") ||
                        (spinnerFrom.getSelectedItem() == spinnerTo.getSelectedItem())){
                    return;
                }
                convert();
            }
        });

        return view;
    }

    private void setScaleToSpinner(Spinner dropdown) {
        List<Scale> scales = Arrays.asList(Scale.values());
        ArrayAdapter<Scale> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, scales);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);
    }

    public void convert() {
        Request request = new Request() {{
            fromValue = txtFrom.getText().toString();
            fromScale = Scale.valueOf(spinnerFrom.getSelectedItem().toString());
            toScale = Scale.valueOf(spinnerTo.getSelectedItem().toString());
        }};

        final Convert convert = new Convert();
        final Response response = convert.run(request);

        txtFrom.setText(response.fromValue);
        txtTo.setText(response.toValue);
        lblFormula.setText(response.formula);
        lblExample.setText(response.calculation);
    }
}
