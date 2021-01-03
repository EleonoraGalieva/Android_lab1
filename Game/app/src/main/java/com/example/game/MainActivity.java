package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), StartGameActivity.class));
            finish();
        }
    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}