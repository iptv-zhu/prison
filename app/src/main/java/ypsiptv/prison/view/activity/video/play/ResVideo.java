package ypsiptv.prison.view.activity.video.play;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.VolleyError;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.ResData;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.SpUtils;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.FULL;


public class ResVideo extends BaseActivity implements OnPreparedListener,
        OnErrorListener, OnCompletionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.res_video);


        initview();

        setvalue();
    }

    ResData resData;

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            resData = (ResData) getIntent().getExtras().getSerializable("key");
            res_video_title.setText(resData.getName());

            vodtime = SpUtils.getInt(this, "resvideo" + resData.getId(), -1);
            if (vodtime / 1000 <= 0) {
                res_video.setVideoPath(resData.getPath());

                req(resData.getId());
            } else {
                crt(vodtime);
            }


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


    VideoView res_video;
    TextView res_video_title;

    private void initview() {
        // TODO Auto-generated method stub
        res_video = (VideoView) findViewById(R.id.res_video);
        FULL.star(res_video);

        MediaController controller = new MediaController(this);
        res_video.setMediaController(controller);


        res_video_title = (TextView) findViewById(R.id.res_video_title);

        res_video.setOnPreparedListener(this);
        res_video.setOnCompletionListener(this);
        res_video.setOnErrorListener(this);

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

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        setdown();
        super.onPause();
    }

    private void setdown() {
        // TODO Auto-generated method stub
        System.out.println("getDuration:" + res_video.getDuration());

        System.out.println("getCurrentPosition:" + res_video.getCurrentPosition());

        if (res_video.getCurrentPosition() > 0
                && res_video.getCurrentPosition() < res_video.getDuration()) {
            SpUtils.getInt(this, "resvideo" + resData.getId(),
                    res_video.getCurrentPosition());
        } else {
            SpUtils.remove(this, "resvideo" + res_video.getId());
        }
    }

    AlertDialog vod_time_dialog;
    int vodtime = 0;

    public void crt(final int vodtime) {
        // TODO Auto-generated method stub

        vod_time_dialog = new AlertDialog.Builder(this).create();
        // update_dialog.setCancelable(false);
        if (vod_time_dialog != null && vod_time_dialog.isShowing()) {
            vod_time_dialog.dismiss();
        } else {
            vod_time_dialog.show();
        }
        vod_time_dialog.setContentView(R.layout.vod_time_dialog);

        TextView update_content = (TextView) vod_time_dialog
                .findViewById(R.id.update_content);

        update_content.setText(getString(R.string.video_time).replace("x", getTimeStr(vodtime)));

        ImageButton update_ok = vod_time_dialog.findViewById(R.id.update_ok);

        update_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                res_video.setVideoPath(resData.getPath());
                res_video.seekTo(vodtime);

//                vrecord(resData.getId());
                vod_time_dialog.dismiss();
            }
        });
        ImageButton update_cancle = vod_time_dialog
                .findViewById(R.id.update_cancle);
        update_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                vod_time_dialog.cancel();
                if (!res_video.isPlaying()) {

                    res_video.setVideoPath(resData.getPath());
//                    vrecord(resData.getId());
                }
            }
        });

        vod_time_dialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                if (!res_video.isPlaying()) {

                    res_video.setVideoPath(resData.getPath());
//                    vrecord(resData.getId());

                }
            }
        });
    }

    private String getTimeStr(int d) {
        // TODO Auto-generated method stub

        int hour = 0;
        int minute = 0;
        int second = 0;

        second = d / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (getTwoLength(hour) + ":" + getTwoLength(minute) + ":" + getTwoLength(second));
    }

    private static String getTwoLength(final int data) {
        if (data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}