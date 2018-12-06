package ypsiptv.prison.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.Cmmond;
import ypsiptv.prison.model.bean.gSon;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.view.activity.ad.ResInsertActivity;
import ypsiptv.prison.view.activity.ad.bean.Calendar;
import ypsiptv.prison.view.widget.msg.IScrollState;
import ypsiptv.prison.view.widget.msg.MarqueeToast;
import ypsiptv.prison.view.widget.msg.TextSurfaceView;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService implements IScrollState, Runnable {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private String tag = "MyIntentService";
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "ypsiptv.prison.service.action.FOO";
    private static final String ACTION_BAZ = "ypsiptv.prison.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "ypsiptv.prison.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "ypsiptv.prison.service.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(receiver, filter);
        getmarquee();
        createsocket();
    }
    String head = new SimpleDateFormat("yyyy-MM-dd ").format(new Date(System
            .currentTimeMillis()));
    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void task() {
//
//        String api = "userLoginAction_getLogo";
//        String url = requrl("userLoginAction_getLogo", "");
//        LogUtils.d(url);
//        VolleyRequestUtil.RequestGet(this, url, api,
//                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
//                        VolleyListenerInterface.mErrorListener) {
//                    // Volley请求成功时调用的函数
//                    @Override
//                    public void onMySuccess(String json) {
//                        try {
//                            LogUtils.d(json);
//                            gSon<LogoData> data = MyApp.gson.fromJson(json,
//                                    new TypeToken<gSon<LogoData>>() {
//                                    }.getType());
//                            if (data.getData() != null) {
//                                logoData = data.getData();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    // Volley请求失败时调用的函数
//                    @Override
//                    public void onMyError(VolleyError error) {
//                        // ...
//                        LogUtils.d(error.toString());
//                    }
//                }, false);
//
//
////        计划插播
//        String url = Config.getipaddr(this) + "getMIPAction_get.action?mac=" + Ini.Mac();
//        System.out.println("计划插播" + url);
//        VolleyRequestUtil.RequestGet(this, url, What.task + "",
//                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
//                        VolleyListenerInterface.mErrorListener) {
//                    // Volley请求成功时调用的函数
//                    @Override
//                    public void onMySuccess(String result) {
//                        try {
//                            System.out.println("计划插播------" + result);
//
//                            if (app.isSschabo()) {
//                                System.out.println("检测到正在实时插播。");
//                                if (app.isInsertadstatus()) {
//                                    System.out.println("检测到有即将执行的任务计划插播，任务计划插播正在被终止。");
//                                    handler.removeMessages(What.task);
//                                    app.setInsertadstatus(!app.isInsertadstatus());
//                                } else {
//                                    System.out.println("未检测到任务计划插播");
//
//                                }
//                                return;
//                            }
//                            GsonBean<InsertAd> task = Ini.gson.fromJson(
//                                    result, new TypeToken<GsonBean<InsertAd>>() {
//                                    }.getType());
//                            if (task.getData() != null && !task.getData().equals("")) {
//
//                                if (!app.isInsertadstatus()) {
//                                    app.setInsertAd(task.getData());
//                                    app.setInsertadstatus(true);
//                                    if (task.getData().getStart() <= 0) {
//                                        System.out
//                                                .println("现在开始插播计划插播*************************");
//                                        handler.sendEmptyMessage(What.task);
//                                    } else {
//                                        System.out
//                                                .println("计划插播 将在 *************************"
//                                                        + (task.getData().getStartTime()
//                                                        .getTime() - System
//                                                        .currentTimeMillis())
//                                                        / 1000 + "秒后开始");
//                                        handler.sendEmptyMessageDelayed(What.task, task
//                                                .getData().getStart());
//                                    }
//                                    System.out
//                                            .println("计划插播 将在  *************************"
//                                                    + (task.getData().getEndTime()
//                                                    .getTime() - System
//                                                    .currentTimeMillis()) / 1000
//                                                    + "秒后结束");
//                                }
//
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    // Volley请求失败时调用的函数
//                    @Override
//                    public void onMyError(VolleyError error) {
//                        return;
//                    }
//                }, false);

    }
    private static final int PORT = 9999;
    private ServerSocket server = null;
    private java.net.Socket socket = null;

    public void createsocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(PORT);
                    while (true) {
                        socket = server.accept();
                        new SocketThread(socket).start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public class SocketThread extends Thread {

        private java.net.Socket socket;

        public SocketThread(java.net.Socket socket) {
            this.socket = socket;
        }

        public void run() {
            String msg;
            DataInputStream in = null;
            try {
                in = new DataInputStream(socket.getInputStream());

                while (true) {
                    if ((msg = in.readUTF()) != null) {
                        LogUtils.d(msg);
                        event(msg);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    String cmd = "";

    private void event(String msg) {
        // TODO Auto-generated method stub
        String msgs = "{\"data\":" + msg.substring(1, msg.length() - 1)
                + ",\"msg\":null,\"status\":\"success\"}";
        System.out.println("接收到命令：" + msgs);
        gSon<Cmmond> aj = MyApp.gson.fromJson(msgs,
                new TypeToken<gSon<Cmmond>>() {
                }.getType());

        exec(aj.getData());

    }

    private void exec(Cmmond cmmond) {
        // TODO Auto-generated method stub
        try {
            if (cmmond.getType() == 1 || cmmond.getType() == 2
                    || cmmond.getType() == 3) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("key",cmmond);
                switch (cmmond.getCommand()) {
                    case 1:
                        cmd = "";
                        startActivity(new Intent(getApplicationContext(),
                                ResInsertActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bundle));
                        break;
                    case 2:
                        cmd = "input keyevent "
                                + KeyEvent.KEYCODE_MEDIA_FAST_FORWARD;
                        sendBroadcast(new Intent(MyApp.FORWARD));
                        break;
                    case 3:
                        cmd = "input keyevent " + KeyEvent.KEYCODE_MEDIA_REWIND;
                        sendBroadcast(new Intent(MyApp.REWIND));
                        break;
                    case 4:
                        sendBroadcast(new Intent(MyApp.Cancle));
                        break;
                    case 5:
                        cmd = "input keyevent " + KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
                        sendBroadcast(new Intent(MyApp.PAUSE));
                        break;
                    case 6:
                        LogUtils.d("停止实时插播");
                        cmd = "input keyevent " + KeyEvent.KEYCODE_MEDIA_STOP;
                        sendBroadcast(new Intent(MyApp.STOP));
                        break;
                    default:
                        break;
                }
            }

            if (cmmond.getType() == 5) {// 全屏字幕
                if (cmmond.getCommand() == 1) {
                    LogUtils.d("启用全屏字幕");

                    sendBroadcast(new Intent()
                            .setAction("finishMsgWebActivity"));
                }
                if (cmmond.getCommand() == 6) {
                    LogUtils.d("停止全屏字幕");
                    sendBroadcast(new Intent()
                            .setAction("finishMsgWebActivity"));
                }
            }
            if (cmmond.getType() == 4) {// 跑马灯
                if (cmmond.getCommand() == 1) {
                    LogUtils.d("启用跑马灯");
                    getMessageAction_doGetMessage();
                }
                if (cmmond.getCommand() == 6) {
                    getMessageAction_doGetMessage();
                }
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    Timer marquee = new Timer();

    private void getmarquee() {
        marquee.schedule(new TimerTask() {
            @Override
            public void run() {
                getMessageAction_doGetMessage();
            }
        }, 0, 60 * 1000);
    }

    private String msg = "";
    private boolean runmarquee = false;

    private void getMessageAction_doGetMessage() {
        String url = MyApp.requrl("getMessageAction_doGetMessage", "");
//        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, tag,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
//                            LogUtils.d(json);
                            gSon<String> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<String>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                msg = data.getData();
                            }
                            if (!runmarquee) {
                                runmarquee = true;
                                handler.post(MyIntentService.this);
                            }
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

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {

            } else if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED)) {
                LogUtils.d(MyApp.mac + "更改系统时间");
            }
        }
    };

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        try {
            Text.setLoop(false);
            Looper.prepare();
            handler.post(this);
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        showMessage();
    }

    private MarqueeToast toast;
    private TextSurfaceView Text;

    public void showMessage() {
        try {
            if (!msg.equals("")) {
                toast = new MarqueeToast(getApplicationContext());
                Text = new TextSurfaceView(getApplicationContext(), this);
                Text.setOrientation(1);
                toast.setHeight(40);
                Text.setContent(msg);
                LogUtils.d(msg);
                toast.setView(Text);
                toast.setGravity(Gravity.BOTTOM, 1920, 0, 0);
                toast.show();
            } else {
                if (toast != null) {
                    toast.hid();
                    toast = null;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
