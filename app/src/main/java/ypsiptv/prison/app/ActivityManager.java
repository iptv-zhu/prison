package ypsiptv.prison.app;

import android.app.Activity;

import java.util.Stack;

public class ActivityManager {

    //使用单例模式:恶汉式
    private ActivityManager(){}

    private static ActivityManager manager = new ActivityManager();

    public static ActivityManager getActivityManager(){
        return manager;
    }

    //提供栈的对象
    private Stack<Activity> activityStack = new Stack<>();

    //Activity的添加
    public void addActivity(Activity activity){
        if(activity != null) {
            activityStack.add(activity);
        }
    }

    //删除指定的Activity
    public void remove(Activity activity){
        if(activity != null) {
            for (int i = activityStack.size() - 1;i >= 0;i--){
                Activity currentActivity = activityStack.get(i);
                if(currentActivity.getClass().equals(activity.getClass())) {
                    //销毁当前线程
                    currentActivity.finish();
                    //从栈空间中移除
                    activityStack.remove(i);
                }
            }
        }
    }

    //删除当前的Activity
    public void removeCurrent(){
        //获取栈顶的Activity
        Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }

    //移除所有的Activity
    public void removeAll(){
        for (int i = activityStack.size() - 1;i >= 0;i--){
            Activity activity = activityStack.get(i);
            activity.finish();
            activityStack.remove(i);
        }
    }

    public int size(){
        return activityStack.size();
    }

}
