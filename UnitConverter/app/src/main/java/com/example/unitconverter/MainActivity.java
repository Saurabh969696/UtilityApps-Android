package com.example.unitconverter;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner fromUnitSpinner, toUnitSpinner;
    TextView resultTextView;
    Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        // Initialize views inside onCreate
        inputValue = findViewById(R.id.inputValue);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        resultTextView = findViewById(R.id.resultTextView);
        convertButton = findViewById(R.id.convertButton);

        // Example units for conversion
        String[] units = {"Meters", "Centimeters", "Kilometers", "Grams", "Kilograms", "Liters", "Milliliters", "Celsius", "Fahrenheit"};


        // Setting up the adapter for spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        // Set onClickListener for Convert button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputValue.getText().toString().isEmpty()) {
                    resultTextView.setText("Please enter a value");
                } else {
                    try {
                        convertUnits();
                    } catch (NumberFormatException e) {
                        resultTextView.setText("Invalid input. Enter a numeric value.");
                    }
                }
            }
        });

    }


    private void convertUnits() {
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();
        double input = Double.parseDouble(inputValue.getText().toString());

        double result = 0;

        // Conversion logic
        if (fromUnit.equals("Meters") && toUnit.equals("Centimeters")) {
            result = input * 100;
        } else if (fromUnit.equals("Centimeters") && toUnit.equals("Meters")) {
            result = input / 100;
        } else if (fromUnit.equals("Kilometers") && toUnit.equals("Meters")) {
            result = input * 1000;
        } else if (fromUnit.equals("Meters") && toUnit.equals("Kilometers")) {
            result = input / 1000;
        } else if (fromUnit.equals("Grams") && toUnit.equals("Kilograms")) {
            result = input / 1000;
        } else if (fromUnit.equals("Kilograms") && toUnit.equals("Grams")) {
            result = input * 1000;
        }
        else if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            result = (input * 9/5) + 32;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            result = (input - 32) * 5/9;
        }

        // Display the result
        resultTextView.setText(String.valueOf(result));
    }
}
