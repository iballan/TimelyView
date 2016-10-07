package com.mbh.timelyview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import static com.mbh.timelyview.TimelyTimeUtils.checkNull;
import static com.mbh.timelyview.TimelyTimeUtils.checkValidPositiveInt;
import static com.mbh.timelyview.TimelyTimeUtils.checkValidPositiveLong;
import static com.mbh.timelyview.TimelyTimeUtils.tryParseInt;

/**
 * Created By MBH on 2016-10-03.
 */

public class TimelyTimeView extends TimelyTimeCommon {
    protected TimelyView sec_left, sec_right;
    protected TextView seperator2;

    private int[] timeIntArr = {0,0,0};

    public TimelyTimeView(Context context) {
        super(context);
    }

    public TimelyTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TimelyTimeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimelyTimeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    boolean isShortTime() {
        return false;
    }

    /**
     * Set time formatted from string
     * @param formattedTime: time should be formatted as 00:00:00
     */
    @Override
    public void setTime(String formattedTime){
        checkNull(formattedTime);
        if(formattedTime.length() != 8) // 8 = 00:00:00 as string length
            throw new IllegalArgumentException("Time format should be 00:00:00, not "+formattedTime);
        String[] splitted = formattedTime.split(":");
        if(splitted.length != 3) // 3 = 00 00 00 as splitted string
            throw new IllegalArgumentException("Time format should be 00:00:00, not "+formattedTime);
        int hour = tryParseInt(splitted[0], -1);
        int min = tryParseInt(splitted[1], -1);
        int sec = tryParseInt(splitted[2], -1);

        setTime(new int[]{hour, min, sec});
    }

    @Override
    public void setTime(int[] timeIntArray){
        setTimeChecked(timeIntArray, timeIntArr);
    }

    @Override
    public void setTime(long milliseconds) {
        checkValidPositiveLong(milliseconds);
        final int hour = (int)((milliseconds / (1000*60*60)) % 24);
        checkValidPositiveInt(hour);
        final int min = (int) ((milliseconds / (1000*60)) % 60);
        checkValidPositiveInt(min);
        final int sec = (int) (milliseconds / 1000) % 60;
        checkValidPositiveInt(sec);
        setTime(new int[]{hour, min, sec});
    }

    @Override
    public void setTime(Date date){
        checkNull(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int[] tempArray = new int[3];
        tempArray[0] = cal.get(Calendar.HOUR_OF_DAY);
        tempArray[1] = cal.get(Calendar.MINUTE);
        tempArray[2] = cal.get(Calendar.SECOND);

        setTimeIfNotEqual(tempArray, timeIntArr);
    }

    @Override
    protected void setTimeToTimelyViews(int[] timeArray){

        animateCarefully(h_left, animationType==ANIMATION_ZOOM?-1:(timeIntArr[0] % 100) / 10,
                (timeArray[0] % 100) / 10);
        animateCarefully(h_right, animationType==ANIMATION_ZOOM?-1:timeIntArr[0]% 10,
                timeArray[0]% 10);

        animateCarefully(min_left, animationType==ANIMATION_ZOOM?-1:(timeIntArr[1] % 100) / 10,
                (timeArray[1] % 100) / 10);
        animateCarefully(min_right, animationType==ANIMATION_ZOOM?-1:timeIntArr[1]% 10,
                timeArray[1]% 10);

        animateCarefully(sec_left, animationType==ANIMATION_ZOOM?-1:(timeIntArr[2] % 100) / 10,
                (timeArray[2] % 100) / 10);
        animateCarefully(sec_right, animationType==ANIMATION_ZOOM?-1:timeIntArr[2]% 10,
                timeArray[2]% 10);

        timeIntArr = timeArray;
    }

    @Override
    protected void init() {
        inflate(getContext(), R.layout.timely_timeview_layout, this);

        h_left = (TimelyView) findViewById(R.id.ttv_hours_left);
        h_right = (TimelyView) findViewById(R.id.ttv_hours_right);
        min_left = (TimelyView) findViewById(R.id.ttv_minutes_left);
        min_right = (TimelyView) findViewById(R.id.ttv_minutes_right);
        sec_left = (TimelyView) findViewById(R.id.ttv_seconds_left);
        sec_right = (TimelyView) findViewById(R.id.ttv_seconds_right);

        seperator1 = (TextView) findViewById(R.id.tv_seperator1);
        seperator2 = (TextView) findViewById(R.id.tv_seperator2);

        array_timelyViews = new SparseArray<>();
        array_seperators = new SparseArray<>();

        array_timelyViews.put(0, h_left);
        array_timelyViews.put(1, h_right);
        array_timelyViews.put(2, min_left);
        array_timelyViews.put(3, min_right);
        array_timelyViews.put(4, sec_left);
        array_timelyViews.put(5, sec_right);


        array_seperators.put(0, seperator1);
        array_seperators.put(1, seperator2);


        for (int i = 0; i < array_timelyViews.size(); i++){
            array_timelyViews.valueAt(i).setTextColorAndCorner(textColor, isRoundedCorner);
        }

        for (int i = 0; i < array_seperators.size(); i++){
            TextView tv = array_seperators.valueAt(i);
            tv.setTextColor(textColor);
            tv.setTextSize(seperatorsTextSize);
        }

    }
}
