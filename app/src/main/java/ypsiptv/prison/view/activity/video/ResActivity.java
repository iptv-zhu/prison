package ypsiptv.prison.view.activity.video;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.Dir;
import ypsiptv.prison.model.bean.gSon;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.view.adapter.DirAdapter;
import ypsiptv.prison.view.base.BaseActivity;

public class ResActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);
        initview();
        setvalue();
    }

    GridView res_list;

    private void initview() {
        res_list = findViewById(R.id.res_list);
        res_list.setOnItemClickListener(this);
    }


    private void setvalue() {
        try {
            getFileAction_getDir();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    List<Dir> dirs;

    private void getFileAction_getDir() {
        String api = "getFileAction_getDir";
        String url = MyApp.requrl("getFileAction_getDir", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<List<Dir>> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<List<Dir>>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                dirs = data.getData();
                                res_list.setAdapter(new DirAdapter(ResActivity.this, dirs));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                    }
                }, false);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        if (parent == res_list) {

            final int p = position;
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", dirs.get(p));

//                    startActivity(new Intent(VideoActivity.this, ResActivity2.class)
//                            .putExtras(bundle));


        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
