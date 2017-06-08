package com.sounder.sounderselector.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sounder.sounderselector.R;
import com.sounder.sounderselector.util.Logger;

/**
 *  Created by sounder on 2017/6/3.
 */

public class CropView extends View{
    private int mWidth;//裁剪区域的宽度，不是view的宽度，下同
    private int mHeight;
    private int mStartX;
    private int mStartY;
    private int mStrokeWidth;
    private Paint mPaint;

    private Rect rect,rLeft,rRight,rTop,rBottom;

    private float mLastX,mLastY;
    private int mStrokeColor;

    public CropView(Context context) {
        super(context);
        init();
    }
    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mWidth = mHeight = getResources().getDimensionPixelOffset(R.dimen.default_crop_size);
        mStrokeWidth = getResources().getDimensionPixelOffset(R.dimen.default_stroke_width);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mStrokeColor = Color.GREEN;
        mPaint.setColor(mStrokeColor);
        rect = new Rect();
        rLeft = new Rect();
        rRight = new Rect();
        rTop = new Rect();
        rBottom = new Rect();
        int sw = getResources().getDisplayMetrics().widthPixels;
        int sh = getResources().getDisplayMetrics().heightPixels;
        mStartX = (sw-mWidth)/2;
        mStartY = (sh - mHeight)/2;
    }

    private boolean mMoveable = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                mMoveable = contains(mLastX,mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                if(mMoveable) {
                    float x = event.getX();
                    float y = event.getY();
                    float tx = mStartX + (x - mLastX);
                    float ty = mStartY + (y - mLastY);

                    if(tx >= 0 && tx <= getMeasuredWidth() - mWidth) {
                        mStartX  = (int) tx;
                        mLastX = x;
                    }
                    if(ty >= 0 && ty <= getMeasuredHeight() - mHeight){
                        mStartY = (int) ty;
                        mLastY = y;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mMoveable = false;
                break;
            default:break;
        }
        return mMoveable || super.onTouchEvent(event);
    }
    private boolean contains(float x,float y){
        return x-mStartX >= 0 && y-mStartY>=0 && x-mStartX <= mWidth && y - mStartY <= mHeight;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        drawBack(canvas);
        drawFront(canvas);
    }
    private void drawBack(Canvas canvas){
        /*绘制半透明背景
        涉及四个Rect
         */
        mPaint.setColor(Color.argb(125,0,0,0));
        mPaint.setStyle(Paint.Style.FILL);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        rTop.set(0,0,w,mStartY);
        rLeft.set(0,mStartY,mStartX,mStartY+mHeight);
        rRight.set(mStartX+mWidth,mStartY,w,mStartY+mHeight);
        rBottom.set(0,mStartY+mHeight,w,h);
        canvas.drawRect(rTop,mPaint);
        canvas.drawRect(rLeft,mPaint);
        canvas.drawRect(rRight,mPaint);
        canvas.drawRect(rBottom,mPaint);
    }
    private void drawFront(Canvas canvas){
        rect.set(mStartX,mStartY,mStartX+mWidth,mStartY+mHeight);
        mPaint.setColor(mStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawRect(rect,mPaint);

    }
    public void setSize(int width,int height){
        if(width != 0 && height !=0) {
            this.mWidth = width;
            this.mHeight = height;
            int sw = getResources().getDisplayMetrics().widthPixels;
            int sh = getResources().getDisplayMetrics().heightPixels;
            mStartX = (sw-mWidth)/2;
            mStartY = (sh - mHeight)/2;
            invalidate();
        }
    }
    public Rect getRect(){
        Rect r = new Rect(rect.left,rect.top,mWidth,mHeight);
        return r;
    }
    public void setStrokeColor(int strokeColor) {
        this.mStrokeColor = strokeColor;
        invalidate();
    }
}
