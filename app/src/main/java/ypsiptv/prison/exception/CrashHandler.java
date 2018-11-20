package ypsiptv.prison.exception;

import android.content.Context;
import android.util.Log;

/**
 * 作用:未处理异常捕获处理
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler instance;
    private Context context;
    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler defaultHandler;

    // 单例
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    // 初始化
    public void init(Context context) {
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //关键语句,设置默认的异常处理
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (throwable == null) {
            return;
        }

        Log.e("TAG", "CrashHandler---" + throwable.toString());
        throwable.printStackTrace();

        Log.e("TAG", throwable.toString());
        // 关闭进程
//        int nPid = android.os.Process.myPid();
//        android.os.Process.killProcess(nPid);

    }
}
