package com.sounder.sounderselector.adapter.viewholder;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sounder.sounderselector.R;
import com.sounder.sounderselector.javabean.ThumbImage;
import com.sounder.sounderselector.listener.OnImageClickListener;

/**
 *  Created by sounder on 2017/6/2.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder{
    private ImageView imageView;
    private int w=0,h=0;
    public ImageViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        h = w = itemView.getResources().getDisplayMetrics().widthPixels/3;
        itemView.getLayoutParams().width = w;
        itemView.getLayoutParams().height = h;
        itemView.invalidate();
    }
    public void setView(Context context, ThumbImage image, OnImageClickListener listener){
        final  String data = image.getData();
        final OnImageClickListener _listener = listener;
        if(!TextUtils.isEmpty(image.getData())){
            Glide.with(context).load(image.getData())
                    .centerCrop()
                    .placeholder(R.drawable.img_default)
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(_listener != null){
                        _listener.onImageClick(data, imageView);
                    }
                }
            });
        }
    }
}
