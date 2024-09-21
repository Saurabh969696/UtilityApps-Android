package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timerDisplay;
    private Button startButton, stopButton, holdButton;
    private Handler handler = new Handler();
    private long startTime = 0L, timeInMillis = 0L, pauseTime = 0L;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerDisplay = findViewById(R.id.timerDisplay);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        holdButton = findViewById(R.id.holdButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdTimer();
            }
        });
    }

    private void startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - pauseTime; // Start from where it left off
            handler.postDelayed(updateTimer, 0);
            isRunning = true;
        }
    }

    private void stopTimer() {
        handler.removeCallbacks(updateTimer);
        isRunning = false;
        timerDisplay.setText("00:00:00");
        pauseTime = 0L; // Reset pauseTime
    }

    private void holdTimer() {
        if (isRunning) {
            handler.removeCallbacks(updateTimer);
            pauseTime = timeInMillis; // Save the current time when paused
            isRunning = false;
        }
    }

    private Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMillis = System.currentTimeMillis() - startTime;
            int seconds = (int) (timeInMillis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            timerDisplay.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            handler.postDelayed(this, 1000);
        }
    };
}
