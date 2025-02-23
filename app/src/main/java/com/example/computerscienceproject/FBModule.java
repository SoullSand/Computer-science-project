package com.example.computerscienceproject;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Struct;

// google explanations
// https://firebase.google.com/docs/database/android/lists-of-data#java_1


public class FBModule {
    FirebaseDatabase database;
    FirebaseAuth fbAuth;
    DatabaseReference userStorage;
    private String username;
    private int easyRecord, mediumRecord, hardRecord;
    Context context;

    public FBModule(Context context) {
        database = FirebaseDatabase.getInstance("https://computer-science-pro-default-rtdb.europe-west1.firebasedatabase.app/");
        this.context = context;
        fbAuth = FirebaseAuth.getInstance();
        userStorage = database.getReference(fbAuth.getUid());

    }

    public void createUserStorage(String name) {
        // Write name of user and default records for future use
        this.username = name;
        this.easyRecord = 1000;
        this.mediumRecord = 1000;
        this.hardRecord = 1000;
        userStorage.child("name").setValue(name);
        userStorage.child("EASYRecord").setValue(1000);
        userStorage.child("MEDIUMRecord").setValue(1000);
        userStorage.child("HARDRecord").setValue(1000);
    }

    public void SetNewRecord(String difficulty, int time) {
        userStorage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String recordLocation = difficulty + "Record";
                //cast firebase record to long and then use Math.toIntExact to cast to int
                int currentRecord = Math.toIntExact((long) snapshot.child(recordLocation).getValue());
                if (time < currentRecord) {
                    userStorage.child(recordLocation).setValue(time);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public User GetUserStats() {
        userStorage.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                username = task.getResult().child("name").getValue().toString();
                easyRecord = Integer.parseInt((task.getResult().child("EASYRecord").getValue()).toString());
                mediumRecord = Integer.parseInt((task.getResult().child("MEDIUMRecord").getValue()).toString());
                hardRecord = Integer.parseInt((task.getResult().child("HARDRecord").getValue()).toString());
            }
        });
        return new User(username, easyRecord, mediumRecord, hardRecord);
    }
}
