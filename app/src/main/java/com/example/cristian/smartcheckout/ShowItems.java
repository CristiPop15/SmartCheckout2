package com.example.cristian.smartcheckout;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cristian.smartcheckout.Tools.CustomAdapter;
import com.example.cristian.smartcheckout.Tools.ShoppingCart;

public class ShowItems extends AppCompatActivity {

    private FloatingActionButton new_scan, finalize;
    private RecyclerView recyclerView;
    private TextView total, total_number;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        total = (TextView)findViewById(R.id.total);
        total_number = (TextView)findViewById(R.id.total_number);
        new_scan = (FloatingActionButton)findViewById(R.id.new_scan);
        finalize = (FloatingActionButton)findViewById(R.id.finalize);

        new_scan.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_add));
        finalize.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_attach_money));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new CustomAdapter(this, ShoppingCart.getCart().getProducts(), total, total_number);
        recyclerView.setAdapter(adapter);


        total.setText(String.valueOf(ShoppingCart.getCart().getTotal()));
        total_number.setText(String.valueOf(ShoppingCart.getCart().getTotalNumberOfProducts()));


        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowItems.this, ShoppingActivity.class);
                i.putExtra("action", 0);
                Log.e("MainActivity", "finalize");
                startActivity(i);
            }
        });

        new_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowItems.this, ProductScan.class);
                Log.e("MainActivity", "new scan");
                startActivity(i);
                finish();


            }
        });
    }



}

