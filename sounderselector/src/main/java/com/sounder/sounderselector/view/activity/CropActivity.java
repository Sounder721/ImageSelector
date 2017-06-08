package com.sounder.sounderselector.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.sounder.sounderselector.ImageSelector;
import com.sounder.sounderselector.R;
import com.sounder.sounderselector.constant.BaseActivity;
import com.sounder.sounderselector.databinding.ActivityCropBinding;
import com.sounder.sounderselector.util.Logger;
import com.sounder.sounderselector.util.MD5Util;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CropActivity extends BaseActivity implements View.OnClickListener{
    private ActivityCropBinding mBind;
    private String mData;
    private Bitmap.CompressFormat mFormat;
    private int mQuality;
    private ProgressDialog mDialog;
    private boolean mFromCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this,R.layout.activity_crop);
        parseIntent();
        init();
    }

    @Override
    protected void onDestroy() {
        try{
            mBind.fl.destroyDrawingCache();
        }catch (Exception e){
            Logger.e(e.getMessage());
        }
        super.onDestroy();
    }
    private void parseIntent(){
        Intent intent = getIntent();
        mData = intent.getStringExtra("data");
        if(TextUtils.isEmpty(mData)) {
            setResult(RESULT_CANCELED);
            finish();
        }
        int w = intent.getIntExtra(ImageSelector.CROP_WIDTH,0);
        int h = intent.getIntExtra(ImageSelector.CROP_HEIGHT,0);
        mBind.cropView.setSize(w,h);
        mFormat = (Bitmap.CompressFormat) intent.getSerializableExtra(ImageSelector.FORMAT);
        mQuality = intent.getIntExtra(ImageSelector.QUALITY,90);
        if(mQuality > 100){
            mQuality = 100;
        }
        int color = intent.getIntExtra(ImageSelector.COLOR_BACKGROUND,Color.GREEN);
        mBind.cropView.setStrokeColor(color);

        mFromCamera = intent.getBooleanExtra(ImageSelector.FROM_ACNERA,false);
    }
    private void init(){
        mBind.btnConfirm.setOnClickListener(this);
        mBind.btnClose.setOnClickListener(this);

        ViewCompat.setTransitionName(mBind.image,"option");
        Glide.with(this).load(mData).into(mBind.image);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在处理...");
    }
    private Bitmap convertViewToBitmap(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }
    private Bitmap convertViewToBitmap(){
        View view = mBind.fl;
        Logger.i("FromCamera....");
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }


    private void crop(){
        final Rect rect = mBind.cropView.getRect();
        Bitmap bitmap;
        if(mFromCamera){
            bitmap = convertViewToBitmap();
        }else{
            bitmap = convertViewToBitmap(mBind.fl);
        }
        mDialog.show();
        Observable.just(bitmap)
                .map(new Function<Bitmap, String>() {
                    @Override
                    public String apply(Bitmap bitmap) throws Exception {
                        bitmap = Bitmap.createBitmap(bitmap,rect.left,rect.top,rect.right,rect.bottom);
                        String ext;
                        switch (mFormat){
                            case JPEG:
                            case WEBP:
                                ext = ".jpg";
                                break;
                            case PNG:
                            default:
                                ext = ".png";
                                break;
                        }
                        String fileName = MD5Util.getFileName()+ext;
                        String parent = getExternalCacheDir().getAbsolutePath()+File.separator;
                        File file = new File(parent+fileName);
                        Logger.i(file.getAbsolutePath());
                        if(file.exists()){
                            file.delete();
                        }
                        if(file.createNewFile()){
                            Logger.i("create file successful");
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(mFormat,mQuality,out);
                            out.flush();
                            out.close();
                            Logger.i("write file successful");
                        }else{
                            Logger.i("create file failed");
                        }
                        return file.getAbsolutePath();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(String value) {
                        complete(value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mDialog.dismiss();
                        Logger.e(e.getMessage());
                        Toast.makeText(CropActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {
                        mDialog.dismiss();
                        mBind.fl.destroyDrawingCache();
                    }
                });
    }
    protected void complete(String path){
        Intent intent =  new Intent();
        intent.putExtra(ImageSelector.DATA,path);
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public void onClick(View v) {
        if(v == mBind.btnConfirm){
//            crop();
            permission();
        }else if(v == mBind.btnClose){
            onBackPressed();
        }
    }
    private void permission(){
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Logger.e("no access,requesting...");
            ActivityCompat.requestPermissions(this, permissions, 321);
        }else{
            Logger.i("already has the permission");
            crop();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 321){
            try{
                String perm = permissions[0];
                if(perm.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        crop();
                    }
                }
            }catch (Exception e){
                Logger.e(e.getMessage());
            }
        }
    }
}
