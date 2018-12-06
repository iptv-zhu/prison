package ypsiptv.prison.utils;

import android.util.Log;


import okhttp3.internal.Util;
import ypsiptv.prison.app.MyApp;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class DataGather {

    private static String time = Utils.creatTime(System.currentTimeMillis());
    private static String filesDir = MyApp.getInstance().getFilesDir().getAbsolutePath();

    public static void writeDG(String str) {
        //订购
        String dg = filesDir + "longjiangtv-vod-dg-" + time + ".csv";

        File file = new File(dg);
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }else {
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeVod(String str) {
        //点播
        String vod = filesDir + "longjiangtv-vod-playlog-" + time + ".csv";
        Log.e("TAG", "数据采集----" + str);
        File file = new File(vod);
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(fos),"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }else {
                fos = new FileOutputStream(file,true);
                bw = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(fos),"GBK"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("TAG", "数据采集---" + e.toString());
                }
            }
        }
    }

    public static void writeAd(String str) {
        //广告
        String ad = filesDir + "longjiangtv-ad-playlog-" + ".csv";

        File file = new File(ad);
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }else {
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeLookBack(String str) {
        //回看
        String lookBack = filesDir + "longjiangtv-vod-tvup-" + time + ".csv";
        File file = new File(lookBack);
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }else {
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeTimeShift(String str) {
        //时移
        String timeShift = filesDir + "longjiangtv-vod-times-" + time + ".csv";
        Log.e("TAG", "数据采集---" + str);
        File file = new File(timeShift);
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }else {
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
//                byte[] bytes = (str + "\r\n").getBytes();
                bw.write(str);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "数据采集---" + e.toString() + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
