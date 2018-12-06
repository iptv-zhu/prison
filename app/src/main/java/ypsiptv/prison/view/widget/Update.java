package ypsiptv.prison.view.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ypsiptv.prison.BuildConfig;
import ypsiptv.prison.R;


public class Update {

    private Context context;
    private String packageName;
    private String apkurl;
    private String savePath;

    private DownLoadFileThread downLoadFileThread;
    private Handler handler;
    private String filename;

    public Update(Context context, String apkurl) {
        this.context = context;
//        filename = apkurl.substring(apkurl.lastIndexOf('/') + 1);
//        File dir = new File(Environment.getExternalStorageDirectory().getPath()
//                + File.separator + filename);
        File dir = context.getDir("prison", Context.MODE_PRIVATE);
        dir.mkdirs();
        this.packageName = context.getPackageName();
        this.apkurl = apkurl;
        this.savePath = dir.getAbsolutePath() + "/";
//        this.savePath = dir.getAbsolutePath();
        handler = new Handler();
        filename = "prison.apk";
//        if (!dir.exists()) {
            downloadAndInstall();
//        } else {
//            installApk(savePath);
//        }

    }

    private ProgressDialog pBar;

    public void downloadAndInstall() {

        pBar = new ProgressDialog(context);
        pBar.setTitle(context.getString(R.string.update_title));
        pBar.setMessage(context.getString(R.string.update_content));
        // pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pBar.setCancelable(false);


        pBar.setProgress(100);
        pBar.show();
        downLoadFileThread = new DownLoadFileThread(apkurl, savePath + filename
                , new DownloadCallback() {

            @Override
            public void onSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pBar.cancel();
                        getFilePermission(savePath + filename);
//////                        install(savePath + filename);
                        install2(savePath + filename);
//                        installApk(savePath);
                    }
                });
            }

            @Override
            public void onPrecessing(int written, long total) {
                int c = (int) ((long) written * 100 / total);
                pBar.setProgress(c);
            }

            @Override
            public void onFail(final String e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getString(R.string.DownFailed) + "\nCode:" + e, Toast.LENGTH_SHORT).show();
                    }
                });
                pBar.cancel();
            }

            @Override
            public void onCancel() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getString(R.string.Cancle), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        downLoadFileThread.start();
    }

    public static void install(final String path) {
        // TODO Auto-generated method stub
        System.out.println("install :" + path);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {

                    String cmd = "pm install -r " + path;

                    System.out.println(cmd);

                    Process process = Runtime.getRuntime().exec(cmd);
                    // process.wait();

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void install2(String fullfilepath) {
        getFilePermission(fullfilepath);
        android.content.Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + fullfilepath),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private void getFilePermission(String file) {
        ShellExecute ex = new ShellExecute();
        String[] cmd = {"chmod", "607", file};
//        String[] cmd = {"chmod", "777", file};
        try {
            ex.execute(cmd, "/");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class DownLoadFileThread extends Thread {

        private String url;
        // 文件保存路径
        private String fullFilename;
        private DownloadCallback callback;

        public DownLoadFileThread(String url, String fullFilename,
                                  DownloadCallback callback) {
            this.url = url;
            this.fullFilename = fullFilename;
            this.callback = callback;
        }

        public void run() {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(this.url);
            HttpResponse response;

            FileOutputStream outStream = null;
            try {
                response = client.execute(get);
                System.out.println("Update file Status:-----------------" + response.getStatusLine().getStatusCode());
                int code = response.getStatusLine().getStatusCode();
                if (code == 200) {
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();
                    InputStream is = entity.getContent();
                    File f = new File(fullFilename);
                    f.getParentFile().mkdirs();
                    if (is != null) {
                        outStream = new FileOutputStream(new File(fullFilename));

                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            if (this.isInterrupted()) {
                                callback.onCancel();
                                return;
                            }
                            outStream.write(buf, 0, ch);
                            count += ch;
                            if (count != 0) {
                                callback.onPrecessing(count, length);
                            }
                        }
                    }
                    outStream.flush();
                    callback.onSuccess();
                } else {
                    callback.onFail(code + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onFail("Error");
            } finally {
                if (outStream != null) {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    interface DownloadCallback {

        public void onSuccess();

        public void onPrecessing(int written, long total);

        public void onFail(String e);

        public void onCancel();
    }

    private class ShellExecute {
        public String execute(String[] cmmand, String directory)
                throws IOException {
            String result = "";
            try {
                ProcessBuilder builder = new ProcessBuilder(cmmand);
                if (directory != null)
                    builder.directory(new File(directory));
                builder.redirectErrorStream(true);
                Process process = builder.start();
                InputStream is = process.getInputStream();
                byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    result = result + new String(buffer);
                }
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * 安装apk文件
     */
    private void installApk(String apkfiles) {
        File apkFile = new File(apkfiles);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            String[] command = {"chmod", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException ignored) {
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }
}
