package ypsiptv.prison.view.activity.ad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ypsiptv.prison.R;
import ypsiptv.prison.model.bean.Cmmond;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.RsType;
import ypsiptv.prison.view.activity.ad.bean.InsertAd;
import ypsiptv.prison.view.activity.ad.bean.MipTrDetial;
import ypsiptv.prison.view.activity.ad.bean.MipdList;
import ypsiptv.prison.view.activity.ad.bean.SourceDetials;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.FULL;

//计划插播
public class PlanActivity extends BaseActivity {
    private ImageView chaboimg;
    private VideoView chabovod;
    private Timer timer;
    private int localvideo;
    public static final String PALY = "PALY";
    public static final String PAUSE = "PAUSE";
    public static final String STOP = "STOP";
    public static final String FORWARD = "FORWARD";
    public static final String REWIND = "REWIND";
    public static final String Cancle = "Cancle";

    //广播监听
    Timer timerv;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String com = intent.getAction();
            System.out.println(com);
            if (com.equals(PALY)) {
            } else if (com.equals(PAUSE)) {
                try {
                    if (chabovod.isPlaying()) {
                        chabovod.pause();
                    } else {
                        chabovod.start();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else if (com.equals(STOP)) {
                finish();
            } else if (com.equals(FORWARD)) {

                try {
                    if (timerv != null) {
                        timerv.cancel();
                    }
                    timerv = new Timer();
                    if (cmmond.getType() == 2) {
                        timerv.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                handle.sendEmptyMessage(1);

                            }
                        }, 0, 1 * 1000);

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e.toString());
                }

            } else if (com.equals(REWIND)) {
                try {
                    if (timerv != null) {
                        timerv.cancel();
                    }
                    timerv = new Timer();
                    if (cmmond.getType() == 2) {
                        timerv.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                handle.sendEmptyMessage(2);
                            }
                        }, 0, 1 * 1000);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e.toString());
                }
            } else if (com.equals(Cancle)) {
                if (timerv != null) {
                    timerv.cancel();
                }

            }
        }
    };

    //更新进度
    Cmmond cmmond;
    Handler handle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    chabovod.seekTo((int) (chabovod.getCurrentPosition() + chabovod
                            .getDuration() * 0.05));
                    break;
                case 2:
                    chabovod.seekTo((int) (chabovod.getCurrentPosition() - chabovod
                            .getDuration() * 0.05));
                    break;
                case 3:
                    setvalue();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //onCreate生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        LogUtils.d("开始插播广告...");
        insertAd = (InsertAd) getIntent().getSerializableExtra("key");
        initview();
        setvalue();
        isstop();
        reg();
