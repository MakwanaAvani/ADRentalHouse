package com.ad.adrentalhouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText ed_email;
    Button btnfotget;
    FirebaseAuth fAuth;
    public ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        fAuth = FirebaseAuth.getInstance();
        ed_email = findViewById(R.id.ed_uemail);
        btnfotget = findViewById(R.id.btnfotget);

        btnfotget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateData()) {
                    return;
                }
                forgetpass();
            }
        });
    }

    public void backlogin(View view) {
        startActivity(new Intent(ForgetPasswordActivity.this, SinginActivity.class));
        finish();
    }

    public void forgetpass() {
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String email = ed_email.getText().toString();
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if (task.isSuccessful()) {
                    Log.d("TAG-LOGIN-------------------------", "LOGIN : " + email);
                    Toast.makeText(ForgetPasswordActivity.this, "Check your Email and Go to Login", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(ForgetPasswordActivity.this,"Error Failed",Toast.LENGTH_LONG).show();

            }
        });
    }

    private boolean validateData() {
        String emailInput = ed_email.getText().toString().trim();
        //To check the Email is empty
        if (emailInput.isEmpty()) {
            ed_email.setError("Field can't be empty");
            return false;
        }
        //to check the Email Address email are proper or not
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            ed_email.setError("Please enter a valid email address");
            return false;
        } else {
            ed_email.setError(null);
            return true;
        }
    }
}