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

import java.util.Timer;
import java.util.TimerTask;

import ypsiptv.prison.R;
import ypsiptv.prison.model.bean.Cmmond;
import ypsiptv.prison.utils.RsType;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.FULL;

public class ResInsertActivity extends BaseActivity {
    private ImageView chaboimg;
    private VideoView chabovod;
    public static final String PALY = "PALY";
    public static final String PAUSE = "PAUSE";
    public static final String STOP = "STOP";
    public static final String FORWARD = "FORWARD";
    public static final String REWIND = "REWIND";
    public static final String Cancle = "Cancle";

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        initview();
        setvalue();
    }

    Timer timer;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String com = intent.getAction();
            System.out.println(com);
            if (com.equals(PALY)) {
            } else if (com.equals(PAUSE)) {
                try {
                    switch (type) {
                        case 1:

                            break;
                        case 2:
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                            } else {
                                mediaPlayer.start();
                            }
                            break;
                        case 3:
                            if (chabovod.isPlaying()) {
                                chabovod.pause();
                            } else {
                                chabovod.start();
                            }
                            break;
                        case 4:
                            break;
                        default:
                            if (chabovod.isPlaying()) {
                                chabovod.pause();
                            } else {
                                chabovod.start();
                            }
                            break;
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else if (com.equals(STOP)) {
                finish();
            } else if (com.equals(FORWARD)) {

                try {
                    if (timer != null) {
                        timer.cancel();
                    }
                    timer = new Timer();
                    if (cmmond.getType() == 2) {
                        timer.schedule(new TimerTask() {

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
                    if (timer != null) {
                        timer.cancel();
                    }
                    timer = new Timer();
                    if (cmmond.getType() == 2) {
                        timer.schedule(new TimerTask() {

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
                if (timer != null) {
                    timer.cancel();
                }

            }
        }
    };

    Cmmond cmmond;
    int type;

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            cmmond = (Cmmond) getIntent().getSerializableExtra("key");

//			new Req(this, null).addplay(What.addplay, cmmond.getSource_type(),
//					"", cmmond.getId());

            url = cmmond.getPath();

            String temp = url.substring(url.lastIndexOf("."));

            type = RsType.type.get(temp.toLowerCase());

            // System.out.println(type + "-----");

            switch (type) {
                case 1:
                    chabovod.setVisibility(View.GONE);
                    chaboweb.setVisibility(View.GONE);
                    Glide.with(ResInsertActivity.this).load(url).into(chaboimg);
                    break;
                case 2:
                    chaboimg.setVisibility(View.GONE);
                    chabovod.setVisibility(View.GONE);
                    chaboweb.setVisibility(View.GONE);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(getApplicationContext(),
                            Uri.parse(url));
                    mediaPlayer.prepareAsync();
                    break;
                case 3:
                    chaboimg.setVisibility(View.GONE);
                    chaboweb.setVisibility(View.GONE);
                    if (!url.isEmpty()) {
                        play(url);
                    }
                    break;
                case 4:
                    chaboimg.setVisibility(View.GONE);
                    chabovod.setVisibility(View.GONE);

                    playtxt(url);
                    break;
                case 5:
                    chabovod.setVisibility(View.GONE);
                    chaboweb.setVisibility(View.GONE);
                    handle.sendEmptyMessage(80);
                    break;
                default:

                    break;
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    int type5 = 0;

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
                // finish();

            }
        });
        mediaPlayer.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
                // MyToast.makeshow(ResInsertActivity.this,
                // getString(R.string.paly_error), Toast.LENGTH_SHORT);
                return true;
            }
        });
    }

    WebView chaboweb;

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
        chabovod.setOnPreparedListener(new OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
            }
        });
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
                try {
                    // finish();

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        setMediaListene();

    }

    String url;

    private void play(String url) {
        // TODO Auto-generated method stub
        System.out.println(url);
        chabovod.setVideoURI(Uri.parse(url));
        // chabovod.start();
    }

    private void playtxt(String url) {
        // TODO Auto-generated method stub

        chaboweb.loadUrl(url);
        chaboweb.getSettings().setDefaultTextEncodingName("UTF-8");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        try {
            mediaPlayer.release();
            mediaPlayer.stop();
            unregisterReceiver(receiver);

        } catch (Exception e) {
            // TODO: handle exception
        }

        super.onStop();
    }

    Handler handle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    try {
                        switch (type) {
                            case 1:

                                break;
                            case 2:
                                mediaPlayer.seekTo((int) (mediaPlayer
                                        .getCurrentPosition() + mediaPlayer
                                        .getDuration() * 0.05));
                                break;
                            case 3:
                                chabovod.seekTo((int) (chabovod.getCurrentPosition() + chabovod
                                        .getDuration() * 0.05));
                                break;
                            case 4:
                                break;
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    break;
                case 2:

                    try {
                        switch (type) {
                            case 1:

                                break;
                            case 2:

                                mediaPlayer.seekTo((int) (mediaPlayer
                                        .getCurrentPosition() - mediaPlayer
                                        .getDuration() * 0.05));
                                break;
                            case 3:
                                chabovod.seekTo((int) (chabovod.getCurrentPosition() - chabovod
                                        .getDuration() * 0.05));
                                break;
                            case 4:
                                break;
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    break;
                case 3:
                    setvalue();
                    break;

                case 80:

                    Glide.with(ResInsertActivity.this)
                            .load(cmmond.getDetails()[type5]).into(chaboimg);
                    type5++;
                    if (type5 >= cmmond.getDetails().length) {
                        type5 = 0;
                    }
                    handle.sendEmptyMessageDelayed(80, 5 * 1000);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    int currentplay;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        try {
            chabovod.pause();
            currentplay = chabovod.getCurrentPosition();
        } catch (Exception e) {
            // TODO: handle exception
        }
        super.onPause();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(PAUSE);
            filter.addAction(STOP);
            filter.addAction(FORWARD);
            filter.addAction(REWIND);
            filter.addAction(Cancle);
            registerReceiver(receiver, filter);
        } catch (Exception e) {
            // TODO: handle exception
        }

        super.onStart();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        IntentFilter filter = new IntentFilter();
        filter.addAction(PAUSE);
        filter.addAction(STOP);
        filter.addAction(FORWARD);
        filter.addAction(REWIND);
        filter.addAction(Cancle);
        registerReceiver(receiver, filter);

        try {

            switch (type) {
                case 1:

                    break;
                case 2:
                    mediaPlayer.seekTo(currentplay);
                    mediaPlayer.start();
                    break;
                case 3:
                    chabovod.seekTo(currentplay);
                    chabovod.start();
                    break;
                case 4:
                    break;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        super.onRestart();
    }

}
