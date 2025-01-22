package com.example.computerscienceproject;

import android.content.Context;
import android.os.Handler;

public class TimerThread extends Thread {
    private boolean isWin;
    private Handler handler;

    public TimerThread(Context context) {
        isWin = false;
        handler = ((GameActivity) context).handler;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                if (!isWin)
                {
                    Thread.sleep(1000);
                    handler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void stopOrStartTimer()
    {
        isWin = !isWin;
    }
}
