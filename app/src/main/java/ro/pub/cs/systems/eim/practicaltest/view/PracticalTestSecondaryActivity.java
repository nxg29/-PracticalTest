package ro.pub.cs.systems.eim.practicaltest.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ro.pub.cs.systems.eim.practicaltest.R;

import static ro.pub.cs.systems.eim.practicaltest.general.Constants.SUM;

public class PracticalTestSecondaryActivity extends AppCompatActivity {
    private TextView sumText;
    private Button okButton, cancelButton;
    private final ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test_secondary);

        sumText = (TextView) findViewById(R.id.sum_text);

        okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(buttonClickListener);

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();

        if (intent != null) {
            String sum = intent.getStringExtra(SUM);
            sumText.setText(sum);
        }
    }
}
