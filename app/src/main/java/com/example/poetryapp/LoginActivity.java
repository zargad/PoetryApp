package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.poetryapp.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        binding.login.setOnClickListener(v -> {
            progressDialog = new ProgressDialog(this);
            String email = binding.emailAddress.getText().toString().trim();
            String password = binding.password.getText().toString();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        progressDialog.cancel();
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.cancel();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
        binding.resetPassword.setOnClickListener(v -> {
            String email = binding.emailAddress.getText().toString().trim();
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(unused -> Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
        });
        binding.goToSignUpActivity.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }
}