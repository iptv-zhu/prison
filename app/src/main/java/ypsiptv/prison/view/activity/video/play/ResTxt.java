package ypsiptv.prison.view.activity.video.play;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.VolleyError;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.ResData;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.view.base.BaseActivity;


public class ResTxt extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_web);

        initview();
        setvalue();

    }

    private void setvalue() {
        // TODO Auto-generated method stub

        ResData resData = (ResData) getIntent().getExtras().getSerializable(
                "key");
//		new Req(this, null).play(resData.getId(), What.play);
        msgtitle.setText(resData.getName());

        msgweb.loadUrl(resData.getPath());
        msgweb.getSettings().setDefaultTextEncodingName("GBK");
        // msgweb.getSettings().setDefaultTextEncodingName("UTF-8");
        // msgweb.loadDataWithBaseURL(null, resData.getPath(), "text/html",
        // "utf-8", null);

        System.out.println(resData.getPath());
        req(resData.getId());

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

    WebView msgweb;
    TextView msgtitle;

    private void initview() {
        // TODO Auto-generated method stub

        msgtitle = (TextView) findViewById(R.id.msgtitle);
        msgweb = (WebView) findViewById(R.id.msgweb);
        WebSettings websettings = msgweb.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(true);
        msgweb.setBackgroundColor(Color.TRANSPARENT);
        msgweb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

}
