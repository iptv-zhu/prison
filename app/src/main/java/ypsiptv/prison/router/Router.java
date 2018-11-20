package ypsiptv.prison.router;


public class Router {
    public static final String DATA_TYPE_APP = "app";
    public static final String DATA_TYPE_WEB = "web";
    public static final String DATA_TYPE_YP = "yp";


    private static final String TAG = Router.class.getSimpleName();

    public static boolean isCycleData(String dataType) {
//        LogUtils.d(TAG, "---isCycleData---dataType  " + dataType);
//        return !(StringUtils.isSpace(dataType) || DATA_TYPE_YP.equals(dataType));
        return false;
    }
}
