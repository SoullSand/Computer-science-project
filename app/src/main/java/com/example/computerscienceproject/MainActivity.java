package com.example.computerscienceproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGame, btnStats, btnLeaderboard, btnSettings;
    private ActivityResultLauncher<Intent> launcher;

    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGame = findViewById(R.id.btnGame);
        btnStats = findViewById(R.id.btnStats);
        btnLeaderboard = findViewById(R.id.btnLeaderBoard);
        btnSettings = findViewById(R.id.btnSettings);

        btnGame.setOnClickListener(this);
        btnStats.setOnClickListener(this);
        btnLeaderboard.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

        difficulty = "EASY";

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == RESULT_OK) {
                            Intent intent = o.getData();
                            difficulty = intent.getStringExtra("Difficulty");
                        }
                    }
                }
        );

    }

    @Override
    public void onClick(View v) {
        if (v == btnGame) {
            Intent i = new Intent(this, GameActivity.class);
            i.putExtra("Difficulty", difficulty);
            startActivity(i);
        }
        if (v == btnStats) {
            Intent i = new Intent(this, StatsActivity.class);
            startActivity(i);
        }
        if (v == btnLeaderboard) {
            Intent i = new Intent(this, LeaderboardActivity.class);
            startActivity(i);
        }
        if (v == btnSettings) {
            Intent i = new Intent(this, SettingsActivity.class);
            i.putExtra("Difficulty", difficulty);
            launcher.launch(i);
        }
    }
}