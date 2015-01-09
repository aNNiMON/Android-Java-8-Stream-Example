package com.annimon.java8streamexample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Material seekbar.
 * @author DrFailov
 * @author aNNiMON
 */
public class PaperSeekBar extends SeekBar {

    private static final int ANIMATION_DELAY = 20;

    private final Paint mColorPaint = new Paint();
    private final Paint mGrayPaint = new Paint();

    private int mColor;
    private float mCurrentProgress;

    private float mCircleSizeCurrent;
    private float mCircleSizeNormal;
    private float mCircleSizeTouched;

    public PaperSeekBar(Context context) {
        super(context);

        mColor = 0xFFF4B400;

        init();
    }

    public PaperSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.PaperSeekBar, 0, 0);
        try {
            mColor = a.getColor(R.styleable.PaperSeekBar_pw_color, 0xFFF4B400);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mColorPaint.setColor(mColor);
        mColorPaint.setStyle(Paint.Style.FILL);
        mColorPaint.setAntiAlias(true);

        mGrayPaint.setColor(0xFFC8C8C8);
        mGrayPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mColorPaint.setStrokeWidth(h / 10f);
        mGrayPaint.setStrokeWidth(h / 10f);
        mCircleSizeNormal = h / 4f;
        mCircleSizeTouched = h / 2f;
        mCircleSizeCurrent = mCircleSizeNormal;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        final float gap = mCircleSizeTouched;
        final float width = getWidth() - 2 * gap, h2 = getHeight() / 2;
        final float cur = gap + mCurrentProgress * width / getMax();
        if (getProgress() == 0) {
            // Draw gray circle on left side
            canvas.drawLine(cur + mCircleSizeCurrent, h2, gap + width, h2, mGrayPaint);
            canvas.drawCircle(cur, h2, mCircleSizeCurrent - mGrayPaint.getStrokeWidth() / 2, mGrayPaint);
        } else {
            canvas.drawLine(mCircleSizeTouched, h2, cur, h2, mColorPaint);
            canvas.drawLine(cur + mCircleSizeCurrent, h2, gap + width, h2, mGrayPaint);
            canvas.drawCircle(cur, h2, mCircleSizeCurrent, mColorPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCircleSizeCurrent = mCircleSizeTouched;
                return true;
            case MotionEvent.ACTION_UP:
                mCircleSizeCurrent = mCircleSizeNormal;
                mAnimationHandler.sendEmptyMessage(0);
                return true;
            case MotionEvent.ACTION_MOVE:
                mCircleSizeCurrent = mCircleSizeTouched;
                mCurrentProgress = getProgress();
                return true;
        }
        return false;
    }

    protected void onDetachedFromWindow() {
        mAnimationHandler.removeMessages(0);
        super.onDetachedFromWindow();
    }

    private final Handler mAnimationHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            final float max = getProgress() - mCurrentProgress;
            mCurrentProgress += max * 0.2f;
            invalidate();
            if (Math.abs(max) > 1.0f) {
                mAnimationHandler.sendEmptyMessageDelayed(0, ANIMATION_DELAY);
            }
        }
    };
}
