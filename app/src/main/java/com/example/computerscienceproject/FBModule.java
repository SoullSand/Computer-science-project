package com.example.computerscienceproject;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

// google explanations
// https://firebase.google.com/docs/database/android/lists-of-data#java_1


public class FBModule {
    FirebaseDatabase database;
    FirebaseAuth fbAuth;
    DatabaseReference userStorage;
    Context context;

    public FBModule(Context context) {
        database = FirebaseDatabase.getInstance("https://computer-science-pro-default-rtdb.europe-west1.firebasedatabase.app/");
        this.context = context;
        fbAuth = FirebaseAuth.getInstance();
        if (context instanceof StatsActivity) {
            GetCurrentUserStats();
        }
        GetUsersFromFB(Difficulties.EASY);
    }

    public void createUserStorage(String name) {
        // Write name of user and default records for future use
        userStorage = database.getReference("Users").child((fbAuth.getUid()));
        userStorage.child("name").setValue(name);
        userStorage.child(Difficulties.EASY.toString()).setValue(1000);
        userStorage.child(Difficulties.MEDIUM.toString()).setValue(1000);
        userStorage.child(Difficulties.HARD.toString()).setValue(1000);
    }

    public void SetNewRecord(Difficulties difficulty, int time) {
        userStorage = database.getReference("Users").child((fbAuth.getUid()));
        userStorage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String recordLocation = difficulty.toString();
                //cast firebase record to long and then use Math.toIntExact to cast to int
                int currentRecord = Math.toIntExact((Long) snapshot.child(recordLocation).getValue());
                if (time < currentRecord) {
                    userStorage.child(recordLocation).setValue(time);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void SignOut()
    {
        userStorage = null;
        fbAuth.signOut();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }

    public void GetCurrentUserStats() {
        userStorage = database.getReference("Users").child((fbAuth.getUid()));
        userStorage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("name").getValue().toString();
                String easy = snapshot.child(Difficulties.EASY.toString()).getValue().toString();
                String medium = snapshot.child(Difficulties.MEDIUM.toString()).getValue().toString();
                String hard = snapshot.child(Difficulties.HARD.toString()).getValue().toString();

                int easyRecord = Integer.parseInt(easy);
                int mediumRecord = Integer.parseInt(medium);
                int hardRecord = Integer.parseInt(hard);
                User user = new User(username, easyRecord, mediumRecord, hardRecord);
                ((StatsActivity) context).SetStatsText(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void createNewUser(String username, String email, String password) {
        fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                createUserStorage(username);
            }
        });
    }

    public void GetUsersFromFB(Difficulties difficulty) {
        Query query = database.getReference("Users").orderByChild(difficulty.toString()).limitToFirst(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (context instanceof LeaderboardActivity)
                {
                    ((LeaderboardActivity) context).ResetUserList();
                    // for each user file in firebase
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String username = snap.child("name").getValue().toString();
                        String easy = snap.child(Difficulties.EASY.toString()).getValue().toString();
                        String medium = snap.child(Difficulties.MEDIUM.toString()).getValue().toString();
                        String hard = snap.child(Difficulties.HARD.toString()).getValue().toString();

                        int easyRecord = Integer.parseInt(easy);
                        int mediumRecord = Integer.parseInt(medium);
                        int hardRecord = Integer.parseInt(hard);
                        User user = new User(username, easyRecord, mediumRecord, hardRecord);
                        ((LeaderboardActivity) context).AddUserToList(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
