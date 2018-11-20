package ypsiptv.prison.view.activity;

import android.os.Bundle;


import ypsiptv.prison.R;
import ypsiptv.prison.view.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void loadData() {

    }

    @Override
    public int getContentId() {
        return 0;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
