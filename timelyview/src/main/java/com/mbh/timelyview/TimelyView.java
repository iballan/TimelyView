package com.mbh.timelyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mbh.timelyview.animation.TimelyEvaluator;
import com.mbh.timelyview.model.NumberUtils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.util.Property;

public class TimelyView extends View {
    private static final float RATIO = 1f; // square
//    private static final float RATIO = 4f / 3f; // for any ratio
    private static final Property<TimelyView, float[][]> CONTROL_POINTS_PROPERTY = new Property<TimelyView, float[][]>(float[][].class, "controlPoints") {
        @Override
        public float[][] get(TimelyView object) {
            return object.getControlPoints();
        }

        @Override
        public void set(TimelyView object, float[][] value) {
            object.setControlPoints(value);
        }
    };
    private Paint mPaint = null;
    private Path mPath = null;
    private float[][] controlPoints = null;
    private int textColor = Color.BLACK;
    private boolean isRoundedCorner = true;
    private float strokeWidth = 5.0f;
    int lastEnd = -1;
    int lastStart = -1;

    private int width = 1;
    private int height = 1;

    public TimelyView(Context context) {
        super(context);
        init();
    }

    public TimelyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimelyView);
        textColor = typedArray.getColor(R.styleable.TimelyView_text_color, Color.BLACK);
        isRoundedCorner = typedArray.getBoolean(R.styleable.TimelyView_rounded_corner, true);
        typedArray.recycle();
        init();
    }

    public TimelyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTextColor(int color) {
        this.textColor = color;
        init();
    }

    public void setRoundedCorner(boolean isRoundedCorner){
        this.isRoundedCorner = isRoundedCorner;
        init();
    }

    protected void setTextColorAndCorner(int textColor, boolean isRoundedCorner){
        this.textColor = textColor;
        this.isRoundedCorner = isRoundedCorner;
        init();
    }

    public float[][] getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(float[][] controlPoints) {
        this.controlPoints = controlPoints;
        invalidate();
    }

    public ObjectAnimator animate(int start, int end) {
        lastEnd = end; lastStart = start;
        float[][] startPoints = NumberUtils.getControlPointsFor(start);
        float[][] endPoints = NumberUtils.getControlPointsFor(end);

        return ObjectAnimator.ofObject(this, CONTROL_POINTS_PROPERTY, new TimelyEvaluator(), startPoints, endPoints);
    }

    public ObjectAnimator animate(int end) {
        lastEnd = end;
        float[][] startPoints = NumberUtils.getControlPointsFor(-1);
        float[][] endPoints = NumberUtils.getControlPointsFor(end);

        return ObjectAnimator.ofObject(this, CONTROL_POINTS_PROPERTY, new TimelyEvaluator(), startPoints, endPoints);
    }

    protected ObjectAnimator animateCarefully(int end){
        if(lastEnd == end) return null;

        return animate(end);
    }

    protected ObjectAnimator animateCarefully(int start, int end){
        if(lastEnd ==-1|| lastStart==-1){
            lastEnd = end; lastStart = start;
        }else if(lastEnd == end && lastStart == start){
            return null;
        }

        return animate(start,end);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (controlPoints == null) return;

        int length = controlPoints.length;

//        height = getMeasuredHeight();
//        width = getMeasuredWidth();

        float minDimen = height > width ? width : height;

        mPath.reset();
        mPath.moveTo(minDimen * controlPoints[0][0], minDimen * controlPoints[0][1]);
        for (int i = 1; i < length; i += 3) {
            mPath.cubicTo(minDimen * controlPoints[i][0], minDimen * controlPoints[i][1],
                    minDimen * controlPoints[i + 1][0], minDimen * controlPoints[i + 1][1],
                    minDimen * controlPoints[i + 2][0], minDimen * controlPoints[i + 2][1]);
        }
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        int maxWidth = (int) (heigthWithoutPadding * RATIO);
        int maxHeight = (int) (widthWithoutPadding / RATIO);

        if (widthWithoutPadding > maxWidth) {
            width = maxWidth + getPaddingLeft() + getPaddingRight()+(int)strokeWidth;
        } else {
            height = maxHeight + getPaddingTop() + getPaddingBottom()+(int)strokeWidth;
        }

        setMeasuredDimension(width, height);
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        init();
    }

    private void init() {
        // A new paint with the style as stroke.
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(textColor);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        if (isRoundedCorner) {
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
        }
        mPath = new Path();
    }
}
