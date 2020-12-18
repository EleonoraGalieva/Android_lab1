package com.example.tabataapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tabataapplication.Adapters.SeqDataAdapter;
import com.example.tabataapplication.DatabaseHelper.DatabaseAdapter;
import com.example.tabataapplication.ItemTouchHelper.SimpleItemTouchHelperCallback;
import com.example.tabataapplication.Models.Sequence;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Sequence> sequenceList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Button btnSeqAdd, btnSettings;
    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.seqList);
        btnSeqAdd = findViewById(R.id.btnSeqAdd);
        btnSettings = findViewById(R.id.btnSettings);

        databaseAdapter = new DatabaseAdapter(MainActivity.this);
        databaseAdapter.open();
        sequenceList = databaseAdapter.getSequences();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SeqDataAdapter seqDataAdapter = new SeqDataAdapter(this, sequenceList);
        recyclerView.setAdapter(seqDataAdapter);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(seqDataAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        btnSeqAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            String basicSequenceTitle = "Sequence " + String.valueOf(databaseAdapter.getCountSequence() + 1);
            int idSeq = (int) databaseAdapter.insertSequence(new Sequence(basicSequenceTitle, Color.WHITE, 1));
            intent.putExtra("idSeq", idSeq);
            databaseAdapter.close();
            finish();
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent= new Intent(v.getContext(), SettingsActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }
}
