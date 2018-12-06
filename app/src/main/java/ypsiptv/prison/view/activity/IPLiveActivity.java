package ypsiptv.prison.view.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import ypsiptv.prison.R;
import ypsiptv.prison.model.bean.LiveData;
import ypsiptv.prison.model.bean.LiveList;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.SpUtils;
import ypsiptv.prison.view.adapter.LiveListAdapter;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.FULL;

/**
 * 直播
 */

public class IPLiveActivity extends BaseActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {


    //onCreate生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        config();
        initview();
        setvalue();

    }

    AudioManager audioManager;
    int maxVolume;
    int currentVolume;
    LiveData liveData;

    //初始化数据
    private void config() {
        liveData = (LiveData) getIntent().getSerializableExtra("key");
        livelist = liveData.getLiveList();

        // TODO Auto-generated method stub
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);

    }

    RelativeLayout live_tips;
    TextView live_no_s, live_no_name;
    TextView live_no_b;
    VideoView live_player;

    //初始化组件
    private void initview() {
        live_tips = findViewById(R.id.live_tips);
        live_no_s = findViewById(R.id.live_no_s);
        live_no_name = findViewById(R.id.live_no_name);
        live_no_b = findViewById(R.id.live_no_b);
        live_player = findViewById(R.id.live_player);
        FULL.star(live_player);
        live_player.setOnPreparedListener(this);
        live_player.setOnCompletionListener(this);
        live_player.setOnErrorListener(this);
    }

    List<LiveList> livelist = new ArrayList<>();
    private int historyno = 0;

    //准备播放
    private void setvalue() {

        try {
            historyno = SpUtils.getInt(this, "no", 0);
            play();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    String testurl = "http://192.168.31.250:8105/wisdom_hotel/upload/1.ts";

    //播放
    private void play() {
        // TODO Auto-generated method stub
        try {

            live_tips.setVisibility(View.VISIBLE);
            live_no_s.setText(historyno + 1 + "");
            live_no_name.setText(livelist.get(historyno).getLivemanagementname());

            live_no_b.setText(historyno + 1 + "");
            handler.removeMessages(1);
            handler.sendEmptyMessageDelayed(1, 5*1000);

            if (live_player.isPlaying()) {
                live_player.stopPlayback();
            }
            if (!livelist.isEmpty()) {
                LogUtils.d(livelist.get(historyno).getLivemanagementaddress());
                if (live_player != null && !livelist.isEmpty()) {
                    live_player.setVideoPath(livelist.get(historyno).getLivemanagementaddress());
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private ListView live_list;
    private TextView live_count;
    private PopupWindow popupWindow;
    private View view;

    //显示节目列表
    private void show() {
        // TODO Auto-generated method stub
        try {
            view = getLayoutInflater().inflate(R.layout.pop_live, null);
            popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupWindow.dismiss();
                }
            });
            live_list = view.findViewById(R.id.live_list);
            live_count = view
                    .findViewById(R.id.live_count);
            live_list.setAdapter(new LiveListAdapter(this, livelist));
            live_count.setText(getString(R.string.live_count).replace("X", livelist.size() + ""));
            live_list.setSelectionFromTop(historyno, 220);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            live_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> av, View v,
                                        int position, long id) {
                    historyno = position;
                    play();
                }
            });
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 10 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新界面
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    break;
                case 1:
                    live_tips.setVisibility(View.GONE);
                    live_no_b.setText("");
                    break;
                case 2:
                    try {
                        int tmp = Integer.parseInt(cutno) - 1;
                        cutno = "";
                        if (tmp >= 0 && tmp < livelist.size()) {
                            historyno = tmp;
                            play();

                        } else {
//                            AppTool.toast(IPLiveActivity.this, getString(R.string.live_none), 0, Gravity.CENTER, 0, 0);
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                        cutno = "";
                    }
                    break;
            }
        }
    };
    String cutno = "";

    //按键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub


        if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            cutno += keyCode - 7;
            live_no_b.setText(cutno);

//            handler.removeMessages(2);
//            handler.sendEmptyMessageDelayed(2, 2 * 1000);

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
                || keyCode == KeyEvent.KEYCODE_ENTER) {
            show();
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                downvol();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                upvol();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                upchanle();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                downchanle();
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                pause();
                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    //播放完成监听
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    //播放异常监听
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//        AppTool.toast(this, getString(R.string.play_error), 0, Gravity.CENTER, 0, 0);
        Toast.makeText(IPLiveActivity.this,"播放异常",Toast.LENGTH_SHORT).show();
        return true;
    }

    //准备播放监听
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    //保存频道
    @Override
    protected void onDestroy() {
        try {
            SpUtils.putInt(this, "no", historyno);
            if (live_player.isPlaying()) {
                live_player.stopPlayback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    //上一个频道
    private void upchanle() {
        // TODO Auto-generated method stub
        try {
            if (historyno < livelist.size() - 1) {
                historyno += 1;
            } else {
                historyno = 0;
            }
            play();


        } catch (Exception e) {
            // TODO: handle exception

        }
    }

    //下一个频道
    private void downchanle() {
        // TODO Auto-generated method stub
        try {
            if (historyno > 0) {
                historyno -= 1;
            } else {
                historyno = livelist.size() - 1;
            }
            play();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //增加音量
    private void upvol() {
        // TODO Auto-generated method stub
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND
                        | AudioManager.FLAG_SHOW_UI);
    }

    //减少音量
    private void downvol() {
        // TODO Auto-generated method stub
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND
                        | AudioManager.FLAG_SHOW_UI);
    }

    //暂停
    private void pause() {
        // TODO Auto-generated method stub
        try {
            if (live_player.isPlaying()) {
                live_player.pause();
            } else {
                live_player.start();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
    }

    //触摸监听
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            show();
        }
        return super.onTouchEvent(event);
    }
}
