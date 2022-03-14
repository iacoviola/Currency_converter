package com.emidev.currencyconverter;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

import java.net.URL;

public class Locator implements Runnable{

    private String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private String currencyOne;
    private double valueOne;
    private String currencyTwo;
    private double valueTwo;
    private double amount;

    public Locator(String currencyOne, String currencyTwo, double amount){
        this.currencyOne = currencyOne;
        this.currencyTwo = currencyTwo;
        this.amount = amount;
    }

    public double getConversion(){
        return amount / valueOne * valueTwo;
    }

    public void run() {
        URL server;
        HttpsURLConnection service;
        BufferedReader input;
        String line;
        int status;

        try {
            server = new URL(url.toString());
            service = (HttpsURLConnection) server.openConnection();
            service.setRequestProperty("Host", "www.ecb.europa.eu");
            service.setRequestProperty("Accept", "application/xml");
            service.setRequestProperty("Accept-Charset", "UTF-8");
            service.setRequestMethod("GET");
            service.setDoInput(true);
            service.connect();
            status = service.getResponseCode();
            System.out.println(status);
            if (status != 200) {
                return;
            }

            input = new BufferedReader(new InputStreamReader(service.getInputStream(), "UTF-8"));

            StringBuilder xml = new StringBuilder();

            while ((line = input.readLine()) != null) {
                xml.append(line);
            }
            input.close();

            Log.d("before parser", xml.toString());

            Parser parser = new Parser();
            double values[] = parser.parseDocument(xml.toString(), currencyOne, currencyTwo, amount);
            valueOne = values[0];
            valueTwo = values[1];
        } catch (IOException e) { }
    }
}

