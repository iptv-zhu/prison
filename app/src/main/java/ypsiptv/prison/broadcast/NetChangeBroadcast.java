package ypsiptv.prison.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * 作用:网络状态监听广播
 */

public class NetChangeBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int sdkInt = Build.VERSION.SDK_INT;
        Log.e("TAG", "sdkInt---" + sdkInt);
//        if (sdkInt < Build.VERSION_CODES.LOLLIPOP) {
        //获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取ConnectivityManager对象对应的NetworkInfo对象
        // 获取WIFI连接的信息
        NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //获取以太网连接的信息
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (wifiNetworkInfo.isConnected() || networkInfo.isConnected()) {
            Toast.makeText(context, "网络连接成功", Toast.LENGTH_SHORT).show();
        } else if (!networkInfo.isConnected() && !wifiNetworkInfo.isConnected()) {
            Toast.makeText(context, "网络异常,请检查网络", Toast.LENGTH_SHORT).show();
        }

    }
}
