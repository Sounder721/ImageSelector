package com.sounder.sounderselector.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.sounder.sounderselector.javabean.ThumbImage;
import com.sounder.sounderselector.util.Logger;

import java.util.ArrayList;

/**
 *  Created by sounder on 2017/6/2.
 */

public class ImageModelImp implements ImagesModel {
    @Override
    public ArrayList<ThumbImage> getImages(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                },null,null,"_id DESC");
        if(cursor != null) {
            ArrayList<ThumbImage> thumbImages = new ArrayList<>();
            while (cursor.moveToNext()) {
                ThumbImage image = new ThumbImage();
                image.setId(cursor.getInt(0));
                image.setData(cursor.getString(1));
                thumbImages.add(image);
            }
            cursor.close();
            return thumbImages;
        }
        return null;
    }
}
