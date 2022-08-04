package com.ad.adrentalhouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AdminHome extends AppCompatActivity {


    NavigationView nv;
    DrawerLayout dlo;
    Toolbar tb;
    TextView totuser, tothome, totapartmet;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Integer count = 0, counthome = 0, countapart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getIntent();
        nv = findViewById(R.id.NavViewA);
        dlo = findViewById(R.id.admindraw_layout);
        tb = findViewById(R.id.toolbar2);
        totuser = findViewById(R.id.totuser);
        tothome = findViewById(R.id.tothouse);
        totapartmet = findViewById(R.id.totapartmet);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        ActionBarDrawerToggle to = new ActionBarDrawerToggle(AdminHome.this, dlo, tb, R.string.open_drawer, R.string.close_drawer);
        dlo.addDrawerListener(to);
        to.syncState();
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent inti= new Intent(AdminHome.this, AdminHome.class);
                        startActivity(inti);
                        finish();
                        Toast.makeText(AdminHome.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.apartment:
                        Intent intent = new Intent(AdminHome.this, AdminApartmentDisplay.class);
                        startActivity(intent);
                        Toast.makeText(AdminHome.this, "Apartement", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.indepHouse:
                        Intent in = new Intent(AdminHome.this, AdminHomeDisplay.class);
                        startActivity(in);
                        Toast.makeText(AdminHome.this, "Independent House", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contctus:
                        Intent ic = new Intent(AdminHome.this, ContactUs.class);
                        startActivity(ic);
                        break;
                    case R.id.logout:
                        logoutAm();
                        Toast.makeText(AdminHome.this, "Logout", Toast.LENGTH_SHORT).show();
                        break;
                }
                dlo.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Query query = fStore.collection("Users");
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {
                    if (snapshot.getData().toString() != null) {
                        count++;

                    }
                }
                String cou = count.toString();
                totuser.setText(cou);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAGUSERCOUNT", "IS FAIL");
            }
        });

        Query query1 = fStore.collection("RentProperty").whereEqualTo("Property_Type", "Independent House");
        query1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {
                    if (snapshot.getData().toString() != null) {
                        counthome++;

                    }
                }
                String cou = counthome.toString();
                tothome.setText(cou);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAGUSERCOUNT", "IS FAIL");
            }
        });
        Query query3 = fStore.collection("RentProperty").whereEqualTo("Property_Type", "Apartment");
        query3.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {
                    if (snapshot.getData().toString() != null) {
                        countapart++;

                    }
                }
                String cou = countapart.toString();
                totapartmet.setText(cou);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAGUSERCOUNT", "IS FAIL");
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (dlo.isDrawerOpen(GravityCompat.START)) {
            dlo.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //logout method
    public void logoutAm() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), SinginActivity.class));
        finish();
    }

    public void userInfop(View view) {
        startActivity(new Intent(getApplicationContext(), UserLoginInfo.class));
    }

    public void tothoue(View view) {
        startActivity(new Intent(getApplicationContext(), AdminHomeDisplay.class));
    }

    public void totApap(View view) {
        startActivity(new Intent(getApplicationContext(), AdminApartmentDisplay.class));
    }
}
