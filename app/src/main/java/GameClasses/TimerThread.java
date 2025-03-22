package GameClasses;

import android.content.Context;
import android.os.Handler;

public class TimerThread extends Thread {
    private boolean active;
    private Handler handler;

    public TimerThread(Context context) {
        active = false;
        handler = ((GameActivity) context).handler;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                if (active)
                {
                    Thread.sleep(1000);
                    handler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void stopTimer()
    {
        active = false;
    }
    public void startTimer()
    {
        active = true;
    }
}
