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

public class UserLoginInfo extends AppCompatActivity {

    ProgressDialog progressDialog;
    //here we will retrive data
    FirebaseFirestore mStorage;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    ArrayList<UserInfo> userInfoArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_info);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();
        mStorage = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recuser_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userInfoArrayList = new ArrayList<UserInfo>();
        userAdapter = new UserAdapter(userInfoArrayList);
        recyclerView.setAdapter(userAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        Query query = mStorage.collection("Users");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.d("Firestore errer------------------", error.getMessage());
                    return;
                } else {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            Log.d("Firestore------------------", "fetch the data");
                            userInfoArrayList.add(dc.getDocument().toObject(UserInfo.class));
                        }
                        userAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }
            }
        });
    }
}