//		new Req(this, null).addplay(What.addplay, 5, "", app.getInsertAd()
//				.getId());

    }

    InsertAd insertAd;

    //停止计划插播
    private void isstop() {
        // TODO Auto-generated method stub

        System.out.println((insertAd.getEnd() -insertAd
                .getStart()) / 1000 + "秒后停止-----------------------");

        handler.sendEmptyMessageDelayed(0, insertAd.getEnd()
                - insertAd.getStart());
    }

    int local;
    int local2;
    List<MipdList> ad;
    List<MipdList> mipdLists;

    List<MipTrDetial> mipTrDetials;
    List<SourceDetials> sourceDetials;
    String liveAdd;

    //检测播放资源
    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            System.out.println("setvalue***********类型"
                    +insertAd.getType() + "**********当前位置" + local);

            switch (insertAd.getType()) {

                case 1:// 文件
                    sourceDetials = insertAd.getSourceDetials();
                    if (!sourceDetials.isEmpty()) {
                        if (local > sourceDetials.size() - 1) {
                            local = 0;
                        }
                        handler.sendEmptyMessage(88);

                    }

                    break;
                case 2:// 用户上传
                    mipdLists = insertAd.getMipdList();
                    if (local > mipdLists.size() - 1) {
                        local = 0;
                    }
                    handler.sendEmptyMessage(88);
                    break;
                case 3:// 直播
                    liveAdd = insertAd.getLiveAdd();
                    handler.sendEmptyMessage(88);
                    break;
                case 4:// 录播资源
                    mipTrDetials = insertAd.getMipTrDetials();
                    if (local > mipTrDetials.size() - 1) {
                        local = 0;
                    }
                    handler.sendEmptyMessage(88);

                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            handle.sendEmptyMessage(0);
        }
    }

    //直播
    private void oncreatelive(String livepath) {
        // TODO Auto-generated method stub
        chaboimg.setVisibility(View.GONE);
        chaboweb.setVisibility(View.GONE);
        chabovod.setVisibility(View.VISIBLE);
        chabovod.setVideoURI(Uri.parse(livepath));
        if (chabovod.isPlaying()) {
            chabovod.stopPlayback();
        }

    }

    //音乐
    private void playmusic() {
        // TODO Auto-generated method stub
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getApplicationContext(),
                    Uri.parse(localurl));
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //文本
    private void playtxt() {
        // TODO Auto-generated method stub
        chaboimg.setVisibility(View.GONE);
        chabovod.setVisibility(View.GONE);
        chaboweb.setVisibility(View.VISIBLE);
        chaboweb.loadUrl(localurl);
        chaboweb.getSettings().setDefaultTextEncodingName("GBK");
        handler.sendEmptyMessageDelayed(99, jiange);
    }

    //视频
    private void playvideo() {
        // TODO Auto-generated method stub
        chaboimg.setVisibility(View.GONE);
        chaboweb.setVisibility(View.GONE);
        chabovod.setVisibility(View.VISIBLE);
        chabovod.setVideoURI(Uri.parse(localurl));
        chabovod.start();
    }

    WebView chaboweb;

    //初始化组件
    private void initview() {
        // TODO Auto-generated method stub
        chaboimg = (ImageView) findViewById(R.id.chaboimg);
        chabovod = (VideoView) findViewById(R.id.chabovod);
        chaboweb = (WebView) findViewById(R.id.chaboweb);
        WebSettings websettings = chaboweb.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(true);
        chaboweb.setBackgroundColor(Color.TRANSPARENT);
        chaboweb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        FULL.star(chabovod);
        // chabovod.setOnPreparedListener(new OnPreparedListener() {
        //
        // @Override
        // public void onPrepared(MediaPlayer mp) {
        // // TODO Auto-generated method stub
        // mp.start();
        // }
        // });
        chabovod.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
                handle.sendEmptyMessageDelayed(3, 5 * 1000);
                return true;
            }
        });
        chabovod.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                setvalue();
            }
        });
        setMediaListene();
    }

    int type;
    String localurl;
    int jiange = 15 * 1000;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            try {
                switch (msg.what) {

                    case 0:
                        try {
                            System.out.println("STOP----------STOP");
                            finish();
                            timer.cancel();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        break;
                    case 1:
                        chabovod.setVisibility(View.GONE);
                        chaboweb.setVisibility(View.GONE);
                        chaboimg.setVisibility(View.VISIBLE);
                        System.out.println(localurl);

                        Glide.with(PlanActivity.this).load(localurl)
                                .into(chaboimg);
                        handler.sendEmptyMessageDelayed(99, jiange);
                        break;

                    case 88:
                        switch (insertAd.getType()) {
                            case 1:
//						System.out.println(local + "----------");
                                localurl = sourceDetials.get(local).getPath();

                                if (localurl == null) {
                                    localurl = sourceDetials.get(local).getSubFiles()
                                            .get(local2);
                                }
                                if (sourceDetials.get(local).getSubFiles() != null) {
                                    // ppt、pdf
                                    local2++;
                                    System.out
                                            .println(local2
                                                    + "-----"
                                                    + sourceDetials.get(local)
                                                    .getSubFiles().size());
                                    if (local2 <= sourceDetials.get(local).getSubFiles()
                                            .size() - 1) {
                                        return;
                                    }
                                    local2 = 0;
                                }
                                break;
                            case 2:
                                localurl = mipdLists.get(local).getPath();
                                break;
                            case 3:
                                localurl = insertAd.getLiveAdd();
                                break;
                            case 4:
                                localurl = mipTrDetials.get(local).getPath();
                                break;
                            default:
                                break;
                        }

                        if (insertAd.getType() != 3) {
                            String temp = localurl.substring(localurl
                                    .lastIndexOf("."));
                            type = RsType.type.get(temp.toLowerCase());

                            System.out.println(localurl + "地址-----------" + temp
                                    + "类型" + type);
                            switch (type) {
                                case 1:
                                    handler.sendEmptyMessage(1);
                                    break;
                                case 2:
                                    playmusic();
                                    break;
                                case 3:
                                    playvideo();
                                    break;
                                case 4:
                                    playtxt();
                                    break;
                            }
                        } else {
                            playvideo();
                        }


                        local++;
                        break;
                    case 99:
                        setvalue();
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                // System.out.println(e.toString());
            }
        }

    };

    //注册广播
    private void reg() {
        // TODO Auto-generated method stub
        IntentFilter filter = new IntentFilter();
        filter.addAction(PAUSE);
        filter.addAction(STOP);
        filter.addAction(FORWARD);
        filter.addAction(REWIND);
        filter.addAction(Cancle);
        registerReceiver(receiver, filter);
    }

    //按键监听屏蔽
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return true;
    }

    MediaPlayer mediaPlayer;

    private void setMediaListene() {
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    mediaPlayer.release();
                    mediaPlayer.stop();
                    setvalue();

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        mediaPlayer.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
//                AppTool.toast(PlanActivity.this, getString(R.string.play_error), 0, Gravity.CENTER, 0, 0);
                return true;
            }
        });
    }

    //onDestroy生命周期
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        try {
            chabovod.stopPlayback();
            mediaPlayer.release();
            mediaPlayer.stop();
            unregisterReceiver(receiver);
        } catch (Exception e) {
            // TODO: handle exception
        }

        super.onDestroy();
    }

    int currentplay;

    //onPause生命周期
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        try {
            chabovod.pause();
            // mediaPlayer.pause();
            currentplay = chabovod.getCurrentPosition();

        } catch (Exception e) {
            // TODO: handle exception
        }
        super.onPause();
    }

    //onRestart生命周期
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        try {
            chabovod.seekTo(currentplay);
            chabovod.start();
        } catch (Exception e) {
            // TODO: handle exception
        }

        super.onRestart();
    }

}
