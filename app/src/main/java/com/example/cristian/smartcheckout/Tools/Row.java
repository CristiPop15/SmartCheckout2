package com.example.cristian.smartcheckout.Tools;

import java.util.ArrayList;

/**
 * Created by Cristian on 7/12/2017.
 */

public class Row {
    public Product product;
    public ArrayList<Product> p = new ArrayList<>();

    public int getNumOfItems(){
        return p.size();
    }

    public double getSum(){
        return p.size() * product.price;
    }

    public double getTotalWeight(){
        return (p.size()-1) * product.weight;
    }

    public void addProduct(){
        p.add(product);
    }

    public void removeProduct(){
        p.remove(0);
    }

}
