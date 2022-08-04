package com.ad.adrentalhouse;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getUid();
        setContentView(R.layout.activity_main);
        requestPermissions();


    }

    private void showCustomDialog() {
        AlertDialog builder = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Internet Connection Alert")
                .setCancelable(false)
                .setMessage("Please Check Your Internet Connection")
                .setPositiveButton("Connecnt", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            startActivity(new Intent(android.provider.Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
                        } else {
                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        }
                        // startActivity(new Intent(android.provider.Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private boolean isNetworkAvailable(MainActivity view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilconn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobilconn != null && mobilconn.isConnected()) || (wificonn != null && wificonn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissions() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        // Toast.makeText(MainActivity.this, "Permission given", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }

    public void pReg(View view) {

        if (!isNetworkAvailable(MainActivity.this)) {
            showCustomDialog();
        } else {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        }
    }

    public void Singin(View view) {
        if (!isNetworkAvailable(MainActivity.this)) {
            showCustomDialog();
        } else {
            Intent intent = new Intent(MainActivity.this, SinginActivity.class);
            startActivity(intent);
        }
    }
}