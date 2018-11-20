package ypsiptv.prison.utils;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Build;
import android.util.Log;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Locale;

public class Util {

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public Util() {
        // 转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 获取系统时间
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 格式化时间
     */
    public static String format(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 格式化时间
     */
    public static String creatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 格式化时间
     */
    public static String formatTZTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 获取系统年月日
     */
    public static String getYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(new Date());
    }

    public static String formatTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    public static String getWeek() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(new Date());
    }

    public static String getTZTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }

    public static long serializeTZTime(String time) {
        //2017-05-18 23:59:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            Log.e("TAG", "time---" + e.getMessage());
        }
        return date.getTime();
    }

    public static long serializeTime(String time) {
        //2017-05-18 23:59:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            Log.e("TAG", "time---" + e.getMessage());
        }
        return date.getTime();
    }

    /**
     * 得到网速
     * 注意：每隔一段时间去调用，每秒种
     *
     * @param context
     * @return
     */
    public String getNetSpeed(Context context) {

        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB

        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        String speedStr = "正在加载 " + String.valueOf(speed) + " kb/s";


        return speedStr;
    }
}
