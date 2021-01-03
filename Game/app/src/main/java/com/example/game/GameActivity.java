package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.Models.Game;
import com.example.game.Models.Room;
import com.example.game.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    ImageView stageImage;
    TextView word;
    TextView messageAfterGameEnds;

    private Role role;
    private DatabaseReference databaseReference;
    private String roomId;
    private Game game;
    private String wordToGuess;

    private boolean gameFinished = false;
    private static final int ERRORS = 6;
    private int errorsMade = 0;
    List<String> foundLetters;
    List<String> usedLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        stageImage = findViewById(R.id.stageImage);
        word = findViewById(R.id.word);
        messageAfterGameEnds = findViewById(R.id.messageAfterGameEnds);

        Bundle extras = getIntent().getExtras();
        role = (Role) extras.getSerializable("role");
        roomId = extras.getString("roomId");
        wordToGuess = extras.getString("word");
        foundLetters = new ArrayList<>();
        usedLetters = new ArrayList<>();

        if (role == Role.PLAYER1) {
            databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(roomId).child("game1");
            databaseReference.child("state").setValue(Game.State.PLAYING);
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(roomId).child("game2");
            databaseReference.child("state").setValue(Game.State.WAITING);
        }
        readGame();
        final int n = wordToGuess.length();
        for (int i = 0; i < n; i++) {
            foundLetters.add("_");
        }
        updateWordField();
    }

    private void updateWordField() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < wordToGuess.length(); i++) {
            stringBuilder.append(foundLetters.get(i));
            stringBuilder.append(" ");
        }
        word.setText(stringBuilder.toString().trim());
    }

    private void readGame() {
        databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(roomId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Room room = snapshot.getValue(Room.class);
                assert room != null;
                if (role == Role.PLAYER1)
                    game = room.getGame1();
                else
                    game = room.getGame2();
                assert game != null;
                usedLetters = game.getUsedLetters();
                wordToGuess = game.getWord();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onLetterTouched(View view) {
        if (game.getState() == Game.State.WAITING) {
            Toast.makeText(this, "Please, wait for other player to finish", Toast.LENGTH_LONG).show();
            return;
        }
        if (gameFinished) {
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
            return;
        }

        view.setEnabled(false);
        view.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.not_clickable));

        String letter = ((Button) view).getText().toString();
        if (wordToGuess.contains(letter.toLowerCase())) {
            int index = wordToGuess.indexOf(letter.toLowerCase());
            while (index >= 0) {
                foundLetters.set(index, String.valueOf(letter.charAt(0)));
                index = wordToGuess.indexOf(letter.toLowerCase(), index + 1);
            }
            updateWordField();
            if (!foundLetters.contains("_")) {
                Toast.makeText(this, "You won!!!", Toast.LENGTH_LONG).show();
                gameFinished = true;
                addWin();
            }
        } else {
            errorsMade++;
            stageImage.setImageResource(getResources().getIdentifier("stage" + errorsMade, "drawable", getPackageName()));
            if (errorsMade >= ERRORS) {
                Toast.makeText(this, "You loose", Toast.LENGTH_LONG).show();
                gameFinished = true;
                addLoss();
            }
        }
        usedLetters.add(letter);
        updateGame();
    }

    private void addLoss() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                int temp = user.getLosses();
                databaseReference.child("losses").setValue(temp + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addWin() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                int temp = user.getWinnings();
                databaseReference.child("winnings").setValue(temp + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateGame() {
        game.setState(Game.State.WAITING);
        game.setWord(wordToGuess);
        game.setUsedLetters(usedLetters);
        if (role == Role.PLAYER1) {
            databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(roomId).child("game1");
            databaseReference.setValue(game);
            databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(roomId).child("game2").child("state");
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(roomId).child("game2");
            databaseReference.setValue(game);
            databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(roomId).child("game1").child("state");
        }
        databaseReference.setValue(Game.State.PLAYING);
        readGame();
    }
}