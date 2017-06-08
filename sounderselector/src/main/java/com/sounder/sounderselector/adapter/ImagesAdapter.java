package com.sounder.sounderselector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sounder.sounderselector.R;
import com.sounder.sounderselector.adapter.viewholder.CameraViewHolder;
import com.sounder.sounderselector.adapter.viewholder.ImageViewHolder;
import com.sounder.sounderselector.javabean.ThumbImage;
import com.sounder.sounderselector.listener.OnImageClickListener;

import java.util.ArrayList;

/**
 *  Created by sounder on 2017/6/2.
 */

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ThumbImage> thumbImages;
    private Context context;
    private LayoutInflater inflater;
    private OnImageClickListener mListener;
    public ImagesAdapter(Context context,ArrayList<ThumbImage> thumbImages,OnImageClickListener listener){
        this.context = context;
        this.mListener = listener;
        this.thumbImages = thumbImages;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(inflater == null){
            inflater = LayoutInflater.from(context);
        }
        if(viewType == 0){
            View view = inflater.inflate(R.layout.layout_camera, parent, false);
            return new CameraViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.layouto_item_image, parent, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).setView(context, thumbImages.get(position-1), mListener);
        }else if(holder instanceof CameraViewHolder){
            ((CameraViewHolder) holder).setView(mListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return thumbImages==null?1:thumbImages.size()+1;
    }
}
