package com.sounder.sounderselector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.sounder.sounderselector.view.activity.ImagesActivity;

/**
 * 图片选择器的操作类，负责所有参数的设计及Activity的跳转等等
 *  Created by sounder on 2017/6/2.
 */

public class ImageSelector {
    /**
     * 默认的请求码，可自行设置
     * */
    public final static int REQUEST_CODE = 721;
    /**
     * 返回的intent中包含图片地址的字段
     * */
    public static final String DATA = "data";
    /**
     * ToolBar的背景颜色
     * */
    public final static String COLOR_BACKGROUND = "background";
    /**
     * ToolBar的标题颜色
     */
    public final static String COLOR_TITLE = "title_color";
    /**
     * ToolBar的标题
     */
    public final static String TEXT_TITLE = "title";
    /**
     * 是否需要裁剪
     */
    public final static String NEED_CROP = "crop";

    /**
     * 裁剪的宽度
     */
    public final static String CROP_WIDTH = "crop_width";
    /**
     * 裁剪的高度
     */
    public final static String CROP_HEIGHT = "crop_height";
    /**
     * 裁剪时图片的格式
     * {@link android.graphics.Bitmap.CompressFormat}
     */
    public final static String FORMAT = "format";
    /**
     * 裁剪后图片的质量（0-100）
     */
    public static final String QUALITY = "quality";
    /**
     * 表示是否从相机临时拍摄的照片，此间有一个DrawingCache返回黑色的异常，又该变量进行判断调用不同方法
     */
    public static final String FROM_CAMERA = "from_camera";

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

    /**
     * 构造器类，生成一个实例最后打开图片列表Activity
     */
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

        /**
         * 打开Activity
         */
        public void start(){
            mSelector.start();
        }
    }
}
