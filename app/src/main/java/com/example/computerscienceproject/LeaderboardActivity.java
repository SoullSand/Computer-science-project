package com.example.computerscienceproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnReturn, btnEasy, btnMedium, btnHard;
    RecyclerView rvEasy;
    LeaderboardAdapter leaderboardAdapter;
    FBModule fbModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        rvEasy = findViewById(R.id.rvEasy);
        rvEasy.setLayoutManager(new LinearLayoutManager(this));

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        fbModule = new FBModule(this);
        ArrayList<User> allUsers = fbModule.GetUserLeaderboard();

        leaderboardAdapter = new LeaderboardAdapter(this, allUsers);
        rvEasy.setAdapter(leaderboardAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == btnReturn)
        {
            finish();
        }
    }
}