package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailInputLogin, passwordInputLogin;
    Button btnLoginSubmit;
    ProgressBar loginProgressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInputLogin = findViewById(R.id.emailInputLogin);
        passwordInputLogin = findViewById(R.id.passwordInputLogin);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        loginProgressBar = findViewById(R.id.loginProgressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLoginSubmit.setOnClickListener(view -> {
            String email = emailInputLogin.getText().toString().trim();
            String password = passwordInputLogin.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailInputLogin.setError("Email in required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordInputLogin.setError("Password is required");
                return;
            }

            loginProgressBar.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login in succeeded", Toast.LENGTH_SHORT).show();
                    loginProgressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getApplicationContext(), StartGameActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Sorry, something went wrong :(", Toast.LENGTH_SHORT).show();
                }
                loginProgressBar.setVisibility(View.GONE);
            });
        });
    }
}