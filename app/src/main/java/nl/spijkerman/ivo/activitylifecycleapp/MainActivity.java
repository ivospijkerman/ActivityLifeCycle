package nl.spijkerman.ivo.activitylifecycleapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int lifeTime = 0;
    private int visibilityTime = 0;
    private int focusTime = 0;

    private boolean isVisible = false;
    private boolean hasFocus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            lifeTime = savedInstanceState.getInt("lifeTime");
            visibilityTime = savedInstanceState.getInt("visibilityTime");
            focusTime = savedInstanceState.getInt("focusTime");
        }

        showTimes();
        startTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("lifeTime", lifeTime);
        outState.putInt("visibilityTime", visibilityTime);
        outState.putInt("focusTime", focusTime);
    }

    private void startTimer() {
        final Handler handler = new Handler();

        Runnable timerTask = new Runnable() {
            @Override
            public void run() {
                showTimes();
                incrementTimes();

                handler.postDelayed(this, 1000);
            }
        };
        handler.post(timerTask);
    }

    private void showTimes() {
        final TextView lifeTimeView = findViewById(R.id.text_view_life_time);
        final TextView visibilityTimeView = findViewById(R.id.text_view_visibility_time);
        final TextView focusTimeView = findViewById(R.id.text_view_focus_time);

        String lifeTimeValue = getValueString(R.string.life_time, lifeTime);
        String visibilityTimeValue = getValueString(R.string.visibility_time, visibilityTime);
        String focusTimeValue = getValueString(R.string.focus_time, focusTime);

        lifeTimeView.setText(lifeTimeValue);
        visibilityTimeView.setText(visibilityTimeValue);
        focusTimeView.setText(focusTimeValue);
    }

    private String getValueString(int stringId, int time) {
        String result;

        result = getResources().getString(stringId);
        result += "\n";
        result += secondsToString(time);

        return result;
    }

    private String secondsToString(final int time) {
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds =  time % 60;

        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    private void incrementTimes() {

        lifeTime++;

        if (isVisible)
            visibilityTime++;

        if (hasFocus)
            focusTime++;
    }
}
