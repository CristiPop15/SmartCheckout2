package com.example.cristian.smartcheckout.Tools;

/**
 * Created by Cristian on 7/7/2017.
 */

public class Product {

    public int id;
    public String productName;
    public double price;
    public double weight;


    @Override
    public String toString() {
        return productName;
    }
}
