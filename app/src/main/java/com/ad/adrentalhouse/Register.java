package com.ad.adrentalhouse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");
    // request code
    private final int PICK_IMAGE_REQUEST = 1;
    EditText name, emai, pass, conpass;
    FirebaseAuth fAuth;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore fStroe;
    // view for image view
    private ImageView imageView;
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();
        fStroe = FirebaseFirestore.getInstance();
        name = findViewById(R.id.edname);
        emai = findViewById(R.id.edemai);
        pass = findViewById(R.id.edpass);
        conpass = findViewById(R.id.edconpass);

    }

    public void SingUp(View view) {
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        } else {
            // Initialize Firebase Auth

            String nm = name.getText().toString();
            String em = emai.getText().toString();
            String pa = pass.getText().toString();
            fAuth.createUserWithEmailAndPassword(em, pa).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                    DocumentReference df = fStroe.collection("Users").document(user.getUid());
                    Map<String, Object> userINfo = new HashMap<>();
                    userINfo.put("FullName", nm);
                    userINfo.put("UserEmail", em);
                    //Specify if the user is admin
                    userINfo.put("isUser", "1");
                    df.set(userINfo);
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void paSignIn(View view) {
        Intent intent = new Intent(Register.this, SinginActivity.class);
        startActivity(intent);
    }

    //Validate of Email
    private boolean validateEmail() {
        String emailInput = emai.getText().toString().trim();
        //To check the Email is empty
        if (emailInput.isEmpty()) {
            emai.setError("Field can't be empty");
            return false;
        }
        //to check the Email Address email are proper or not
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emai.setError("Please enter a valid email address");
            return false;
        } else {
            emai.setError(null);
            return true;
        }
    }

    //validation of User name
    private boolean validateUsername() {
        String usernameInput = name.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            name.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 20) {
            name.setError("Username too long");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    //validation of password
    private boolean validatePassword() {
        String passwordInput = pass.getText().toString().trim();
        String conpasswordInput = conpass.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            pass.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass.setError("Password too weak");
            return false;
        }
        if (!passwordInput.equals(conpasswordInput)) {
            conpass.setError("Password are not same");
            return false;
        } else {
            pass.setError(null);
            conpass.setError(null);
            return true;
        }
    }
}
