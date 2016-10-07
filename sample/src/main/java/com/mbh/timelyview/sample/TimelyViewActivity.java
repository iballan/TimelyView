package com.mbh.timelyview.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.mbh.timelyview.TimelyView;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.Random;

public class TimelyViewActivity extends AppCompatActivity {
    public static final int            DURATION       = 1000;
    public static final int            NO_VALUE       = -1;
    private TimelyView timelyView     = null;
    private SeekBar seekBar        = null;
    private Spinner fromSpinner    = null;
    private             Spinner        toSpinner      = null;
    private volatile ObjectAnimator objectAnimator = null;

    private volatile int from = NO_VALUE;
    private volatile int to   = NO_VALUE;
    Handler handler;
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timely_view);

        timelyView = (TimelyView) findViewById(R.id.textView1);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);

        handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <10; i++) {
                    final int from = i;
                    final int to = i==9?0:i+1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            timelyView.setTextColor(getRandomColor());
                            timelyView.animate(from, to).start();
                        }
                    }, 900);
                    try{Thread.sleep(1000);}catch (Exception e){}
                }
            }
        }).start();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.from_numbers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from = position - 1;
                if(from != NO_VALUE && to != NO_VALUE) {
                    objectAnimator = timelyView.animate(from, to);
                    objectAnimator.setDuration(DURATION);
                } else {
                    objectAnimator = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                to = position - 1;
                if(from != NO_VALUE && to != NO_VALUE) {
                    objectAnimator = timelyView.animate(from, to);
                    objectAnimator.setDuration(DURATION);
                } else {
                    objectAnimator = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seekBar.setMax(DURATION);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(objectAnimator != null) objectAnimator.setCurrentPlayTime(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, TimelyViewActivity.class);
        context.startActivity(starter);
    }
}
