package ypsiptv.prison.utils;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;


public class ReflectHelper {
    private static String TAG = "ReflectHelper";

    public static Object invoke(String clazzName, String fieldName, Object obj) {
        try {
            Class clazz = Class.forName(clazzName);
            Method method = clazz.getDeclaredMethod(fieldName);
            method.setAccessible(true);
            return method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getField(String clazzName, String fieldName, Object objOrc) {
        try {
            Class clazz = Class.forName(clazzName);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(objOrc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setField(String clazzName, String fieldName, Object objOrc, Object objDec) {
        try {
            Class clazz = Class.forName(clazzName);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(objOrc, objDec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Integer> getStaticFieldMap_Int(String className, String fieldNameFilter) {
        HashMap<String, Integer> flagFieldMap = new HashMap<>();
        try {
            Class clazz = Class.forName(className);
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                if (field.getName().indexOf(fieldNameFilter) == 0) {
                    flagFieldMap.put(field.getName(), field.getInt(clazz));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getFieldTable error！");
        }
        return flagFieldMap;
    }
}
