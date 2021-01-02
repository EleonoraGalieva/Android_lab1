package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.game.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Statistic extends AppCompatActivity {
    TextView wonGamesTv, failedGamesTv;
    int losses, winnings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        failedGamesTv = findViewById(R.id.failedGamesTv);
        wonGamesTv = findViewById(R.id.wonGamesTv);

        losses = getIntent().getIntExtra("userLosses", 0);
        winnings = getIntent().getIntExtra("userWinnings", 0);
        failedGamesTv.setText(String.valueOf(losses));
        wonGamesTv.setText(String.valueOf(winnings));
    }
}