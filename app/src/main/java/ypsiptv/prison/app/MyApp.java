package ypsiptv.prison.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import ypsiptv.prison.service.MyIntentService;
import ypsiptv.prison.utils.Utils;


/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.ccdt.app.tv.iptv.app.MyApp.java
 * @author: Sun
 * @date: 2017-04-19 16:16
 */
public class MyApp extends Application {

    /**
     * Application实例
     */
    private static MyApp cInstance;
    public static boolean vodAllAuth = false;
    public static String mac;

    public static RequestQueue getQueue() {
        return queue;
    }

    public static void setQueue(RequestQueue queue) {
        MyApp.queue = queue;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //未捕获异常处理
//        CrashHandler.getInstance().init(getApplicationContext());

        // 初始化第三方模块
        initThird();

        cInstance = this;

        // 初始化自身功能模块
        initMyself();

        //初始化GreenDao.
        initGreenDao();

        collectStartUp();

        vodAuth();

        queue = Volley.newRequestQueue(getApplicationContext());
        mac = Utils.getMACAddress();
        startService(new Intent(this, MyIntentService.class));
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

    public static MyApp getInstance() {
        return cInstance;
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
}
