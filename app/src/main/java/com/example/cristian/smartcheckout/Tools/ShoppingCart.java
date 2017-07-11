package com.example.cristian.smartcheckout.Tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian on 7/7/2017.
 */

public class ShoppingCart {


    private List<Product> mProducts;
    private int total_number_of_products = 0;
    private double cart_weight = 0, total_weight = 0, total = 0;

    private static ShoppingCart shoppingCart = new ShoppingCart();

    private ShoppingCart(){
        mProducts = new ArrayList<>();
    }

    public static ShoppingCart getCart(){
        return shoppingCart;
    }

    public void clearCart(){
        mProducts.clear();
        total_weight = 0;
        total = 0;
        total_number_of_products = 0;
    }

    public double getTotalWeight(){
        return total_weight+cart_weight;
    }

    public double getTotal(){
        return total;
    }

    public void addProduct(Product product){
        mProducts.add(product);
        total_weight += product.weight;
        total += product.price;
        total_number_of_products++;

        Log.e("MainActivity", "Product added to cart --- "+product.toString());
    }

    public void removeProduct(int position){
        total_weight -= mProducts.get(position).weight;
        total -= mProducts.get(position).price;

        mProducts.remove(position);
        total_number_of_products--;


        Log.e("MainActivity", "Product removed from cart --- at position "+position);


    }

    public void setCartWeight(double weight){
        cart_weight = weight;

    }


    public void endShopping(){
        cart_weight = 0;
    }

    public List<Product> getProducts(){
        return mProducts;
    }

    public int getTotalNumberOfProducts(){
        return total_number_of_products;
    }

    public Product getProduct(int position){
        return mProducts.get(position);
    }

}
