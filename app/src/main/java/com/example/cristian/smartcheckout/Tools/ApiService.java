package com.example.cristian.smartcheckout.Tools;

import com.example.cristian.smartcheckout.Objects.Account;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Cristian on 7/7/2017.
 */


public interface ApiService {

    @GET("products.json")
    Call<List<Product>> getProducts();

    @GET("accounts.json")
    Call<List<Account>> getAccounts();
}
