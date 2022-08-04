package com.ad.adrentalhouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class SinginActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +       //any letter
                    "(?=.*[@#$%^&+=])" +     //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    EditText ems, pas;
    Button login;
    TextView sup;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        ems = findViewById(R.id.edsiemail);
        pas = findViewById(R.id.edsipass);
        login = findViewById(R.id.btnlogin);
        sup = findViewById(R.id.btnsignupr);
    }

    public void Login(View view) {
        // Initialize Firebase Auth
        String em = ems.getText().toString();
        String ps = pas.getText().toString();

        if (!validateEmail() | !validatePassword()) {
            return;
        } else {
            fAuth.signInWithEmailAndPassword(em, ps).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SinginActivity.this, "Loggedin Successfully", Toast.LENGTH_SHORT).show();
                    checkUserAccessLevel(authResult.getUser().getUid());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SinginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        //extract the data from document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess" + documentSnapshot.getData());
                //identify the user access Level
                if (documentSnapshot.getString("isAdmin") != null) {
                    //user is admin
                    startActivity(new Intent(getApplicationContext(), AdminHome.class));
                    finish();
                }
                if (documentSnapshot.getString("isUser") != null) {
                    //user is admin
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                }
            }
        });
    }


    public void SignupReda(View view) {
        Intent intent = new Intent(SinginActivity.this, Register.class);
        startActivity(intent);
    }

    private boolean validateEmail() {
        String emailInput = ems.getText().toString().trim();
        //To check the Email is empty
        if (emailInput.isEmpty()) {
            ems.setError("Field can't be empty");
            return false;
        }
        //to check the Email Address email are proper or not
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            ems.setError("Please enter a valid email address");
            return false;
        } else {
            ems.setError(null);
            return true;
        }
    }

    //validation of password
    private boolean validatePassword() {
        String passwordInput = pas.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            pas.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pas.setError("Password too weak");
            return false;
        } else {
            pas.setError(null);
            return true;
        }
    }


    public void forgetpass(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
        finish();
    }
}