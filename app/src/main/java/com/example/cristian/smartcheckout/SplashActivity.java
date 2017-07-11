package com.example.cristian.smartcheckout;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.smartcheckout.Database.DBHandler;
import com.example.cristian.smartcheckout.Objects.Account;
import com.example.cristian.smartcheckout.Tools.Accounts;
import com.example.cristian.smartcheckout.Tools.CurrentAccount;
import com.example.cristian.smartcheckout.Tools.Product;
import com.example.cristian.smartcheckout.Objects.Products;
import com.example.cristian.smartcheckout.Tools.ApiService;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    private ApiService mService;
    Thread splashTread;
    TextView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();

        setupRetrofit();
        getAccounts();
        getProducts();

    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/CristiPop15/Shopy/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(ApiService.class);
    }

    private void getAccounts() {
        Call<List<Account>> call = mService.getAccounts();
        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                Accounts.accounts = (ArrayList<Account>) response.body();
          //      Toast.makeText(SplashActivity.this, "credentials loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Error getting credentials. Please restart app. Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("MainActivity", t.getMessage());
            }
        });
    }

    private void getProducts() {
        Call<List<Product>> call = mService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Products.products = (ArrayList<Product>) response.body();
           //     Toast.makeText(SplashActivity.this, "Products were loaded from server!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Error getting the products. Please restart app. Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("MainActivity", t.getMessage());
            }
        });
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        iv = (TextView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }

                    DBHandler db = new DBHandler(SplashActivity.this,null,null,1);
                    Log.e("MainActivity", "Db created");

                    if(db.getCurrentAccount().getEmail()!=null) {
                        Log.e("MainActivity", "Current != null");
                        if(autoLogin(db.getCurrentAccount())) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            CurrentAccount.create(db.getCurrentAccount());
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                        else {
                            Log.e("MainActivity", "Incorrect credentials");
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    }
                    else {
                        Log.e("MainActivity", "Current == null");
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashActivity.this.finish();
                }

            }
        };
        splashTread.start();

    }

    private boolean autoLogin(Account a){
        Log.e("MainActivity", "Auto login");
        for(Account credentials: Accounts.accounts){
            if(a.getEmail().equals(credentials.getEmail()))
                return BCrypt.checkpw(a.getPassword(), credentials.getPassword());
        }
        return false;
    }


}
