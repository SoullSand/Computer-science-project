package com.example.computerscienceproject;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// google explanations
// https://firebase.google.com/docs/database/android/lists-of-data#java_1


public class FBModule {
    FirebaseDatabase database;
    FirebaseAuth fbAuth;
    DatabaseReference userStorage;
    Context context;
    private ArrayList<User> users;

    public FBModule(Context context) {
        database = FirebaseDatabase.getInstance("https://computer-science-pro-default-rtdb.europe-west1.firebasedatabase.app/");
        this.context = context;
        fbAuth = FirebaseAuth.getInstance();
        userStorage = database.getReference(fbAuth.getUid());
        if (context instanceof StatsActivity) {
            GetUserStats();
        }
        users = new ArrayList<>();
        GetUsersFromFB();
    }

    public void createUserStorage(String name) {
        // Write name of user and default records for future use
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

    public void GetUserStats() {
        userStorage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("name").getValue().toString();
                int easyRecord = Integer.parseInt((snapshot.child("EASYRecord").getValue()).toString());
                int mediumRecord = Integer.parseInt((snapshot.child("MEDIUMRecord").getValue()).toString());
                int hardRecord = Integer.parseInt((snapshot.child("HARDRecord").getValue()).toString());
                User user = new User(username, easyRecord, mediumRecord, hardRecord);
                ((StatsActivity) context).SetStats(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GetUsersFromFB() {
        DatabaseReference reference = database.getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users = new ArrayList<>();
                // for each user file in firebase
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String username = snap.child("name").getValue().toString();
                    int easyRecord = Integer.parseInt((snap.child("EASYRecord").getValue()).toString());
                    int mediumRecord = Integer.parseInt((snap.child("MEDIUMRecord").getValue()).toString());
                    int hardRecord = Integer.parseInt((snap.child("HARDRecord").getValue()).toString());
                    User user = new User(username, easyRecord, mediumRecord, hardRecord);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public ArrayList<User> GetUserLeaderboard()
    {
        return users;
    }
}
