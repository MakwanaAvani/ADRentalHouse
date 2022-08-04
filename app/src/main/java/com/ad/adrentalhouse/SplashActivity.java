package com.ad.adrentalhouse;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String current_user = mAuth.getUid();

//    protected void onStart() {
//        super.onStart();
//        userCheck();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread splah = new Thread() {
            public void run() {
                try {
                    super.run();
                    sleep(1500);

                } catch (InterruptedException e) {

                } finally {
                    userCheck();
                    // startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    //finish();
                }
                //
            }
        };
        splah.start();
        //splash screen wait for 2 sce and than launch MainActivity
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                userCheck();
//                //startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                finish();
//            }
//        }, 1000);

    }

    protected void userCheck() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getString("isAdmin") != null) {
                            startActivity(new Intent(getApplicationContext(), AdminHome.class));
                            finish();
                        } else if (documentSnapshot.getString("isUser") != null) {
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        }
    }
}