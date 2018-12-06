package ypsiptv.prison.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ypsiptv.prison.model.bean.LogoData;
import ypsiptv.prison.model.bean.PowerData;
import ypsiptv.prison.model.bean.VolData;
import ypsiptv.prison.model.bean.gSon;
import ypsiptv.prison.service.MyIntentService;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.SpUtils;
import ypsiptv.prison.utils.Utils;
import ypsiptv.prison.utils.ViewUtil;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.utils.adb;
import ypsiptv.prison.view.activity.ad.bean.FileType;
import ypsiptv.prison.view.base.BaseActivity;


/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.ccdt.app.tv.iptv.app.MyApp.java
 * @author: Sun
 * @date: 2017-04-19 16:16
 */
public class MyApp extends Application {
    public static class ScreenType {

        // 横屏
        public static final int HORIZONTAL = 1;
        // 竖屏
        public static final int VERTICAL = 2;
    }

    public static final String PALY = "PALY";
    public static final String PAUSE = "PAUSE";
    public static final String STOP = "STOP";
    public static final String FORWARD = "FORWARD";
    public static final String REWIND = "REWIND";
    public static final String Cancle = "Cancle";
    /**
     * Application实例
     */

    private static MyApp cInstance;
    public static boolean vodAllAuth = false;


    public static RequestQueue getQueue() {
        return queue;
    }

