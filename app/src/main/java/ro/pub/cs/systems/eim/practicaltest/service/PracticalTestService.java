package ro.pub.cs.systems.eim.practicaltest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import static ro.pub.cs.systems.eim.practicaltest.general.Constants.*;

public class PracticalTestService extends Service {
    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int firstNumber = Integer.parseInt(intent.getStringExtra(LEFT_CLICKS));
        int secondNumber = Integer.parseInt(intent.getStringExtra(RIGHT_CLICKS));
        processingThread = new ProcessingThread(this, firstNumber, secondNumber);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
