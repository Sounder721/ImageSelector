package com.sounder.sounderselector.widgets;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.sounder.sounderselector.util.Logger;
/**
 *  Created by sounder on 2017/6/3.
 */

public class PhotoView extends ImageView {
    public final static int MODE_NONE = 0;
    public final static int MODE_ZOOM = 1;
    private int mode = MODE_NONE;

    private float mLastX1,mLastX2,mLastY1,mLastY2;
    private float mScaleDistance;
    private Matrix matrix = new Matrix();

    public PhotoView(Context context) {
        super(context);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mLastX1 = event.getX();
                mLastY1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = MODE_ZOOM;
                mLastX2 = event.getX(1);
                mLastY2 = event.getY(1);
                mScaleDistance = (float)Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Logger.i("pointer up");
                mode = MODE_NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mode == MODE_NONE){
                    //移动
                }else{
                    Logger.i("yes");
                    matrix.set(getImageMatrix());
                    getImageMatrix().postScale(1.5f,1.5f);
                }
                break;
            default:break;
        }
        return true;
    }
}
