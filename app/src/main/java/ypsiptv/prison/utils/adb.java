package ypsiptv.prison.utils;

import android.content.Context;
import android.os.SystemClock;

public class adb {
    Context context;

    public adb(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public static void screen() {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    String cmd = "screencap -p /mnt/screen.png";
                    Process process = Runtime.getRuntime().exec(cmd);
                    System.out.println("**************screen**************");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void InputEvent(final int KeyCode) {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    String cmd = "input keyevent " + KeyCode;
                    System.out.println(cmd);
                    Process process = Runtime.getRuntime().exec(cmd);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void LongInputEvent(final int KeyCode) {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    String cmd = "input keyevent --longpress " + KeyCode;
                    System.out.println(cmd);
                    Process process = Runtime.getRuntime().exec(cmd);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void reboot() {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    String cmd = "reboot";
                    Process process = Runtime.getRuntime().exec(cmd);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }).start();
    }

    public static void install(final String path) {
        // TODO Auto-generated method stub
        System.out.println("install :" + path);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Process process = Runtime.getRuntime().exec("chmod 607 " + path);
                    process.wait();

                    String cmd = "pm install -r " + path;

                    System.out.println(cmd);

                    Process process2 = Runtime.getRuntime().exec(cmd);
                    // process.wait();

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }).start();

    }

}