package com.example.carlo.cambiodemoneda;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner mSpinnerFromCurrency;
    private Spinner mSpinnerToCurrency;
    private String[] currencies = {"DOP","USD","EUR"};
    private String number;
    private String digit;
    private boolean dp_test = false;
    private String Ans;
    private double tasaDOP = 0.021024;
    private double tasaUSD = 1;
    private double tasaEUR = 1.18127;
    private int maxLength = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.one).setOnClickListener(this);
        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
        findViewById(R.id.four).setOnClickListener(this);
        findViewById(R.id.five).setOnClickListener(this);
        findViewById(R.id.six).setOnClickListener(this);
        findViewById(R.id.seven).setOnClickListener(this);
        findViewById(R.id.eight).setOnClickListener(this);
        findViewById(R.id.nine).setOnClickListener(this);
        findViewById(R.id.dp).setOnClickListener(this);
        findViewById(R.id.zero).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.button_update).setOnClickListener(this);

        mSpinnerFromCurrency = (Spinner) findViewById(R.id.spinnerFromCurrency);

        ArrayAdapter<String> adapterActualCurrency = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapterActualCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerFromCurrency.setAdapter(adapterActualCurrency);

        mSpinnerToCurrency = (Spinner) findViewById(R.id.spinnerToCurrency);

        ArrayAdapter<String> adapterTransformingCurrency = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapterTransformingCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerToCurrency.setAdapter(adapterTransformingCurrency);

    }

    @Override
    public void onClick(View view) {
        //Mostrar cantidad a convertir
        TextView textFromCurrency = (TextView) findViewById(R.id.textFromCurrency);
        switch (view.getId()){
            case R.id.one:
                digit = "1";
                break;
            case R.id.two:
                digit = "2";
                break;
            case R.id.three:
                digit = "3";
                break;
            case R.id.four:
                digit = "4";
                break;
            case R.id.five:
                digit = "5";
                break;
            case R.id.six:
                digit = "6";
                break;
            case R.id.seven:
                digit = "7";
                break;
            case R.id.eight:
                digit = "8";
                break;
            case R.id.nine:
                digit = "9";
                break;
            case R.id.dp:
                digit = ".";
                break;
            case R.id.zero:
                digit = "0";
                break;
            case R.id.delete:
                digit = "&lt";
                if (number.length() > 0 || !TextUtils.isEmpty(number)) {
                    if (number.substring(number.length() - 1).equals(".")) {
                        dp_test = false;
                    }
                    number = number.substring(0, number.length() - 1);
                }
                break;
            case R.id.button_update:
                digit = null;
                break;
        }
        if ((number == null || number.length() <= maxLength) && digit != "&lt" && digit != null){
            switch (digit){
                case ".":
                    if (dp_test == false){
                        if (number != null){
                            number = number + digit;
                        }
                        else {
                            number = 0 + digit;
                        }
                        dp_test = true;
                    }
                    break;
                default:
                    if (number == null){
                        number = digit;
                    }
                    else {
                        number = number + digit;
                    }
            }
        }
        if (mSpinnerFromCurrency.getSelectedItem().toString() == "EUR"){
            textFromCurrency.setText("€ " + number);
        }
        else {
            textFromCurrency.setText("$ " + number);
        }

        //Mostrar cantidad convertida
        TextView textToCurrency = (TextView) findViewById(R.id.textToCurrency);
        if (number.length() > 0){
            switch (mSpinnerFromCurrency.getSelectedItem().toString()){
                case "DOP":
                    switch (mSpinnerToCurrency.getSelectedItem().toString()){
                        case "DOP":
                            Ans = Double.toString((tasaDOP/tasaDOP)*Double.parseDouble(number));
                            break;
                        case "USD":
                            Ans = Double.toString((tasaDOP/tasaUSD)*Double.parseDouble(number));
                            break;
                        case "EUR":
                            Ans = Double.toString((tasaDOP/tasaEUR)*Double.parseDouble(number));
                            break;
                    }
                    break;
                case "USD":
                    switch (mSpinnerToCurrency.getSelectedItem().toString()){
                        case "DOP":
                            Ans = Double.toString((tasaUSD/tasaDOP)*Double.parseDouble(number));
                            break;
                        case "USD":
                            Ans = Double.toString((tasaUSD/tasaUSD)*Double.parseDouble(number));
                            break;
                        case "EUR":
                            Ans = Double.toString((tasaUSD/tasaEUR)*Double.parseDouble(number));
                            break;
                    }
                    break;
                case "EUR":
                    switch (mSpinnerToCurrency.getSelectedItem().toString()){
                        case "DOP":
                            Ans = Double.toString(((tasaEUR/tasaDOP)*Double.parseDouble(number)));
                            break;
                        case "USD":
                            Ans = Double.toString((tasaEUR/tasaUSD)*Double.parseDouble(number));
                            break;
                        case "EUR":
                            Ans = Double.toString((tasaEUR/tasaEUR)*Double.parseDouble(number));
                            break;
                    }
                    break;
            }
        }
        else {
            if (TextUtils.isEmpty(number)){
                Ans = "0";
            }
        }
        if (Ans.length() > maxLength + 4){
            if (mSpinnerToCurrency.getSelectedItem().toString() == "EUR"){
                textToCurrency.setText("€ " + Ans.substring(0,maxLength + 5));
            }
            else{
                textToCurrency.setText("$ " + Ans.substring(0,maxLength + 5));
            }
        }
        else {
            if (mSpinnerToCurrency.getSelectedItem().toString() == "EUR"){
                textToCurrency.setText("€ " + Ans);
            }
            else{
                textToCurrency.setText("$ " + Ans);
            }
        }
        digit = null;
    }
}