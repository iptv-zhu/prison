package ypsiptv.prison.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import ypsiptv.prison.R;
import ypsiptv.prison.view.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
