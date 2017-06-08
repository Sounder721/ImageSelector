package com.sounder.sounderselector.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 *  Created by sounder on 2017/6/3.
 */

public interface OnImageClickListener {
    void onImageClick(String data, ImageView imageView);

    void onCameraClick();
}
