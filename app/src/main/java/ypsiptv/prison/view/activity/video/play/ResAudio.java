package ypsiptv.prison.view.activity.video.play;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.VolleyError;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.ResData;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.FULL;


public class ResAudio extends BaseActivity implements OnPreparedListener,
        OnErrorListener, OnCompletionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R
                .layout.res_audio);



        initview();

        setvalue();
    }

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            ResData resData = (ResData) getIntent().getExtras()
                    .getSerializable("key");
            res_audio_title.setText(resData.getName());
            res_audio.setVideoURI(Uri.parse(resData.getPath()));
            System.out.println(resData.getPath());
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

    VideoView res_audio;
    TextView res_audio_title;

    private void initview() {
        // TODO Auto-generated method stub
        res_audio = (VideoView) findViewById(R.id.res_audio);
        FULL.star(res_audio);
        MediaController controller = new MediaController(this);
        res_audio.setMediaController(controller);
        res_audio_title = (TextView) findViewById(R.id.res_audio_title);
        res_audio.setOnPreparedListener(this);
        res_audio.setOnCompletionListener(this);
        res_audio.setOnErrorListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub
        finish();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // TODO Auto-generated method stub
        mp.start();
    }

}
