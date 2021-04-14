package ro.pub.cs.systems.eim.practicaltest.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest.R;
import ro.pub.cs.systems.eim.practicaltest.service.PracticalTestService;

import static ro.pub.cs.systems.eim.practicaltest.general.Constants.*;

public class PracticalTestMainActivity extends AppCompatActivity {
    private TextView leftClickText;
    private TextView rightClickText;
    private Button pressMeButton, pressMeTooButton;
    private Button navigateButton;

    private int serviceStatus = SERVICE_STOPPED;

    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private final ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int leftClicks = Integer.parseInt(leftClickText.getText().toString());
            int rightClicks = Integer.parseInt(rightClickText.getText().toString());

            switch (view.getId()) {
                case R.id.press_me_button:
                    leftClicks++;
                    leftClickText.setText(String.valueOf(leftClicks));
                    break;
                case R.id.press_me_too_button:
                    rightClicks++;
                    rightClickText.setText(String.valueOf(rightClicks));
                    break;
                case R.id.navigate_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTestSecondaryActivity.class);
                    intent.putExtra(SUM, String.valueOf(leftClicks + rightClicks));
                    startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                    break;
            }

            if (leftClicks + rightClicks > THRESHOLD) {
                Intent intent = new Intent(getApplicationContext(), PracticalTestService.class);
                intent.putExtra(LEFT_CLICKS, String.valueOf(leftClicks));
                intent.putExtra(RIGHT_CLICKS, String.valueOf(rightClicks));
                getApplicationContext().startService(intent);
                serviceStatus = SERVICE_STARTED;
            }
        }
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(BROADCAST_RECEIVER_TAG, intent.getStringExtra(BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test_main);

        pressMeButton = (Button) findViewById(R.id.press_me_button);
        pressMeButton.setOnClickListener(buttonClickListener);

        pressMeTooButton = (Button) findViewById(R.id.press_me_too_button);
        pressMeTooButton.setOnClickListener(buttonClickListener);

        navigateButton = (Button) findViewById(R.id.navigate_button);
        navigateButton.setOnClickListener(buttonClickListener);

        leftClickText = (TextView) findViewById(R.id.left_clicks_text);
        rightClickText = (TextView) findViewById(R.id.right_clicks_text);

        if (savedInstanceState !=  null) {
            if (savedInstanceState.getString(LEFT_CLICKS) != null) {
                leftClickText.setText(savedInstanceState.getString(LEFT_CLICKS));
            } else {
                leftClickText.setText("0");
            }

            if (savedInstanceState.getString(RIGHT_CLICKS) != null) {
                rightClickText.setText(savedInstanceState.getString(RIGHT_CLICKS));
            } else {
                leftClickText.setText("0");
            }
        } else {
            leftClickText.setText("0");
            rightClickText.setText("0");
        }

        for (String actionType : actionTypes) {
            intentFilter.addAction(actionType);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(getApplicationContext(), PracticalTestService.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(LEFT_CLICKS, leftClickText.getText().toString());
        savedInstanceState.putString(RIGHT_CLICKS, rightClickText.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getString(LEFT_CLICKS) != null) {
            leftClickText.setText(savedInstanceState.getString(LEFT_CLICKS));
        } else {
            leftClickText.setText("0");
        }

        if (savedInstanceState.getString(RIGHT_CLICKS) != null) {
            rightClickText.setText(savedInstanceState.getString(RIGHT_CLICKS));
        } else {
            leftClickText.setText("0");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "Activity returned with code " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}