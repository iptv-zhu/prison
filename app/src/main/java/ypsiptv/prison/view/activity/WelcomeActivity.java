package ypsiptv.prison.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.AddeList;
import ypsiptv.prison.model.bean.WelcomData;
import ypsiptv.prison.model.bean.gSon;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.FULL;

public class WelcomeActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setMediaListene();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                //获取ConnectivityManager对象对应的NetworkInfo对象
                // 获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取以太网连接的信息
                NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                if (wifiNetworkInfo.isConnected() || networkInfo.isConnected()) {
                    Toast.makeText(context, "网络连接成功", Toast.LENGTH_SHORT).show();
                    loadData();
                } else if (!networkInfo.isConnected() && !wifiNetworkInfo.isConnected()) {
                    Toast.makeText(context, "网络异常,请检查网络", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };


    private TextView welcome_tips;
    private ImageView welcome_image;
    private VideoView welcome_video;
    private TextView welcome_time_tips;


    private void initView() {
        welcome_tips = findViewById(R.id.welcome_tips);
        welcome_image = findViewById(R.id.welcome_image);
        welcome_video = findViewById(R.id.welcome_video);
        FULL.star(welcome_video);
        welcome_time_tips = findViewById(R.id.welcome_time_tips);
        welcome_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playvideo();
            }
        });
        welcome_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        welcome_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return true;
            }
        });
    }


    private void loadData() {
        adDetailAction_getAllAdDetail();
    }


    private void adDetailAction_getAllAdDetail() {
        String api="adDetailAction_getAllAdDetail";
        String url = MyApp.requrl("adDetailAction_getAllAdDetail", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<List<WelcomData>> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<List<WelcomData>>>() {
                                    }.getType());
                            if (data.getData().isEmpty())
                                return;
                            addeList = data.getData().get(0).getAddeList();
                            if (addeList.isEmpty())
                                return;

                            for (AddeList ad : addeList) {
                                WELCOME_FINISH += ad.getInter();
                            }
                            handler.sendEmptyMessage(PlayWelcomead);
                            handler.sendEmptyMessage(WELCOME_TIMEOUT);
                        } catch (Exception e) {
                            e.printStackTrace();
                            gSon<Object> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<Object>>() {
                                    }.getType());
                            welcome_tips.setText(data.getMsg()+"\n设备号："+MyApp.mac);
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                        welcome_tips.setText("连接服务器超时");
                    }
                }, false);
    }

    public int WELCOME_FINISH;
    private List<AddeList> addeList;
    final int WELCOME_TIMEOUT = 0;
    final int PlayWelcomead = 1;
    int cutad;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WELCOME_TIMEOUT:
                    if (WELCOME_FINISH == 0) {
                        tomain();
                    } else if (WELCOME_FINISH > 0) {
                        welcome_time_tips.setVisibility(View.VISIBLE);
                        welcome_time_tips.setText(WELCOME_FINISH + "");
                        WELCOME_FINISH--;
                        handler.sendEmptyMessageDelayed(WELCOME_TIMEOUT, 1 * 1000);
                    }
                    break;
                case PlayWelcomead:
                    try {
                        switch (addeList.get(cutad).getType()) {
                            case 1:
                                playimg();
                                playmusic();
                                break;
                            case 2:
                                playvideo();
                                break;
                        }
                        if (cutad < addeList.size() - 1) {
                            handler.sendEmptyMessageDelayed(PlayWelcomead, addeList.get(cutad).getInter() * 1000);
                            cutad++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };


    private void playimg() {
        try {
            if (welcome_video.getVisibility() == View.VISIBLE) {
                welcome_video.setVisibility(View.GONE);
                if (welcome_video.isPlaying()) {
                    welcome_video.stopPlayback();
                }
            }
            if (welcome_image.getVisibility() == View.GONE) {
                welcome_image.setVisibility(View.VISIBLE);
            }
            String path = addeList.get(cutad).getPath();
            String temp = path.substring(path.lastIndexOf(".")).toLowerCase();
            if ("gif".equals(temp)) {
                Glide.with(this).load(path).asGif().into(welcome_image);
            } else {
                Glide.with(this).load(path).into(welcome_image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer mediaPlayer;

    private void setMediaListene() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playmusic();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return true;
            }
        });
    }

    private void playmusic() {
        // TODO Auto-generated method stub

        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(WelcomeActivity.this,
                    Uri.parse(addeList.get(cutad).getBgMusic()));
            mediaPlayer.prepareAsync();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private void playvideo() {
        try {
            if (welcome_image.getVisibility() == View.VISIBLE) {
                welcome_image.setVisibility(View.GONE);
            }
            if (welcome_video.getVisibility() == View.GONE) {
                welcome_video.setVisibility(View.VISIBLE);
            }
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            welcome_video.setVideoURI(Uri.parse(addeList.get(cutad).getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void tomain() {
        finish();
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