    public static void setQueue(RequestQueue queue) {
        MyApp.queue = queue;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        config();
        //未捕获异常处理
//        CrashHandler.getInstance().init(getApplicationContext());

        // 初始化第三方模块
        initThird();

//        cInstance = this;

        // 初始化自身功能模块
        initMyself();

        //初始化GreenDao.
        initGreenDao();

        collectStartUp();

        vodAuth();


        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(receiver, filter);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                userLoginAction_uploadPic();
                keepConnectAction_heartbeat();
            } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                //获取ConnectivityManager对象对应的NetworkInfo对象
                // 获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取以太网连接的信息
                NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                if (wifiNetworkInfo.isConnected() || networkInfo.isConnected()) {
                    userLoginAction_getTime();//时间
                    userLoginAction_getLogo();//logo
                    getConfig_getConfig();//音量
                    userLoginAction_changeIp();//修改ip
                    getMessageAction_getPass();//密码
                    getConfig_getControl();//开关机
                    startService(new Intent(context, MyIntentService.class));
//                    unregisterReceiver(receiver);
                } else if (!networkInfo.isConnected() && !wifiNetworkInfo.isConnected()) {

                }
            }

        }
    };

    private void keepConnectAction_heartbeat() {
        String api = "keepConnectAction_heartbeat";
        String url = requrl("keepConnectAction_heartbeat", "");
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

    private void getConfig_getControl() {//获取开关机时间
        String api = "getConfig_getControl";
        String url = requrl("getConfig_getControl", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<List<PowerData>> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<List<PowerData>>>() {
                                    }.getType());
                            if (!data.getData().isEmpty()) {
                                for (PowerData powerData : data.getData()) {
                                    String s = Utils.creatDate(internettime) + " " + powerData.getTime();
                                    LogUtils.d(s + "---" + powerData.getOperate());
                                    long time = Utils.offtime(s).getTime();
                                    long se = time - internettime;
                                    if (se > 0 && powerData.getOperate() == 1) {//开机
                                        handler.sendEmptyMessageDelayed(0, se);
                                    }
                                    if (se > 0 && powerData.getOperate() == 2) {//关机
                                        handler.sendEmptyMessageDelayed(1, se);
                                    }

                                }
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adb.InputEvent(KeyEvent.KEYCODE_POWER);
                    break;
                case 1:
                    adb.InputEvent(KeyEvent.KEYCODE_POWER);
                    break;
            }
        }
    };

    public static String password = "123456";

    private void getMessageAction_getPass() { //获取终端密码
        String api = "getMessageAction_getPass";
        String url = requrl("getMessageAction_getPass", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<String> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<String>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                password = data.getData();
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

    private void userLoginAction_changeIp() {//修改ip通知服务器
        String api = "userLoginAction_changeIp";
        String url = requrl("userLoginAction_changeIp", "");
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


    private Timer timer = null;
    public static long internettime = System.currentTimeMillis();

    private void userLoginAction_getTime() {//获取服务器时间
        String api = "userLoginAction_getTime";
        String url = requrl("userLoginAction_getTime", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {

                            LogUtils.d(json);
                            gSon<Long> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<Long>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                internettime = data.getData();
                                if (timer != null) {
                                    timer.cancel();
                                }
                                timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    public void run() {
                                        internettime += 1000;
                                    }
                                }, 0, 1000);
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

    public static LogoData logoData;

    private void userLoginAction_getLogo() {//获取终端logo
        String api = "userLoginAction_getLogo";
        String url = requrl("userLoginAction_getLogo", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<LogoData> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<LogoData>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                logoData = data.getData();
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

    private void getConfig_getConfig() { //获取音量控制大小
        String api = "getConfig_getConfig";
        String url = requrl("getConfig_getConfig", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<List<VolData>> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<List<VolData>>>() {
                                    }.getType());
                            if (!data.getData().isEmpty()) {
                                int percent = Integer.parseInt(data.getData().get(0).getVal());
                                setStreamVolume(percent);
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

    // 设置音量
    public static void setStreamVolume(int percent) {
        int volume = (int) Math.round((double) maxvolume * percent / 10);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
    }

    private boolean fstart;
    public static String mac;
    public static Gson gson;
    private static AudioManager am;
    private static int maxvolume;
    private static int defaultvolume;
    private Context context;

    public static String version;

    private void config() {
        try {
            context = this;
            gson = new GsonBuilder().create();
            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            maxvolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            defaultvolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            LogUtils.d("maxvolume" + maxvolume + " " + "defaultvolume" + defaultvolume);
            SpUtils.putInt(context, "vol", defaultvolume);
            queue = Volley.newRequestQueue(context);
            mac = Utils.getMACAddress();
            fstart = SpUtils.getBoolean(context, "fstart", false);

            if (!fstart) {
                SpUtils.putBoolean(context, "fstart", true);
                SpUtils.putString(context, "ip", ip);
            }
            version = Utils.getVersion(context);
            LogUtils.d("---version---\n" + version);
            getip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean setip(String ip) {
        try {
            SpUtils.putString(this, "ip", ip);
            getip();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //    public static String ip = "106.12.200.32:8080";
    public static String ip = "192.168.2.11:8080";
    public static String headurl;

    public String getip() {
        String tmp = SpUtils.getString(this, "ip", ip);
        if (!tmp.equals("")) {
            headurl = "http://" + tmp + "/prison/box/";
//            headurl = "http://" + tmp + "/box/";
            LogUtils.d("---headurl---\n" + headurl);
        }
        return tmp;
    }

    public static String requrl(String api, String parm) {
        String url = headurl + api + ".action?mac=" + mac + parm;

        return url;
    }


    // 建立请求队列
    public static RequestQueue queue;

    public static RequestQueue getHttpQueue() {
        return queue;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static boolean getVodAuth() {
        return vodAllAuth;
    }


    private void vodAuth() {

    }

    private void initGreenDao() {
    }

//    public static DaoSession getDaoSession() {
//        return session;
//    }

    /**
     * 初始化自身功能模块
     */
    private void initMyself() {
//        VooleProxyManager.initVooleProxy(getApplicationContext());
    }

    /**
     * 初始化第三方模块
     */
    private void initThird() {

        // LeakCanary 初始化放在最优先
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        enabledStrictMode();
//        LeakCanary.install(this);


        // 初始化腾讯Bugly
//        CrashReport.initCrashReport(getApplicationContext(), "4d8682fdeb", true);
        // 腾讯Bugly崩溃测试
        // CrashReport.testJavaCrash();
        // 初始化第三方工具类库
//        Utils.init(getApplicationContext());
    }


    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }


    /**
     * 记录启动日志
     */
    private void collectStartUp() {
        StringBuilder value = new StringBuilder();
        value.append("{\"data\":{");
        value.append("\"dataType\":\"\",");
        value.append("\"FilmName\":\"AndroidTV启动\",");
        value.append("\"Mid\":\"AndroidTVStartUp\"");
        value.append("}}");

//        DataCollectManager.getInstance().collect("startUp", value.toString());
    }

    public static <T> T jsonToObject(String json, TypeToken<T> typeToken) {
        //  new TypeToken<AJson<Object>>() {}.getType()   对象参数
        // new TypeToken<AJson<List<Object>>>() {}.getType() 集合参数

        if (TextUtils.isEmpty(json) || json.equals("null"))
            return null;
        try {
            return gson.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            return null;
        }
    }

    public static MyApp getInstance() {
        return cInstance;
    }


    private void userLoginAction_uploadPic() {
        try {
            Bitmap bit = ViewUtil.screenShot(BaseActivity.activity);
            final File file = ViewUtil.BitmapToFile(bit);
            if (file.exists()) {
                new Thread(new Runnable() {
                    public void run() {
                        // TODO Auto-generated method stub
                        uploadFile("screenshot", file);
                    }
                }).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    public String uploadFile(String tagName, File file) {
        String api = "userLoginAction_uploadPic";
        String url = requrl(api, "");

        String result = null;
        PostMethod post = null;
        HttpClient client = null;
        try {
            post = new PostMethod(url);

            NameValuePair[] params = new NameValuePair[1];
            params[0] = new BasicNameValuePair("mac", mac);

            client = new HttpClient();

            Part[] parts = null;
            if (params != null) {
                parts = new Part[params.length + 1];
            }
            if (params != null) {
                int i = 0;
                for (NameValuePair entry : params) {
                    parts[i++] = new StringPart(entry.getName(),
                            (String) entry.getValue());
                }
            }

            FilePart filePart = new FilePart(tagName, file.getName(), file,
                    new FileType().getMIMEType(file), "UTF-8");

            filePart.setTransferEncoding("binary");
            parts[parts.length - 1] = filePart;

            post.setRequestEntity(new MultipartRequestEntity(parts, post
                    .getParams()));

            List<Header> headers = new ArrayList<Header>();

            client.getHostConfiguration().getParams()
                    .setParameter("http.default-headers", headers);

            int status = client.executeMethod(post);

            if (status == HttpStatus.SC_OK) {
                String json = post.getResponseBodyAsString();
                JSONObject data = new JSONObject(json);
                String msgCode = data.getString("status");
                // if (msgCode.equalsIgnoreCase("success")) {
                // result = data.getString("success");
                // }
                LogUtils.d("上传截图成功");
            } else {
                result = "";
                LogUtils.d("上传截图失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
            if (client != null) {
                client = null;
            }

        }
        return result;
    }
}
