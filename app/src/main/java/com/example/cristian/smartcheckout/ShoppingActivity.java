package com.example.cristian.smartcheckout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.cristian.smartcheckout.Tools.ShoppingCart;


public class ShoppingActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{

    private QRCodeReaderView mQRCodeReaderView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        Log.e("MainActivity", "Shopping Activity");

        checkPermissions();
    }


    private void checkPermissions() {
        boolean permissionAccepted = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        if (permissionAccepted) {
            loadCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        checkPermissions();
    }

    private void loadCamera() {
        mQRCodeReaderView = (QRCodeReaderView) findViewById(R.id.reader);
        mQRCodeReaderView.setOnQRCodeReadListener(this);
        mQRCodeReaderView.setAutofocusInterval(2000L);
        mQRCodeReaderView.setTorchEnabled(true);
        mQRCodeReaderView.setQRDecodingEnabled(true);
        mQRCodeReaderView.setBackCamera();
        mQRCodeReaderView.startCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mQRCodeReaderView != null) {
            mQRCodeReaderView.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mQRCodeReaderView != null) {
            mQRCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        onPause();

//        Intent i = this.getIntent();
//        int action = i.getExtras().getInt("action");


        try {
            ShoppingCart.getCart().setCartWeight(Double.parseDouble(text));
            Log.e("MainActivity", "Cart weight = "+text);
            Intent goToItems = new Intent(ShoppingActivity.this, ShowItems.class);
            startActivity(goToItems);
            finish();
        } catch (Exception e) {
            Toast.makeText(ShoppingActivity.this, "Cart weight error --text instead of number", Toast.LENGTH_LONG).show();
            onResume();
        }
    }


}

