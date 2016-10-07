package com.mbh.timelyview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created By MBH on 2016-10-05.
 */

public class TimelyShortTimeView extends TimelyTimeCommon {
    private int timeFormat = 1; // Default HOUR_MIN;
    public static final int FORMAT_HOUR_MIN = 1;
    public static final int FORMAT_MIN_SEC = 2;

    private int[] timeIntArr = {0,0};

    public TimelyShortTimeView(Context context) {
        super(context);
    }

    public TimelyShortTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimelyShortTimeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimelyShortTimeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        inflate(getContext(), R.layout.timely_shorttimeview_layout, this);

        h_left = (TimelyView) findViewById(R.id.ttv_hours_left);
        h_right = (TimelyView) findViewById(R.id.ttv_hours_right);
        min_left = (TimelyView) findViewById(R.id.ttv_minutes_left);
        min_right = (TimelyView) findViewById(R.id.ttv_minutes_right);

        seperator1 = (TextView) findViewById(R.id.tv_seperator1);

        array_timelyViews = new SparseArray<>();
        array_seperators = new SparseArray<>();

        array_timelyViews.put(0, h_left);
        array_timelyViews.put(1, h_right);
        array_timelyViews.put(2, min_left);
        array_timelyViews.put(3, min_right);


        array_seperators.put(0, seperator1);


        for (int i = 0; i < array_timelyViews.size(); i++){
            array_timelyViews.valueAt(i).setTextColorAndCorner(textColor, isRoundedCorner);
        }

        for (int i = 0; i < array_seperators.size(); i++){
            TextView tv = array_seperators.valueAt(i);
            tv.setTextColor(textColor);
            tv.setTextSize(seperatorsTextSize);
        }
    }

    @Override
    boolean isShortTime() {
        return true;
    }

    @Override
    protected void setTimeToTimelyViews(int[] timeArray) {
        animateCarefully(h_left, animationType==ANIMATION_ZOOM?-1:(timeIntArr[0] % 100) / 10,
                (timeArray[0] % 100) / 10);
        animateCarefully(h_right, animationType==ANIMATION_ZOOM?-1:timeIntArr[0]% 10,
                timeArray[0]% 10);

        animateCarefully(min_left, animationType==ANIMATION_ZOOM?-1:(timeIntArr[1] % 100) / 10,
                (timeArray[1] % 100) / 10);
        animateCarefully(min_right, animationType==ANIMATION_ZOOM?-1:timeIntArr[1]% 10,
                timeArray[1]% 10);

        timeIntArr = timeArray;
    }

    @Override
    public void setTime(Date date) {
        TimelyTimeUtils.checkNull(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int[] tempArray = new int[2];
        if(timeFormat == FORMAT_HOUR_MIN){
            tempArray[0] = cal.get(Calendar.HOUR_OF_DAY);
            tempArray[1] = cal.get(Calendar.MINUTE);
        }else {
            tempArray[0] = cal.get(Calendar.MINUTE);
            tempArray[1] = cal.get(Calendar.SECOND);
        }

        setTimeIfNotEqual(tempArray, timeIntArr);
    }

    @Override
    public void setTime(int[] timeIntArray) {
        setTimeChecked(timeIntArray, timeIntArr);
    }

    /**
     * Set time formatted from string
     * @param formattedTime: time should be formatted as 00:00
     */
    @Override
    public void setTime(String formattedTime){
        TimelyTimeUtils.checkNull(formattedTime);
        if(formattedTime.length() != 5) // 5 = 00:00 as string length
            throw new IllegalArgumentException("Time format should be 00:00, not "+formattedTime);
        String[] splitted = formattedTime.split(":");
        if(splitted.length != 2) // 2 = 00 00 as splitted string
            throw new IllegalArgumentException("Time format should be 00:00, not "+formattedTime);
        int field1 = TimelyTimeUtils.tryParseInt(splitted[0], -1);
        int field2 = TimelyTimeUtils.tryParseInt(splitted[1], -1);

        setTime(new int[]{field1, field2});
    }

    @Override
    public void setTime(long milliseconds) {
        TimelyTimeUtils.checkValidPositiveLong(milliseconds);
        int field1, field2;
        if(timeFormat == FORMAT_HOUR_MIN){
            field1 = (int)((milliseconds / (1000*60*60)) % 24);// Hours
            field2 = (int) ((milliseconds / (1000*60)) % 60);// minutes
        }else {
            field1 = (int) ((milliseconds / (1000*60)) % 60); // minutes
            field2 = (int) (milliseconds / 1000) % 60 ; // seconds
        }
        TimelyTimeUtils.checkValidPositiveInt(field1);
        TimelyTimeUtils.checkValidPositiveInt(field2);
        setTime(new int[]{field1, field2});
    }

    public int getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }
}
