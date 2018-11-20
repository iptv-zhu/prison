package ypsiptv.prison.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ypsiptv.prison.app.MyApp;


public class ConstUtils {
    public static String BASEURL = "http://192.168.2.25:8106";

    private void getReq(final Context context, String url) {
        VolleyRequestUtil.RequestGet(context, url, "tag",
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                    }
                }, true);
    }

    private void postReq(final Context context, String url) {
        VolleyRequestUtil.RequestGet(context, url, "tag",
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                    }
                }, true);
    }

    private void uploadFile(String filename, final String cutnameString, final Context mContext) {
        final String upLoadServerUri = "";
        HashMap<String, String[]> map = new HashMap<>();
        map.put("uploadedfile", new String[]{filename, cutnameString});
        MyApp.getQueue().add(new PostUploadRequest(upLoadServerUri,
                map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                updatePic(cutnameString, mContext, upLoadServerUri);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "文件上传失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                //map.put("token", "");
                return map;
            }
        });
    }

    private void updatePic(String cutnameString, Context context, String url) {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        params.put("pic", cutnameString);
        VolleyRequestUtil.RequestPost(context, url, "tag", params,
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String response) {

                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {

                    }
                }, true);
    }

}
