package com.example.computerscienceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatsActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth fbAuth;
    private DatabaseReference databaseReference;
    TextView tvUsername, tvEasyRecord, tvMediumRecord, tvHardRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        database = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();
        databaseReference = database.getReference(fbAuth.getUid());
        databaseReference = database.getReference();

        tvUsername = findViewById(R.id.tvUsername);
        tvEasyRecord = findViewById(R.id.tvEasyRecord);
        tvMediumRecord = findViewById(R.id.tvMediumRecord);
        tvHardRecord = findViewById(R.id.tvHardRecord);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvUsername.setText("Username: " + snapshot.child("name"));
                tvEasyRecord.setText("Username: " + snapshot.child("EASYRecord"));
                tvMediumRecord.setText("Username: " + snapshot.child("MEDIUMRecord"));
                tvHardRecord.setText("Username: " + snapshot.child("HARDRecord"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}