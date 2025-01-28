package com.example.computerscienceproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// google explanations
// https://firebase.google.com/docs/database/android/lists-of-data#java_1


public class FB {
    FirebaseDatabase database;
    Context context;
    public FB(Context context) {
        //database = FirebaseDatabase.getInstance("https://fbrecordst-default-rtdb.firebaseio.com");
        database = FirebaseDatabase.getInstance("https://computer-science-pro-default-rtdb.europe-west1.firebasedatabase.app/");
        this.context = context;

        // read the records from the Firebase and order them by the record from highest to lowest
        // limit to only 8 items
        Query myQuery = database.getReference("records").orderByChild("score").limitToLast(8);

        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference myRef = database.getReference(FirebaseAuth.getInstance().getUid());
        myRef = database.getReference("records/" + FirebaseAuth.getInstance().getUid());
        //DatabaseReference myRef = database.getReference().child(FirebaseAuth.getInstance().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(context, "name onCancelled", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setRecord()
    {
        // Write a message to the database
        DatabaseReference myRef = database.getReference("a"); // push adds new node with unique value
        myRef.setValue("bddddd");
        //DatabaseReference myRef = database.getReference("records/" + FirebaseAuth.getInstance().getUid());

    }

    public void setPrivateRecord(String name, int record)
    {
        // Write a message to the database
        //DatabaseReference myRef = database.getReference("records").push(); // push adds new node with unique value

        DatabaseReference myRef = database.getReference("records/" + FirebaseAuth.getInstance().getUid());

    }
}
