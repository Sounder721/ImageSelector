package com.sounder.sounderselector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.sounder.sounderselector.view.activity.ImagesActivity;

/**
 *  Created by sounder on 2017/6/2.
 */

public class ImageSelector {
    public final static int REQUEST_CODE = 721;
    /**返回的intent中包含图片地址的字段*/
    public static final String DATA = "data";
    public final static String COLOR_BACKGROUND = "background";
    public final static String COLOR_TITLE = "title_color";
    public final static String TEXT_TITLE = "title";
    public final static String NEED_CROP = "crop";
    public final static String CROP_WIDTH = "crop_width";
    public final static String CROP_HEIGHT = "crop_height";
    public final static String FORMAT = "format";
    public static final String QUALITY = "quality";
    public static final String FROM_ACNERA = "from_camera";

    private Activity mContext;
    private int mRequestCode;
    private int mToolBarBgColor;
    private int mTitleColor;
    private String mTitle;
    private boolean mNeedCrop;
    private int mCropWidth;
    private int mCropHeight;
    private Bitmap.CompressFormat mFormat;
    private int mQuality;
    private ImageSelector(){
        mRequestCode = REQUEST_CODE;
        mToolBarBgColor = Color.BLUE;
        mTitleColor = Color.WHITE;
        mTitle = "Choose Image";
        mNeedCrop = false;
        mCropWidth = mCropHeight = 200;
        mFormat = Bitmap.CompressFormat.JPEG;
        mQuality = 90;
    }
    private void start(){
        Intent intent = new Intent(mContext, ImagesActivity.class);
        intent.putExtra(COLOR_BACKGROUND,mToolBarBgColor);
        intent.putExtra(COLOR_TITLE,mTitleColor);
        intent.putExtra(TEXT_TITLE,mTitle);
        intent.putExtra(NEED_CROP,mNeedCrop);
        intent.putExtra(CROP_WIDTH,mCropWidth);
        intent.putExtra(CROP_HEIGHT,mCropHeight);
        intent.putExtra(FORMAT,mFormat);
        intent.putExtra(QUALITY,mQuality);

        mContext.startActivityForResult(intent,mRequestCode);
    }

    public static class Builder{
        private ImageSelector mSelector;
        public Builder(){
            mSelector = new ImageSelector();
        }
        public Builder with(Activity context){
            mSelector.mContext = context;
            return this;
        }
        /**
         * 设置打开图片选择器Activity的请求码，默认即为 {@link #REQUEST_CODE}
         * @param requestCode
         * @return
         */
        public Builder setRequestCode(int requestCode){
            mSelector.mRequestCode = requestCode;
            return this;
        }
        public Builder setBackgroundColor(int backgroundColor){
            mSelector.mToolBarBgColor = backgroundColor;
            return this;
        }
        public Builder setTitleColor(int titleColor){
            mSelector.mTitleColor = titleColor;
            return this;
        }
        public Builder setTitle(String title){
            mSelector.mTitle = title;
            return this;
        }
        public Builder needCrop(boolean needCrop){
            mSelector.mNeedCrop = needCrop;
            return this;
        }
        public Builder cropSize(int w, int h){
            mSelector.mCropWidth = w;
            mSelector.mCropHeight = h;
            return this;
        }
        public Builder format(Bitmap.CompressFormat format){
            mSelector.mFormat = format;
            return this;
        }
        public Builder quality(int quality){
            mSelector.mQuality = quality;
            return this;
        }
        public void start(){
            mSelector.start();
        }
    }
}
