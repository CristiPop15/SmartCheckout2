package com.example.cristian.smartcheckout;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cristian.smartcheckout.Database.DBHandler;
import com.example.cristian.smartcheckout.Objects.Account;
import com.example.cristian.smartcheckout.Tools.AdsAdapter;
import com.example.cristian.smartcheckout.Tools.CurrentAccount;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String cristiEmail = "cristipop@yahoo.com";
    private String sergiuEmail = "sergiumarcus@yahoo.com";

    ImageView profile_image_header;
    TextView name_text_header;
    TextView email_text_header;
    RecyclerView recyclerView;
    AdsAdapter adapter;
    private ArrayList<Integer> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        settingUpContent();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.start_shopping) {
            Intent i = new Intent(MainActivity.this, ShoppingActivity.class);
            startActivity(i);
        } else if (id == R.id.add_preferences) {

        }  else if (id == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Iesire")
                    .setMessage("Sunteti sigur ca doriti sa iesiti?")
                    .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if(CurrentAccount.getInstance()!=null) {
                                DBHandler db = new DBHandler(MainActivity.this, null, null, 1);
                                CurrentAccount.getInstance().logout();

                                db.deleteSavedAccount();
                                Intent i = new Intent(MainActivity.this, SplashActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else
                                Log.e("MainActivity", "No user logged in");


                        }
                    })
                    .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void settingUpContent(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        profile_image_header = (ImageView)headerView.findViewById(R.id.imageView);
        name_text_header = (TextView)headerView.findViewById(R.id.user_name);
        email_text_header = (TextView)headerView.findViewById(R.id.user_email);

        Account account = CurrentAccount.getInstance().getAccount();

        lst = new ArrayList<>();

        lst.add(R.drawable.kaufland1);
        lst.add(R.drawable.kaufland2);
        lst.add(R.drawable.lidl2);
        lst.add(R.drawable.kaufland3);
        lst.add(R.drawable.lidl3);
        lst.add(R.drawable.kaufland1);
        lst.add(R.drawable.lidl2);

        email_text_header.setText(account.getEmail());

        if(account.getEmail().equals(cristiEmail)){
            profile_image_header.setImageResource(R.drawable.cristi);
            name_text_header.setText("Pop Cristian");
        }
        else if(account.getEmail().equals(sergiuEmail)){
            profile_image_header.setImageResource(R.drawable.marcussergiu);
            name_text_header.setText("Marcus Sergiu");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new AdsAdapter(lst);
        recyclerView.setAdapter(adapter);

    }
}
