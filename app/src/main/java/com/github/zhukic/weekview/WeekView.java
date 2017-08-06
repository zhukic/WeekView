package com.github.zhukic.weekview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Zhukov (https://github.com/zhukic)
 */

public class WeekView extends View {

    public static final String TAG = WeekView.class.getSimpleName();

    //Размер квадрата 48 dp
    //Высота дня недели 62 dp
    //время дня - 12sp regular
    private int dividerColor = Color.rgb(224, 224, 224);

    private float hourHeight;

    private float dividerWidth;

    private float weekEventRectCornerWidth;

    private float weekEventTextSize;

    private float weekEventTextSizePadding;

    private Float maxCurrentPointY = null;

    private Paint axisPaint;
    private Paint weekEventPaint;
    private Paint weekEventNamePaint;

    private Scroller scroller;
    private PointF currentPoint = new PointF(0f, 0f);

    private GestureDetectorCompat gestureDetector;

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            if (maxCurrentPointY == null) {
                maxCurrentPointY = (hourHeight * 24) - getHeight();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll " + distanceX + " " + distanceY);

            currentPoint.y += distanceY;

            if (currentPoint.y >= maxCurrentPointY) {
                currentPoint.y = maxCurrentPointY;
            } else if (currentPoint.y < 0) {
                currentPoint.y = 0;
            }

            ViewCompat.postInvalidateOnAnimation(WeekView.this);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling");
            scroller.fling((int) e1.getX(), (int) e1.getY(), (int) velocityX, (int) velocityY, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
            ViewCompat.postInvalidateOnAnimation(WeekView.this);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            for (WeekEventRectF weekEventRectF : weekEventRectFs) {
                if (weekEventRectF.contains(e.getX(), e.getY())) {
                    if (weekEventClickListener != null) {
                        weekEventClickListener.onWeekEventClick(weekEventRectF.weekEvent);
                    }
                }
            }
            return true;
        }
    };

    private List<WeekEvent> weekEvents;

    private List<WeekEventRectF> weekEventRectFs;

    private WeekEventClickListener weekEventClickListener;

    public WeekView(Context context) {
        super(context);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        scroller = new Scroller(getContext(), null, true);
        gestureDetector = new GestureDetectorCompat(getContext(), onGestureListener);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.WeekView, 0, 0);


        hourHeight = a.getDimensionPixelSize(R.styleable.WeekView_hourHeight, 0);
        dividerWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        weekEventRectCornerWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        weekEventTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        weekEventTextSizePadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4, getResources().getDisplayMetrics());

        axisPaint = new Paint();
        axisPaint.setColor(dividerColor);
        axisPaint.setStrokeWidth(dividerWidth);

        weekEventPaint = new Paint();
        weekEventPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        weekEventNamePaint = new Paint();
        weekEventNamePaint.setTextSize(weekEventTextSize);
        weekEventNamePaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
        weekEventNamePaint.setTextAlign(Paint.Align.LEFT);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "onDraw");

        drawAxis(canvas);

        drawWeekEvents(canvas);

    }

    private void drawAxis(Canvas canvas) {
        for (int i = 1; i <= 24; i++) {
            float left = 0;
            float top = i * hourHeight - currentPoint.y;
            float right = getWidth();
            float bottom = i * hourHeight + dividerWidth - currentPoint.y;
            canvas.drawLine(0, i * hourHeight - currentPoint.y, right, i * hourHeight - currentPoint.y, axisPaint);
        }

        for (int i = 0; i < 7; i++) {
            float left = i * getWidth() / 7;
            float top = 0;
            float right = i * getWidth() / 7;
            float bottom = getHeight();
            canvas.drawLine(left, top, left, bottom, axisPaint);
        }
    }

    private void drawWeekEvents(Canvas canvas) {

        weekEventRectFs = new ArrayList<>(weekEvents.size());

        for (WeekEvent weekEvent : weekEvents) {

            float top = hourHeight * weekEvent.getStartHour() - currentPoint.y;
            float left = weekEvent.getDayOfWeek() * getWidth() / 7 + dividerWidth / 2;
            float bottom = hourHeight * weekEvent.getEndHour() - currentPoint.y;
            float right = (weekEvent.getDayOfWeek() + 1) * getWidth() / 7 - dividerWidth / 2;

            WeekEventRectF weekEventRectF = new WeekEventRectF(left, top, right, bottom, weekEvent);
            weekEventRectFs.add(weekEventRectF);

            canvas.drawRoundRect(weekEventRectF, weekEventRectCornerWidth, weekEventRectCornerWidth, weekEventPaint);

            canvas.drawText(weekEvent.getName(), (left + weekEventTextSizePadding), (bottom + top) / 2, weekEventNamePaint);

        }

    }

    public void setWeekEvents(List<WeekEvent> weekEvents) {
        this.weekEvents = weekEvents;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void setWeekEventClickListener(WeekEventClickListener weekEventClickListener) {
        this.weekEventClickListener = weekEventClickListener;
    }

    private class WeekEventRectF extends RectF {

        private final WeekEvent weekEvent;

        public WeekEventRectF(float left, float top, float right, float bottom, WeekEvent weekEvent) {
            super(left, top, right, bottom);
            this.weekEvent = weekEvent;
        }

        public WeekEvent getWeekEvent() {
            return weekEvent;
        }

    }


    public interface WeekEventClickListener {

        public void onWeekEventClick(WeekEvent weekEvent);

    }

}
