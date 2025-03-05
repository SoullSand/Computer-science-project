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
    ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        rvEasy = findViewById(R.id.rvEasy);
        rvEasy.setLayoutManager(new LinearLayoutManager(this));

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        fbModule = new FBModule(this);
        allUsers = new ArrayList<User>();

        leaderboardAdapter = new LeaderboardAdapter(this, allUsers);
        rvEasy.setAdapter(leaderboardAdapter);
    }
    public void AddUserToList(User user)
    {
        allUsers.add(user);
        leaderboardAdapter.notifyDataSetChanged();
    }
    public void ResetUserList()
    {
        allUsers.clear();
    }


    @Override
    public void onClick(View v) {
        if (v == btnReturn)
        {
            finish();
        }
    }
}