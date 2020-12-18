package com.example.tabataapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.tabataapplication.DatabaseHelper.DatabaseAdapter;
import com.example.tabataapplication.Models.Phase;
import com.example.tabataapplication.Models.Sequence;

import java.util.ArrayList;
import java.util.List;

public class TimerService extends Service {
    private static final long COEF_FROM_MINUTES_TO_MILLISECONDS = 60000;
    public static final String COUNTDOWN_BR = "ru.timer.broadcast.COUNTDOWN_BR";

    private DatabaseAdapter databaseAdapter;
    private CountDownTimer countDownTimer;
    private Sequence currentSequence;
    private int setsAmount;
    private List<Phase> phases = new ArrayList<>();
    private Phase currentPhase;
    private long timeLeftInMilliseconds;
    private int currentPhaseIndex = 0;
    private int idSeq;
    private MediaPlayer mediaPlayer;

    Intent intentBroadcast = new Intent(COUNTDOWN_BR);

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                intentBroadcast.putExtra("ru.timer.broadcast.timeLeft", timeLeftInMilliseconds);
                sendBroadcast(intentBroadcast);
                int seconds = (int) (timeLeftInMilliseconds % COEF_FROM_MINUTES_TO_MILLISECONDS) / 1000;
                if (seconds == 10) {
                    mediaPlayer.start();
                }
            }

            @Override
            public void onFinish() {
                currentPhaseIndex++;
                if (currentPhaseIndex == phases.size()) {
                    setsAmount--;
                    if (setsAmount == 0) {
                        stopTimer();
                    }
                    currentPhaseIndex = 0;
                }
                currentPhase = phases.get(currentPhaseIndex);
                timeLeftInMilliseconds = currentPhase.getTime() * COEF_FROM_MINUTES_TO_MILLISECONDS;
                startTimer();
            }
        }.start();
    }

    public void stopTimer() {
        countDownTimer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        databaseAdapter = new DatabaseAdapter(getApplicationContext());
        databaseAdapter.open();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.signal);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        idSeq = intent.getIntExtra("idSeq", 0);
        timeLeftInMilliseconds = intent.getLongExtra("timeLeft", 0);

        currentSequence = databaseAdapter.getSequence(idSeq);
        setsAmount = currentSequence.getSetsAmount();
        phases = databaseAdapter.getPhasesOfSequence(currentSequence.getId());
        currentPhase = phases.get(currentPhaseIndex);

        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopTimer();
        databaseAdapter.close();
        super.onDestroy();
    }
}
