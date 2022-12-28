package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.poetryapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        binding.signup.setOnClickListener(v -> {
            String name = binding.fullName.getText().toString();
            String number = binding.phoneNumber.getText().toString();
            String email = binding.emailAddress.getText().toString().trim();
            String password= binding.password.getText().toString();

            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        startActivity(new Intent(this, LoginActivity.class));
                        progressDialog.cancel();
                        firebaseFirestore.collection("User")
                                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                .set(new UserModel(name, number, email));
                    }).addOnFailureListener(e -> progressDialog.cancel());
        });
    }

}