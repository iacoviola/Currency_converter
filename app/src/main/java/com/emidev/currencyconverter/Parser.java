package com.emidev.currencyconverter;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

    public Parser(){};

    public double[] parseDocument(String file, String currencyOne, String currencyTwo, double amount){
        double values[] = new double[2];
        try{
            DocumentBuilderFactory factory;
            DocumentBuilder builder;
            Document document;
            Element root;
            NodeList cube;
            Element nd;

            System.out.println("one: " + currencyOne + " two: " + currencyTwo);

            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(new ByteArrayInputStream(file.toString().getBytes()));
            root = document.getDocumentElement();

            //get cube
            cube = root.getElementsByTagName("Cube").item(1).getChildNodes();

            for(int i = 1; i < cube.getLength(); i += 2){
                nd = (Element) cube.item(i);
                if(nd.getAttribute("currency").equals(currencyOne)){
                    values[0] = Double.parseDouble(nd.getAttribute("rate"));
                    System.out.println("values[0] " + values[0]);
                }
                if (nd.getAttribute("currency").equals(currencyTwo)){
                    values[1] = Double.parseDouble(nd.getAttribute("rate"));
                    System.out.println("values[1] " + values[1]);
                }
            }

            if (currencyOne.equals("EUR"))
                values[0] = 1;
            if (currencyTwo.equals("EUR"))
                values[1] = 1;


        } catch (ParserConfigurationException | SAXException | IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return values;
    }
}
