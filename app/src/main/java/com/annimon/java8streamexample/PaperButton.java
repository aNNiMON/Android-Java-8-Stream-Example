package com.annimon.java8streamexample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Material flat button with ripple animation.
 * @author DrFailov
 * @author aNNiMON
 */
public class PaperButton extends Button {

    private static final int ANIMATION_DELAY = 20;
    private static final int CIRCLE_OPACITY_MAX = 160;
    private static final Handler clickHandler = new Handler();

    private int mBackgroundColor;

    private final Paint mCirclePaint = new Paint();
    private int mFocusColor;
    private float mCircleX, mCircleY;
    private float mCircleSize;
    private int mCircleOpacity;

    public PaperButton(Context context) {
        super(context);

        mBackgroundColor = 0xFFFAFAFA;
        mFocusColor = 0xFFCCCCCC;

        init();
    }

    public PaperButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.PaperButton, 0, 0);
        try {
            mBackgroundColor = a.getColor(R.styleable.PaperButton_pw_backgroundColor, 0xFFFAFAFA);
            mFocusColor = a.getColor(R.styleable.PaperButton_pw_focusColor, 0xFFCCCCCC);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mCircleX = -1;
        mCircleY = -1;
        mCircleSize = 0;
        mCircleOpacity = CIRCLE_OPACITY_MAX;
        super.setBackgroundColor(0);
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mBackgroundColor);
        mCirclePaint.setColor((mCircleOpacity << 24) | (mFocusColor & 0xFFFFFF));
        canvas.drawCircle(mCircleX, mCircleY, mCircleSize, mCirclePaint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            beginHighlight(motionEvent.getX(), motionEvent.getY());
            return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        clickHandler.postDelayed(PaperButton.super::performClick, 300L);
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        mAnimationHandler.removeMessages(0);
        super.onDetachedFromWindow();
    }

    private void beginHighlight(float circleX, float circleY) {
        mAnimationHandler.removeMessages(0);
        mCircleX = circleX;
        mCircleY = circleY;
        mCircleSize = 10f;
        mCircleOpacity = CIRCLE_OPACITY_MAX;
        mAnimationHandler.sendEmptyMessage(0);
    }

    private final Handler mAnimationHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mCircleSize *= 1.5;
            mCircleOpacity -= 8;
            invalidate();
            if (mCircleOpacity > 0) {
                mAnimationHandler.sendEmptyMessageDelayed(0, ANIMATION_DELAY);
            }
        }
    };
}