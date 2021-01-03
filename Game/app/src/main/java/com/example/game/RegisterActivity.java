package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput, emailInputRegister, passwordInputRegister;
    private Button btnRegisterSubmit;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.usernameInput);
        emailInputRegister = findViewById(R.id.emailInputRegister);
        passwordInputRegister = findViewById(R.id.passwordInputRegister);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);
        progressBar = findViewById(R.id.registerProgressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnRegisterSubmit.setOnClickListener(view -> {
            String username = usernameInput.getText().toString().trim();
            String email = emailInputRegister.getText().toString().trim();
            String password = passwordInputRegister.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                usernameInput.setError("Username is required");
            }

            if (TextUtils.isEmpty(email)) {
                emailInputRegister.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordInputRegister.setError("Password is required");
                return;
            }

            if (password.length() < 6) {
                passwordInputRegister.setError("Password has to be at least 6 characters");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userId = firebaseUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("gravatar", false);
                    hashMap.put("winnings", 0);
                    hashMap.put("losses", 0);
                    databaseReference.setValue(hashMap);

                    Toast.makeText(RegisterActivity.this, "Registration succeeded", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), StartGameActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Sorry, something went wrong :(", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            });
        });
    }
}