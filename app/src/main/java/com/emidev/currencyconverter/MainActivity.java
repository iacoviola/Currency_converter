package com.emidev.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    double currencyOne, currencyTwo;
    boolean isDollarsCurrencyOne = true;

    //Metodo giusto per fare una funzione ?
    //Si può/dovrebbe fare ?
    private void convert (Spinner cOS, Spinner cTS, EditText cOET, EditText cTET){
        String currencyOne = cOS.getSelectedItem().toString();
        String currencyTwo = cTS.getSelectedItem().toString();
        if(cOET.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), R.string.Toast, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        double amount = Double.parseDouble(cOET.getText().toString());

        Locator loc = new Locator(currencyOne, currencyTwo, amount);

        Thread request = new Thread(loc);

        request.start();
        try {
            request.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cTET.setText(String.format(java.util.Locale.US, "%.2f", loc.getConversion()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner currencyOneSpinner = findViewById(R.id.currencyOneSpinner);
        Spinner currencyTwoSpinner = findViewById(R.id.currencyTwoSpinner);
        EditText currencyOneEditText = findViewById(R.id.currencyOneEditText);
        EditText currencyTwoEditText = findViewById(R.id.currencyTwoEditText);

        Button convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<CharSequence>adapterOne= ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_item);

        currencyOneSpinner.setAdapter(adapterOne);

        ArrayAdapter<CharSequence>adapterTwo= ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_item);

        currencyTwoSpinner.setAdapter(adapterTwo);

        currencyOneEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    convert(currencyOneSpinner, currencyTwoSpinner, currencyOneEditText, currencyTwoEditText);
                    return true;
                }
                return false;
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                convert(currencyOneSpinner, currencyTwoSpinner, currencyOneEditText, currencyTwoEditText);
            }
        });
    }
}