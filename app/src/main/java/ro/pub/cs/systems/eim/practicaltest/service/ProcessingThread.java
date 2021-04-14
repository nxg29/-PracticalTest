package ro.pub.cs.systems.eim.practicaltest.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

import static ro.pub.cs.systems.eim.practicaltest.general.Constants.*;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private final Random random = new Random();

    private double arithmeticMean;
    private double geometricMean;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;
        arithmeticMean = (firstNumber + secondNumber) / 2;
        geometricMean = Math.sqrt(firstNumber * secondNumber);
    }

    @Override
    public void run() {
        Log.d(PROCESSING_THREAD_TAG, "Thread has started! TID: " + Thread.currentThread().getId());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(actionTypes[random.nextInt(actionTypes.length)]);
        intent.putExtra(BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
