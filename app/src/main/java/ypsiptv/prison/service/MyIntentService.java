package ypsiptv.prison.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.utils.LogUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
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
        System.out.println("test services");
        mobile();
    }

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


    private String url = "http://192.168.2.25:8100/phone";
    //    private String url = "http://192.168.2.15:8102/phone";
    public Socket socket;

    private void mobile() {

        try {
            socket = IO.socket(url);
            initAction();
        } catch (Exception e) {
            e.printStackTrace();
        }


        socket.connect();
    }

    private String connect_error = "connect_error";
    private String disconnect = "disconnect";
    private String connect = "connect";
    private String switch_status = "switch_status";
    private String basic_masseage = "basic_masseage";
    private String skinPublish = "skinPublish";
    private String theme = "theme";
    private String addtheme = "add-theme";
    private String deltheme = "del-theme";
    private String setStb = "setStb";
    private String test = "test";

    private void initAction() {
        eventAction(connect_error);
        eventAction(disconnect);
        eventAction(connect);
        eventAction(switch_status);
        eventAction(basic_masseage);
        eventAction(skinPublish);
        eventAction(theme);
        eventAction(addtheme);
        eventAction(deltheme);
        eventAction(setStb);
    }

    private void eventAction(final String actionname) {

        socket.on(actionname, new Emitter.Listener() {

            public void call(Object... arg0) {
                // TODO Auto-generated method stub
                try {
                    if (connect_error.equals(actionname)) {
                        LogUtils.d(MyApp.mac + "连接失败----");
                    }
                    if (disconnect.equals(actionname)) {
                        LogUtils.d("断开连接----");
                    }
                    if (connect.equals(actionname)) {
                        LogUtils.d("连接成功----");
                        socket.emit("register", MyApp.mac);
                    }
                    if (switch_status.equals(actionname)) {
                        LogUtils.d(actionname);
                    }
                    if (basic_masseage.equals(actionname)) {
                        LogUtils.d(actionname);
                    }
                    if (skinPublish.equals(actionname)) {
                        LogUtils.d(actionname);
                    }
                    if (theme.equals(actionname)) {
                        LogUtils.d(actionname);
                    }
                    if (addtheme.equals(actionname)) {
                        LogUtils.d(actionname);
                    }
                    if (deltheme.equals(actionname)) {
                        LogUtils.d(actionname);
                    }
                    if (setStb.equals(actionname)) {

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        });
    }

}
