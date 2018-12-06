package ypsiptv.prison.view.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.File;


public abstract class BaseActivity extends Activity {

    private String mTag;
    private String test;
    public static Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activity = this;
        mTag = getClass().getName();
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);


//        sc();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void ld(String msg) {

    }

    protected void lw(String msg) {

    }

    protected void le(String msg) {

    }

    protected void le(String msg, Throwable tr) {
    }

    protected float getDimension(int id) {
        return getResources().getDimension(id);
    }


}
