package com.example.computerscienceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvUsername, tvEasyRecord, tvMediumRecord, tvHardRecord;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        FBModule fbModule = new FBModule(this);

        tvUsername = findViewById(R.id.tvUsername);
        tvEasyRecord = findViewById(R.id.tvEasyRecord);
        tvMediumRecord = findViewById(R.id.tvMediumRecord);
        tvHardRecord = findViewById(R.id.tvHardRecord);

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);
    }

    public void SetStats(User user) {
        tvUsername.setText("Username: " + user.getName());
        tvEasyRecord.setText("Easy record: " + user.getEasyRecord());
        tvMediumRecord.setText("Medium record: " + user.getMediumRecord());
        tvHardRecord.setText("Hard record: " + user.getHardRecord());
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}