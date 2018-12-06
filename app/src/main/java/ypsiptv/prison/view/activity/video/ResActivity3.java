package ypsiptv.prison.view.activity.video;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.Res;
import ypsiptv.prison.model.bean.ResData;
import ypsiptv.prison.model.bean.gSon;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.RsType;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.view.activity.video.play.ResAudio;
import ypsiptv.prison.view.activity.video.play.ResImage;
import ypsiptv.prison.view.activity.video.play.ResTxt;
import ypsiptv.prison.view.activity.video.play.ResVideo;
import ypsiptv.prison.view.adapter.ResAdapter2;
import ypsiptv.prison.view.base.BaseActivity;

/**
 * Created by zhu on 2017/9/26.
 */

public class ResActivity3 extends BaseActivity implements AdapterView.OnItemClickListener {
    int pageNo = 1;
    int type;
    int pageSize = 999999;
    int maxpageSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res3);

        initview();
        setvalue();
    }

    private ResData resData;

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            resData = (ResData) getIntent().getSerializableExtra("key");
            if (resData != null) {
                getDirF();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    List<ResData> griddir = new ArrayList<ResData>();


    String filedir;

    private void getDirF() {
        if (resData.getId() > 0) {
            filedir = "&file.dir=" + resData.getId();
        }

        String api = "getFileAction_getSourceFile";
        String url = MyApp.requrl("getFileAction_getSourceFile", filedir);
        LogUtils.d(url);


        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<Res> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<Res>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                try {
                                    griddir = data.getData().getData();
                                    res2_grid.setAdapter(new ResAdapter2(ResActivity3.this, griddir));
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
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


    GridView res2_grid;


    private void initview() {

        res2_grid = findViewById(R.id.res2_grid);
        res2_grid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

        if (parent == res2_grid) {
            try {
                String path = griddir.get(position).getPath();
                String temp = path.substring(path.lastIndexOf("."));
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putSerializable("key", griddir.get(position));
                System.out.println(path + "*************" + temp);
                switch (RsType.type.get(temp.toLowerCase())) {
                    case 1:
                        intent.setClass(ResActivity3.this, ResImage.class);
                        break;
                    case 2:
                        intent.setClass(ResActivity3.this, ResAudio.class);
                        break;
                    case 3:
                        intent.setClass(ResActivity3.this, ResVideo.class);
                        break;
                    case 4:
                        intent.setClass(ResActivity3.this, ResTxt.class);
                        break;

                    default:
                        break;
                }
                startActivity(intent.putExtras(bundle));
            } catch (Exception e) {
                // TODO: handle exception

            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
