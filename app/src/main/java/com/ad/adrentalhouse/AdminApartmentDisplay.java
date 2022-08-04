package com.ad.adrentalhouse;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminApartmentDisplay extends AppCompatActivity {
    ProgressDialog progressDialog;
    //here we will retrive data
    FirebaseFirestore mStorage;
    RecyclerView recyclerView;
    AdminPropertyAdapter adminPropertyAdapter;
    ArrayList<Property> propertyArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_apartment_display);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();
        mStorage = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.rec_admin_homeview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        propertyArrayList = new ArrayList<Property>();
        adminPropertyAdapter = new AdminPropertyAdapter(propertyArrayList);
        recyclerView.setAdapter(adminPropertyAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        Query query = mStorage.collection("RentProperty").whereEqualTo("Property_Type", "Apartment");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.d("Firestore errer------------------", error.getMessage());
                    return;
                } else {
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            Log.d("Firestore------------------", "fetch the data");
                            propertyArrayList.add(dc.getDocument().toObject(Property.class));
                        }
                        adminPropertyAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }
            }
        });
    }
}