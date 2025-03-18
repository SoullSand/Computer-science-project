package com.example.computerscienceproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class UpdateFirebaseService extends Service {
    FBModule fbModule;
    FirebaseAuth firebaseAuth;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            fbModule = new FBModule(getApplicationContext());  // gets the context of where the service was called from
            String action = intent.getStringExtra("action");
            if ("Register".equals(action)) {
                RegisterUser(intent);
            }
            if ("SetRecord".equals(action))
            {
                SetRecord(intent);
            }
        }
        return START_STICKY;
    }

    private void RegisterUser(Intent intent) {
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        fbModule.createNewUser(username, email, password);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            stopSelf();
        }
    }
    private void SetRecord(Intent intent)
    {
        String difficulty = intent.getStringExtra("difficulty");
        int time = intent.getIntExtra("time", 999);
        fbModule.SetNewRecord(difficulty, time);
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
