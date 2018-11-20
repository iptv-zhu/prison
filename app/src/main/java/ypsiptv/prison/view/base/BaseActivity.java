package ypsiptv.prison.view.base;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;



public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private String mTag;
    private String test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mTag = getClass().getName();
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        setContentView(getContentId());


        ld("Activity_Life_Method --- >> onCreate");
        initView(savedInstanceState);
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ld("Activity_Life_Method --- >> onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ld("Activity_Life_Method --- >> onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ld("Activity_Life_Method --- >> onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        ld("Activity_Life_Method --- >> onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ld("Activity_Life_Method --- >> onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ld("Activity_Life_Method --- >> onDestroy");
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
