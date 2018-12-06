package ypsiptv.prison.view.activity.video.play;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.ResData;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.view.base.BaseActivity;


public class ResImage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.res_image);


        initview();

        setvalue();
    }

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            ResData resData = (ResData) getIntent().getExtras()
                    .getSerializable("key");
            res_image_title.setText(resData.getName());
            Glide.with(this).load(resData.getPath()).into(res_image);
            req(resData.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void req(int id) {
        String api = "getFileAction_addPlayC";
        String url = MyApp.requrl("getFileAction_addPlayC", "id=" + id);
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                    }
                }, false);


    }

    ImageView res_image;
    TextView res_image_title;

    private void initview() {
        // TODO Auto-generated method stub
        res_image = (ImageView) findViewById(R.id.res_image);
        res_image_title = (TextView) findViewById(R.id.res_image_title);
    }

}