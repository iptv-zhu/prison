package ypsiptv.prison.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import ypsiptv.prison.app.MyApp;
import com.bumptech.glide.Glide;

import java.io.File;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    public static void saveString(String key, String value) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("cache", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value).commit();
    }

    public static String getString(String key) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("cache", MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void savaInt(String key, int position) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("cache", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, position).commit();
    }

    public static int getInt(String key) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("cache", MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static void savaBoolean(String key, boolean value) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("cache", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value).commit();
    }

//    public static void savaDAO(HistoryBean bean) {
//        HistoryBeanDao dao = MyApp.getDaoSession().getHistoryBeanDao();
//        HistoryBean unique = dao.queryBuilder().where(HistoryBeanDao.Properties.Title.eq(bean.getTitle())).unique();
//        if (unique == null) {
//            dao.insert(bean);
//        } else {
//            dao.delete(unique);
//            dao.insert(bean);
//        }
//
//        Log.e("TAG", "插入数据库成功--" + bean);
//    }

    public static boolean getBoolean(String key) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences("cache", MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static String parseToTimeTmp(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return (format.parse(time).getTime() + "").substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 身份证号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isID(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^\\d{17}([0-9]|X|x)$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static void disPlayImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void disPlayImage(File file, ImageView imageView) {
        Glide.with(MyApp.getInstance().getApplicationContext()).load(file).into(imageView);
    }

    public static void disPlayImage(File file, ImageView imageView, int width, int heigth) {
        Glide.with(MyApp.getInstance().getApplicationContext()).load(file).into(imageView);
    }

    public static void disPlayImage(String url, ImageView imageView, int defaultPic) {
        Glide.with(MyApp.getInstance().getApplicationContext())
                .load(url).into(imageView);
    }

    public static void disPlayImage(String url, ImageView imageView) {
        Glide.with(MyApp.getInstance().getApplicationContext())
                .load(url).into(imageView);
    }

    public static void disPlayImage(int id, ImageView imageView) {
        Glide.with(MyApp.getInstance().getApplicationContext()).load(id).into(imageView);
    }

    /**
     * 获取设备当前MAC地址
     */
    public static String getMACAddress() {
        // 优先获取有线的mac
//        String mac = getMACAddress("wlan0");
        String mac = getMACAddress("eth0");
        return mac;
    }

    /**
     * 获取MAC地址
     */
    private static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName))
                        continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null)
                    return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0)
                    buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "testcode";
    }
}
