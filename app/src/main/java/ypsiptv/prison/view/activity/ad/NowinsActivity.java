package ypsiptv.prison.view.activity.ad;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import ypsiptv.prison.R;
import ypsiptv.prison.utils.RsType;
import ypsiptv.prison.view.activity.ad.bean.Command;
import ypsiptv.prison.view.activity.ad.bean.Play;
import ypsiptv.prison.view.activity.ad.bean.Sources;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.FULL;
//即时插播


public class NowinsActivity extends BaseActivity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    static Activity activity;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    Command command;

    //生命周期onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowins);
        activity = this;
        initview();
        setvalue();

    }

    int type = 0;
    String resurl;
    Play play;

    //检测播放资源
    private void setvalue() {
        command = (Command) getIntent().getExtras().get("key");
        System.out.println(command.getCommand());
        if (command != null) {
            play = command.getPlay();
            type = play.getStype();
            switch (type) {
                case 2://直播
                    AllGonn();
                    msgvideo.setVisibility(View.VISIBLE);
                    msgname.setText("正在插播" + play.getName());
                    resurl = play.getSurl();
                    msgvideo.setVideoPath(resurl);
                    System.out.println(resurl + "*************直播");
                    break;
                case 3://点播
                    Vod();
                    break;
                case 4://临时文件
                    msgname.setText("正在插播" + play.getSname());
                    resurl = play.getSurl();
                    OtherRes();
                    break;
            }
        }
    }

    //其他资源
    private void OtherRes() {
        try {
            String temp = resurl.substring(resurl.lastIndexOf(".")).toLowerCase();
            System.out.println(resurl + "*************" + temp);
            type = RsType.type.get(temp);
            AllGonn();
            switch (type) {
                case 1://ResImage
                    msgimg.setVisibility(View.VISIBLE);
                    playimg();
                    //                next();
                    break;
                case 2://ResAudio
                    msgvideo.setVisibility(View.VISIBLE);
                    playvideo();
                    break;
                case 3://ResVideo
                    msgvideo.setVisibility(View.VISIBLE);
                    playvideo();
                    break;
                case 4://ResTxt
                    System.out.println("====================");
                    msgweb.setVisibility(View.VISIBLE);
                    playweb();
                    break;
                case 5://ResOffice
                    msgimg.setVisibility(View.VISIBLE);
                    playweb();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Sources sources;
    int cursource;

    //资源
    private void Vod() {
        try {
            sources = play.getSource();
            msgname.setText("正在插播" +  sources.getName());
            resurl = sources.getDetails().get(0).getFilePath();
            String temp = resurl.substring(resurl.lastIndexOf(".")).toLowerCase();
            System.out.println(resurl + "*************" + temp);
            type = RsType.type.get(temp);

            AllGonn();
            switch (type) {
                case 1://ResImage
                    msgimg.setVisibility(View.VISIBLE);
                    playimg();
                    //                next();
                    break;
                case 2://ResAudio
                    msgvideo.setVisibility(View.VISIBLE);
                    playvideo();
                    break;
                case 3://ResVideo
                    msgvideo.setVisibility(View.VISIBLE);
                    playvideo();
                    break;
                case 4://ResTxt
                    msgweb.setVisibility(View.VISIBLE);
                    playweb();
                    break;
                case 5://ResOffice
                    msgimg.setVisibility(View.VISIBLE);
                    playweb();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //播放网页
    private void playweb() {
        msgweb.loadUrl(resurl);
    }

    //播放影片
    private void playvideo() {
        msgvideo.setVideoPath(resurl);
    }

    //播放图片
    private void playimg() {
        Glide.with(this).load(resurl).into(msgimg);
    }

    //播放下一个
    private void next() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (cursource < mings.getSources().size() - 1) {
//                    cursource++;
//                } else {
//                    cursource = 0;
//                }
//                OtherRes();
//            }
//        }, 5 * 1000);
    }

    //隐藏组件
    private void AllGonn() {
        msgimg.setVisibility(View.GONE);
        msgvideo.setVisibility(View.GONE);
        msgweb.setVisibility(View.GONE);

    }


    static VideoView msgvideo;
    ImageView msgimg;
    TextView msgname;
    WebView msgweb;

    //初始化
    private void initview() {
        // TODO Auto-generated method stub
        msgvideo = (VideoView) findViewById(R.id.msgvideo);
        FULL.star(msgvideo);
        msgname = (TextView) findViewById(R.id.msgname);
        MediaController controller = new MediaController(this);
        msgvideo.setMediaController(controller);
        msgimg = (ImageView) findViewById(R.id.msgimg);
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
        msgweb.getSettings().setDefaultTextEncodingName("GBK");


        msgvideo.setOnPreparedListener(this);
        msgvideo.setOnCompletionListener(this);
        msgvideo.setOnErrorListener(this);
    }

    //播放完成监听
    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub
//        if (!mings.getSources().isEmpty()) {
//            if (cursource < mings.getSources().size() - 1) {
//                cursource++;
//            } else {
//                cursource = 0;
//            }
//            OtherRes();
//        }

    }

    //播放错误监听
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        return true;
    }

    //准备播放监听
    @Override
    public void onPrepared(MediaPlayer mp) {
        // TODO Auto-generated method stub
        mp.start();
    }

    //返回键监听
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    //按鍵监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    public static void exit() {
        if (activity != null) {
            activity.finish();
        }

    }


}
