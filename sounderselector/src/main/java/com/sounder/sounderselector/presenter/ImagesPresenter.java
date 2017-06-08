package com.sounder.sounderselector.presenter;

import android.content.Context;

import com.sounder.sounderselector.javabean.ThumbImage;
import com.sounder.sounderselector.model.ImageModelImp;
import com.sounder.sounderselector.model.ImagesModel;
import com.sounder.sounderselector.util.Logger;
import com.sounder.sounderselector.view.view.ImagesView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *  Created by sounder on 2017/6/2.
 */

public class ImagesPresenter {
    private ImagesView mView;
    private ImagesModel mModel;

    public ImagesPresenter(ImagesView view){
        this.mView = view;
        mModel = new ImageModelImp();
    }
    public void getImages(Context context){
        Observable.just(context)
                .map(new Function<Context, ArrayList<ThumbImage>>() {
                    @Override
                    public ArrayList<ThumbImage> apply(Context context) throws Exception {
                        return mModel.getImages(context);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<ThumbImage>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onNext(ArrayList<ThumbImage> value) {
                        mView.onImagesResult(value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        Logger.i("get image complete");
                    }
                });
    }
}
