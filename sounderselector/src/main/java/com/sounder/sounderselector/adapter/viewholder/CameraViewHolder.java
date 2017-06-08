package com.sounder.sounderselector.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sounder.sounderselector.listener.OnImageClickListener;

/**
 * Created by sounder on 2017/6/4.
 */

public class CameraViewHolder extends RecyclerView.ViewHolder {
    private int w=0,h=0;
    public CameraViewHolder(View itemView) {
        super(itemView);
        h = w = itemView.getResources().getDisplayMetrics().widthPixels/3;
        itemView.getLayoutParams().width = w;
        itemView.getLayoutParams().height = h;
        itemView.invalidate();
    }
    public void setView(final OnImageClickListener listener){
        if(listener != null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCameraClick();
                }
            });
        }
    }
}
