package com.emidev.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    double currencyOne, currencyTwo;
    boolean isDollarsCurrencyOne = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button swapButton = findViewById(R.id.swapButton);
        Button convertButton = findViewById(R.id.convertButton);

        Spinner currencyOneSpinner = findViewById(R.id.currencyOneSpinner);
        Spinner currencyTwoSpinner = findViewById(R.id.currencyTwoSpinner);

        ArrayAdapter<CharSequence>adapterOne= ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_item);

        currencyOneSpinner.setAdapter(adapterOne);

        ArrayAdapter<CharSequence>adapterTwo= ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_item);

        currencyTwoSpinner.setAdapter(adapterTwo);

        //TextView currencyOneTextView = findViewById(R.id.currencyOneTextView);
        //TextView currencyTwoTextView = findViewById(R.id.currencyTwoTextView);

        EditText currencyOneEditText = findViewById(R.id.currencyOneEditText);
        EditText currencyTwoEditText = findViewById(R.id.currencyTwoEditText);

        /* swapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                if(isDollarsCurrencyOne){
                    //currencyOneTextView.setText(R.string.Euros);
                    //currencyTwoTextView.setText(R.string.Dollars);
                    isDollarsCurrencyOne = false;
                } else {
                    //currencyOneTextView.setText(R.string.Dollars);
                    //currencyTwoTextView.setText(R.string.Euros);
                    isDollarsCurrencyOne = true;
                }
            }

        });*/

        convertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                String currencyOne = currencyOneSpinner.getSelectedItem().toString();
                String currencyTwo = currencyTwoSpinner.getSelectedItem().toString();

            }
        });
    }
}