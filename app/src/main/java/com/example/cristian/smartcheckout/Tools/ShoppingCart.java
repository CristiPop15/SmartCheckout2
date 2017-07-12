package com.example.cristian.smartcheckout.Tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian on 7/7/2017.
 */

public class ShoppingCart {


    private List<Row> mProducts;
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

        for(int i = 0; i<=mProducts.size()-1; i++) {
            Log.e("MainActivity", "for");
            if (mProducts.get(i).product.id == product.id) {
                mProducts.get(i).p.add(product);
                refresh();
                Log.e("MainActivity", "another Product added to cart --- " + product.toString());
                return;
            }
        }

        Row newRow = new Row();
        newRow.product = product;
        newRow.p.add(product);
        mProducts.add(newRow);

        refresh();
        Log.e("MainActivity", "new Product added to cart --- "+product.toString());
    }

    public void removeProduct(int position){

        mProducts.remove(position);
        refresh();

        Log.e("MainActivity", "Product removed from cart --- at position "+position);


    }

    public void setCartWeight(double weight){
        cart_weight = weight;

    }


    public void endShopping(){
        cart_weight = 0;
    }

    public List<Row> getProducts(){
        return mProducts;
    }

    public int getTotalNumberOfProducts(){
        return total_number_of_products;
    }

    public Row getProduct(int position){
        return mProducts.get(position);
    }

    public void removeOneProduct(Product p){

        for(int i = 0; i<=mProducts.size()-1; i++) {
            Log.e("MainActivity", "for");
            if (mProducts.get(i).product.id == p.id) {
                mProducts.get(i).p.remove(0);
                refresh();
                Log.e("MainActivity", "another Product removed from cart --- ");
                return;
            }
        }

    }

    private void refresh(){
        total = 0;
        total_number_of_products = 0;
        total_weight = 0;

        for(int i = 0; i <= mProducts.size()-1; i++){
            total += mProducts.get(i).getSum();
            total_number_of_products += mProducts.get(i).getNumOfItems();
            total_weight += mProducts.get(i).getTotalWeight();

            if(mProducts.get(i).p.size() < 1){
                mProducts.remove(i);

            }
        }
    }

}
