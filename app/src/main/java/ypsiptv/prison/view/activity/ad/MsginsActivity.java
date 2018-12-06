package ypsiptv.prison.view.activity.ad;

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
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.utils.RsType;
import ypsiptv.prison.view.activity.ad.bean.Mings;
import ypsiptv.prison.view.activity.ad.bean.MsgSgLives;
import ypsiptv.prison.view.activity.ad.bean.Sources;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.FULL;

public class MsginsActivity extends BaseActivity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    MyApp app;
    Mings mings;
    MsgSgLives msgSgLives;
    Sources sources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgins);
        mings = (Mings) getIntent().getSerializableExtra("key");

        initview();

        setvalue();
        stop();
    }

    int cursource = 0;

    private void setvalue() {
        // TODO Auto-generated method stub
        try {


            String path = "";
            if (!mings.getMsgSgLives().isEmpty()) {
                AllGonn();
                msgvideo.setVisibility(View.VISIBLE);
                msgSgLives = mings.getMsgSgLives().get(0);
                msgname.setText("正在插播" + msgSgLives.getLivesingle().getName());
                msgvideo.setVideoPath(msgSgLives.getLivesingle().getAddress());
                System.out.println(msgSgLives.getLivesingle().getAddress() + "*************直播");
            } else if (!mings.getSources().isEmpty()) {
                OtherRes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String resurl;
    int type;

    private void OtherRes() {
        try {
            sources = mings.getSources().get(cursource);
            msgname.setText( "正在插播" + sources.getName());
            resurl = sources.getDetails().get(0).getFilePath();
            String temp = resurl.substring(resurl.lastIndexOf(".")).toLowerCase();
            System.out.println(resurl + "*************" + temp);
            type = RsType.type.get(temp);

            AllGonn();
            switch (type) {
                case 1://ResImage
                    msgimg.setVisibility(View.VISIBLE);
                    playimg();
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

    private void playweb() {
        msgweb.loadUrl(resurl);
    }


    private void playvideo() {
        msgvideo.setVideoPath(resurl);
    }


    private void playimg() {
        Glide.with(this).load(resurl).into(msgimg);
        next();
    }

    private void next() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cursource < mings.getSources().size() - 1) {
                    cursource++;
                } else {
                    cursource = 0;
                }
                OtherRes();
            }
        }, 5 * 1000);
    }


    private void AllGonn() {
        msgimg.setVisibility(View.GONE);
        msgvideo.setVisibility(View.GONE);
        msgweb.setVisibility(View.GONE);

    }


    VideoView msgvideo;
    ImageView msgimg;
    TextView msgname;
    WebView msgweb;

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

    private void stop() {
        long end = mings.getEndTime();
        long cur = System.currentTimeMillis();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, end - cur);

    }



    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub
        if (!mings.getSources().isEmpty()) {
            if (cursource < mings.getSources().size() - 1) {
                cursource++;
            } else {
                cursource = 0;
            }
            OtherRes();
        }

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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}
