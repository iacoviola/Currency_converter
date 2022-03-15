package com.emidev.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String last = null;

    /*  Metodo giusto per fare una funzione,
        si può/dovrebbe fare ? */
    private void convert(Spinner cOS, Spinner cTS, EditText cOET, EditText cTET) {
        if(cOET.getText().toString().isEmpty() || cOET.getText().toString().equals(last))
            return;
        String currencyOne = cOS.getSelectedItem().toString();
        String currencyTwo = cTS.getSelectedItem().toString();
        if (cOET.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.Toast, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        double amount = Double.parseDouble(cOET.getText().toString());

        Locator loc = new Locator(currencyOne, currencyTwo, amount);

        Thread request = new Thread(loc);
        request.start();

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(true);
        AlertDialog alert;
        alert  = builder.create();
        alert.show();

        try {
            request.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.dismiss();
        cTET.setText(String.format(java.util.Locale.US, "%.2f", loc.getConversion()));
        last = String.format(java.util.Locale.US, "%.2f", loc.getConversion());
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
        Button swapButton = findViewById(R.id.swapButton);

        ArrayAdapter<CharSequence> adapterOne = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_item);

        currencyOneSpinner.setAdapter(adapterOne);

        ArrayAdapter<CharSequence> adapterTwo = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_item);

        currencyTwoSpinner.setAdapter(adapterTwo);

        currencyTwoSpinner.setSelection(1);

        currencyOneEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    convert(currencyOneSpinner, currencyTwoSpinner, currencyOneEditText, currencyTwoEditText);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(currencyOneEditText.getWindowToken(), 0);
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

        swapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int pos = currencyOneSpinner.getSelectedItemPosition();
                currencyOneSpinner.setSelection(currencyTwoSpinner.getSelectedItemPosition());
                currencyTwoSpinner.setSelection(pos);
                convert(currencyOneSpinner, currencyTwoSpinner, currencyOneEditText, currencyTwoEditText);
            }
        });

    }
}