package com.sounder.sounderselector.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sounder.sounderselector.ImageSelector;
import com.sounder.sounderselector.R;
import com.sounder.sounderselector.adapter.ImagesAdapter;
import com.sounder.sounderselector.constant.BaseActivity;
import com.sounder.sounderselector.databinding.ActivityImagesBinding;
import com.sounder.sounderselector.javabean.ThumbImage;
import com.sounder.sounderselector.listener.OnImageClickListener;
import com.sounder.sounderselector.presenter.ImagesPresenter;
import com.sounder.sounderselector.util.Logger;
import com.sounder.sounderselector.util.MD5Util;
import com.sounder.sounderselector.view.view.ImagesView;

import java.io.File;
import java.util.ArrayList;

public class ImagesActivity extends BaseActivity implements ImagesView,OnImageClickListener{
    private ActivityImagesBinding mBind;
    private ImagesPresenter mPresenter;
    private boolean mNeedCrop;
    /**若需要拍照，则记录文件名*/
    private String mCameraPhotoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initWindow();
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this,R.layout.activity_images);
        parseIntent();
        init();
    }
    private void init(){
        mBind.recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mPresenter = new ImagesPresenter(this);
        mPresenter.getImages(this);
    }
    private void parseIntent(){
        Intent intent = getIntent();
        mNeedCrop = intent.getBooleanExtra(ImageSelector.NEED_CROP,false);
        setSupportActionBar(mBind.toolbar);
        mBind.toolbar.setBackgroundColor(intent.getIntExtra(ImageSelector.COLOR_BACKGROUND, Color.BLUE));
        mBind.toolbar.setTitleTextColor(intent.getIntExtra(ImageSelector.COLOR_TITLE,Color.WHITE));
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(intent.getStringExtra(ImageSelector.TEXT_TITLE));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
        System.gc();
    }

    @Override
    public void onImagesResult(ArrayList<ThumbImage> thumbImages) {
        ImagesAdapter adapter = new ImagesAdapter(this,thumbImages,this);
        mBind.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onImageClick(String data, ImageView imageView) {
        if(mNeedCrop) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "option");
            Intent intent = getIntent();
            intent.setClass(this, CropActivity.class);
            intent.putExtra(ImageSelector.DATA, data);
            ActivityCompat.startActivityForResult(this, intent, ImageSelector.REQUEST_CODE, optionsCompat.toBundle());
        }else{
            Intent intent = new Intent();
            intent.putExtra(ImageSelector.DATA,data);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void onCameraClick() {
        Logger.i("camera clicked");
        mCameraPhotoName = getExternalCacheDir().getAbsolutePath()+File.separator+MD5Util.getFileName()+".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraPhotoName)));
            intent.putExtra(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
            startActivityForResult(intent,110);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == ImageSelector.REQUEST_CODE){
                Logger.i("data returned by CropActivity: "+data.getStringExtra(ImageSelector.DATA));
                setResult(RESULT_OK,data);
                finish();
            }else if(requestCode == 110){
                Intent intent = getIntent();
                intent.setClass(this, CropActivity.class);
                intent.putExtra(ImageSelector.FROM_ACNERA,true);
                intent.putExtra(ImageSelector.DATA, mCameraPhotoName);
                startActivityForResult(intent,ImageSelector.REQUEST_CODE);
            }
        }
    }
}
