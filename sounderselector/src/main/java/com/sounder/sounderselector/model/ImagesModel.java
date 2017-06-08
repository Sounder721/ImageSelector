package com.sounder.sounderselector.model;

import android.content.Context;

import com.sounder.sounderselector.javabean.ThumbImage;

import java.util.ArrayList;

/**
 *  Created by sounder on 2017/6/2.
 */

public interface ImagesModel {
    ArrayList<ThumbImage> getImages(Context context);
}
