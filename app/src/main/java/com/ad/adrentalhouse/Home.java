package com.ad.adrentalhouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    NavigationView nv;
    DrawerLayout dl;
    Toolbar tb;
    private FirebaseAuth mAuth;
    private String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getUid();
        nv = findViewById(R.id.NavView);
        dl = findViewById(R.id.drawer_layout);
        tb = findViewById(R.id.toolbar);

        ActionBarDrawerToggle to = new ActionBarDrawerToggle(Home.this, dl, tb, R.string.open_drawer, R.string.close_drawer);
        dl.addDrawerListener(to);
        to.syncState();
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.apartment:
                        Intent intent = new Intent(Home.this, RentingApartment.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.indepHouse:
                        Intent i = new Intent(Home.this, RentingAHome.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.contctus:
                        Intent ic = new Intent(Home.this, ContactUs.class);
                        startActivity(ic);
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), SinginActivity.class));
                        finish();
                        Toast.makeText(Home.this, "Logout", Toast.LENGTH_SHORT).show();
                        break;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void rentYourProp(View view) {
        Intent intent = new Intent(Home.this, RentYourProperty.class);
        startActivity(intent);
    }

    public void rentaHouse(View view) {
        Intent intent = new Intent(Home.this, RentingAHome.class);
        startActivity(intent);
    }

    public void rentaApartment(View view) {
        Intent intent = new Intent(Home.this, RentingApartment.class);
        startActivity(intent);
    }
}