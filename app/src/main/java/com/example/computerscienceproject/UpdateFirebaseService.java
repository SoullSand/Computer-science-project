package com.example.computerscienceproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateFirebaseService extends Service {
    public UpdateFirebaseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}