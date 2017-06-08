package com.sounder.sounderselector.constant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sounder.sounderselector.ImageSelector;

/**
 *  Created by sounder on 2017/6/3.
 */
public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void initWindow(){
        Intent intent = getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
        {
            Window window = getWindow();
            if(intent!=null) {
                window.setStatusBarColor(intent.getIntExtra(ImageSelector.COLOR_BACKGROUND, Color.BLUE));
            }
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
