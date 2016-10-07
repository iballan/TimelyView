package com.mbh.timelyview.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mbh.timelyview.TimelyShortTimeView;
import com.mbh.timelyview.TimelyTimeView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimelyTimeViewActivity extends AppCompatActivity {

    TimelyTimeView ttv;
    TimelyShortTimeView tstv_hours, tstv_mins;
    Timer timer;

    public static void start(Context context) {
        Intent starter = new Intent(context, TimelyTimeViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timely_time_view);

        ttv = (TimelyTimeView) findViewById(R.id.ttv);
        tstv_hours = (TimelyShortTimeView) findViewById(R.id.tstv_hours);
        tstv_mins = (TimelyShortTimeView) findViewById(R.id.tstv_mins);

        // Full time 00:00:00
        ttv.setTextColor(Color.WHITE);
        ttv.setSeperatorsTextSize(50);
        ttv.setTime(new int[]{99, 99, 99});
        ttv.setTime(new int[]{0, 0, 0});

        // Short time *HOURS:MINUTES* 00:00
        tstv_hours.setTextColor(Color.BLACK);
        tstv_hours.setTimeFormat(TimelyShortTimeView.FORMAT_HOUR_MIN);
        tstv_hours.setSeperatorsTextSize(50);
        tstv_hours.setTime("99:99");
        tstv_hours.setTime("00:00");

        // Short time *MINUTES:SECONDS* 00:00
        tstv_mins.setTextColor(Color.WHITE);
        tstv_mins.setTimeFormat(TimelyShortTimeView.FORMAT_MIN_SEC);
        tstv_mins.setSeperatorsTextSize(50);
        tstv_mins.setStrokeWidth(20f);
        tstv_mins.setTime("99:99");
        tstv_mins.setTime("00:00");
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timer.scheduleAtFixedRate(getTimerTask(), 1500, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        timer = null;
    }

    private TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date now = new Date();
                        ttv.setTime(now);

                        tstv_mins.setTime(now.getTime()); // You give as milliseconds
                        tstv_hours.setTime(now);
                    }
                });
            }
        };
    }
}
