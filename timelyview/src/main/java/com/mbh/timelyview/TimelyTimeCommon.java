package com.mbh.timelyview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;

import java.util.Date;

/**
 * Created By MBH on 2016-10-05.
 */

abstract class TimelyTimeCommon extends LinearLayout{
    public final static int ANIMATION_ZOOM = 1;
    public final static int ANIMATION_MATERIAL = 2;

    protected SparseArray<TimelyView> array_timelyViews;
    protected SparseArray<TextView> array_seperators;
    protected TimelyView h_left, h_right;
    protected TimelyView min_left, min_right;

    protected TextView seperator1;

    protected int textColor = Color.BLACK;
    protected boolean isRoundedCorner = true;
    protected int seperatorsTextSize = 16;
    protected float strokeWidth = 5.0f;
    protected int animationType = ANIMATION_MATERIAL;

    public TimelyTimeCommon(Context context) {
        super(context);
        init();
    }

    public TimelyTimeCommon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TimelyTimeCommon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        handleAttributes(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimelyTimeCommon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        handleAttributes(context, attrs);
    }

    protected void animateCarefully(TimelyView timelyView, int start, int end){
        ObjectAnimator oa;
        if(start == -1) {
            oa = timelyView.animateCarefully(end);
        }else {
            oa = timelyView.animateCarefully(start, end);
        }
        if (oa != null) oa.start();
    }

    abstract void init();


    protected void handleAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimelyView);
        textColor = typedArray.getColor(R.styleable.TimelyTimeView_text_color, Color.BLACK);
        isRoundedCorner = typedArray.getBoolean(R.styleable.TimelyTimeView_rounded_corner, true);
        seperatorsTextSize = typedArray.getInt(R.styleable.TimelyTimeView_seperatorsTextSize, 20);
        animationType = typedArray.getInt(R.styleable.TimelyTimeView_animation_type, ANIMATION_MATERIAL);
        strokeWidth = typedArray.getFloat(R.styleable.TimelyTimeView_stroke_width, 5.0f);
        typedArray.recycle();
    }

    abstract boolean isShortTime();

    abstract void setTimeToTimelyViews(int[] timeArray);

    abstract public void setTime(Date date);

    abstract public void setTime(int[] timeIntArray);

    abstract public void setTime(long milliseconds);

    abstract public void setTime(String formattedTime);

    public void setTextColor(int textColor){
        this.textColor = textColor;
        setTextColor(array_timelyViews, array_seperators, textColor);
    }

    public void setStrokeWidth(float strokeWidth){
        if(strokeWidth<=0f) throw new IllegalArgumentException("Stroke width must be more than 0");
        this.strokeWidth = strokeWidth;
        for (int i = 0; i < array_timelyViews.size(); i++){
            array_timelyViews.valueAt(i).setStrokeWidth(strokeWidth);
        }
    }

    /**
     * Set text color to all timelyviews and textviews(seperators)
     * @param _timelyViews: Array of timely views
     * @param _seperators: array of seperators
     * @param textColor: new text color to be set
     */
    protected void setTextColor(SparseArray<TimelyView> _timelyViews , SparseArray<TextView> _seperators, int textColor){

        for (int i = 0; i < _timelyViews.size(); i++){
            _timelyViews.valueAt(i).setTextColor(textColor);
        }

        for (int i = 0; i < _seperators.size(); i++){
            _seperators.valueAt(i).setTextColor(textColor);
        }
    }

    /**
     * This will check the arrays before applying it to the TimelyViews, cuz the array will be provided by user
     * @param newArray: New array provided by user
     * @param olderArray: Array that present the previous time
     */
    protected void setTimeChecked(int[] newArray, int[] olderArray){
        TimelyTimeUtils.checkTimelyTimeArray(newArray, isShortTime());
        setTimeIfNotEqual(newArray, olderArray);
    }

    /**
     * Start animation if new time different from the older one, otherwise do nothing
     * @param arrayToShow: Array needs to be shown
     * @param prevArray: Array that present the previous time
     */
    protected void setTimeIfNotEqual(int[] arrayToShow, int[] prevArray){
        if(TimelyTimeUtils.compareArrays(arrayToShow, prevArray)){
            return;
        }
        setTimeToTimelyViews(arrayToShow);
    }

    /**
     * Will change the (":") seperators text size which is not included in the timely views
     * @param _seperators: seperators textviews
     * @param seperatorsTextSize: text size to be set
     */
    private void setSeperatorsTextSize(SparseArray<TextView> _seperators,int seperatorsTextSize){
        for (int i = 0; i < _seperators.size(); i++){
            _seperators.valueAt(i).setTextSize(seperatorsTextSize);
        }
    }

    private void setRoundedCorner(SparseArray<TimelyView> _timelyViews,boolean isRoundedCorner){
        for (int i = 0; i < _timelyViews.size(); i++){
            _timelyViews.valueAt(i).setRoundedCorner(isRoundedCorner);
        }
    }

    public void setSeperatorsTextSize(int seperatorsTextSize){
        if(seperatorsTextSize <= 0) throw new IllegalArgumentException("Textsize cannot be less than or equals to 0");
        this.seperatorsTextSize = seperatorsTextSize;
        setSeperatorsTextSize(array_seperators, seperatorsTextSize);
    }

    private void setRoundedCorner(boolean isRoundedCorner){
        this.isRoundedCorner = isRoundedCorner;
        setRoundedCorner(array_timelyViews, isRoundedCorner);
    }
}
