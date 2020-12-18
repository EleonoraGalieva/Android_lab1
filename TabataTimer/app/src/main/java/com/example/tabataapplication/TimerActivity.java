package com.example.tabataapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tabataapplication.Adapters.PhaseDataAdapter;
import com.example.tabataapplication.DatabaseHelper.DatabaseAdapter;
import com.example.tabataapplication.Models.Phase;
import com.example.tabataapplication.Models.Sequence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TimerActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TextView textViewCountdown;
    private FloatingActionButton fabPause;
    private FloatingActionButton fabPrev;
    private FloatingActionButton fabNext;

    private static final long COEF_FROM_MINUTES_TO_MILLISECONDS = 60000;

    private DatabaseAdapter databaseAdapter;
    private Sequence currentSequence;
    private List<Phase> phases = new ArrayList<>();
    private Phase currentPhase;
    private int currentPhaseIndex = 0;
    private long timeLeftInMilliseconds;
    private boolean isTimerRunning;

    public TimerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        textViewCountdown = findViewById(R.id.textViewCountdown);
        fabPause = findViewById(R.id.fabPause);
        fabPrev = findViewById(R.id.fabPrev);
        fabNext = findViewById(R.id.fabNext);
        recyclerView = findViewById(R.id.phaseList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        int seqId = intent.getIntExtra("idSeq", 1);
        databaseAdapter = new DatabaseAdapter(TimerActivity.this);
        databaseAdapter.open();
        currentSequence = databaseAdapter.getSequence(seqId);
        phases = databaseAdapter.getPhasesOfSequence(currentSequence.getId());
        currentPhase = phases.get(currentPhaseIndex);
        PhaseDataAdapter phaseDataAdapter = new PhaseDataAdapter(this, phases);
        recyclerView.setAdapter(phaseDataAdapter);

        timeLeftInMilliseconds = currentPhase.getTime() * COEF_FROM_MINUTES_TO_MILLISECONDS;

        fabPause.setOnClickListener(v -> pauseTimer());

        fabNext.setOnClickListener(v -> nextPhase());

        fabPrev.setOnClickListener(v -> prevPhase());

        createService();

        isTimerRunning = true;
    }

    public void createService() {
        Intent serviceIntent = new Intent(this, TimerService.class);
        serviceIntent.putExtra("idSeq", currentSequence.getId());
        serviceIntent.putExtra("timeLeft", timeLeftInMilliseconds);
        startService(serviceIntent);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long timeLeft = intent.getLongExtra("ru.timer.broadcast.timeLeft", 0);
            updateTimer(timeLeft);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(TimerService.COUNTDOWN_BR));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        stopService(new Intent(this, TimerService.class));
    }

    public void updateTimer(long timeLeftInMilliseconds) {
        int minutes = (int) (timeLeftInMilliseconds / COEF_FROM_MINUTES_TO_MILLISECONDS);
        int seconds = (int) (timeLeftInMilliseconds % COEF_FROM_MINUTES_TO_MILLISECONDS) / 1000;

        String timerLeftText;

        timerLeftText = "" + minutes;
        timerLeftText += ":";
        if (seconds < 10) {
            timerLeftText += "0";
        }
        timerLeftText += seconds;
        textViewCountdown.setText(timerLeftText);
    }

    public void pauseTimer() {
        if (isTimerRunning) {
            stopService(new Intent(this, TimerService.class));
            isTimerRunning = false;
            fabPause.setBackground(getResources().getDrawable(R.drawable.ic_start));
        } else {
            createService();
            fabPause.setBackground(getResources().getDrawable(R.drawable.ic_pause));
        }
    }

    public void nextPhase() {
        if (currentPhaseIndex == phases.size() - 1)
            return;
        currentPhaseIndex++;
        stopService(new Intent(this, TimerService.class));
        currentPhase = phases.get(currentPhaseIndex);
        timeLeftInMilliseconds = currentPhase.getTime() * COEF_FROM_MINUTES_TO_MILLISECONDS;
        createService();
    }

    public void prevPhase() {
        if (currentPhaseIndex != 0) {
            stopService(new Intent(this, TimerService.class));
            currentPhaseIndex--;
            currentPhase = phases.get(currentPhaseIndex);
            timeLeftInMilliseconds = currentPhase.getTime() * COEF_FROM_MINUTES_TO_MILLISECONDS;
            createService();
        }
    }
}
