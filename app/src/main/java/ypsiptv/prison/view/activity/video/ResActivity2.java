package ypsiptv.prison.view.activity.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.Dir;
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
import ypsiptv.prison.view.adapter.DirListAdapter;
import ypsiptv.prison.view.adapter.ResAdapter2;
import ypsiptv.prison.view.base.BaseActivity;

/**
 * Created by zhu on 2017/9/26.
 */

public class ResActivity2 extends BaseActivity implements AdapterView.OnItemClickListener, DirListAdapter.OnItemClickListener {
    int pageNo = 1;
    int type;
    int pageSize = 99999;
    int maxpageSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res2);

        initview();
        setvalue();
    }


    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            dir = (Dir) getIntent().getSerializableExtra("key");
            if (dir != null) {
                dirid1 = dir.getId();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        getdirlist();
                    }
                });

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    List<Dir> dirs;
    DirListAdapter adapter;
    String filedir;

    private void getdirlist() {
        if (dirid1 > 0) {
            filedir = "&file.dir=" + dirid1;
        }
        String api = "getFileAction_getDir";
        String url = MyApp.requrl("getFileAction_getDir", filedir);
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
                            if (!data.getData().isEmpty()) {
                                dirs = data.getData();

                                adapter = new DirListAdapter(ResActivity2.this, dirs);
                                left_list.setAdapter(adapter);
                                adapter.setOnItemClickListener(ResActivity2.this);

                                dirid2 = dirs.get(0).getId();
                                getDirD();

                                if (res_line.getVisibility() == View.GONE) {
                                    res_line.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (res_line.getVisibility() == View.VISIBLE) {
                                    res_line.setVisibility(View.GONE);
                                }
                                getDirF();
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
    public void onItemClick(View view, int position) {
        try {
            System.out.println("-----xxxxxx" + (view == left_list));
            if (!griddir.isEmpty()) {
                griddir.clear();
            }
            dirid2 = dirs.get(position).getId();
            getDirD();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    List<ResData> griddir = new ArrayList<ResData>();

    private void getDirD() {
        if (dirid2 > 0) {
            filedir = "&file.dir=" + dirid2;
        }
        String api = "getFileAction_getDir";
        String url = MyApp.requrl("getFileAction_getDir", filedir);
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
                            for (int i = 0; i < data.getData().size(); i++) {
                                ResData resData = new ResData();
                                resData.setId(data.getData().get(i).getId());
                                resData.setName(data.getData().get(i).getName());
                                resData.setPath("");
                                resData.setFileType(0);
                                griddir.add(resData);
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


    private void getDirF() {
        if (dirid2 > 0) {
            filedir = "&file.dir=" + dirid2;
        }

        String api = "getFileAction_getSourceFile";
        String url = MyApp.requrl("getFileAction_getSourceFile", filedir
                + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&sub=false");
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
                            for (int i = 0; i < data.getData().getData().size(); i++) {
                                ResData resData = new ResData();
                                resData.setId(data.getData().getData().get(i).getId());
                                resData.setName(data.getData().getData().get(i).getName());
                                resData.setPath(data.getData().getData().get(i).getPath());
                                resData.setFileType(1);
                                griddir.add(resData);
                            }

                            res2_grid.setAdapter(new ResAdapter2(ResActivity2.this,
                                    griddir));
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

    private Dir dir;
    private int dirid1;
    private int dirid2;
    RecyclerView left_list;
    LinearLayoutManager layoutmanager;
    GridView res2_grid;

    LinearLayout res_line;

    private void initview() {
        left_list = findViewById(R.id.left_list);
        res_line = findViewById(R.id.res_line);
        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        left_list.setLayoutManager(layoutmanager);
        res2_grid = findViewById(R.id.res2_grid);
        res2_grid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

        if (parent == res2_grid) {
            if (griddir.get(position).getFileType() == 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", griddir.get(position));
                startActivity(new Intent(this, ResActivity3.class)
                        .putExtras(bundle));
            } else {
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
                            intent.setClass(ResActivity2.this, ResImage.class);
                            break;
                        case 2:
                            intent.setClass(ResActivity2.this, ResAudio.class);
                            break;
                        case 3:
                            intent.setClass(ResActivity2.this, ResVideo.class);
                            break;
                        case 4:
                            intent.setClass(ResActivity2.this, ResTxt.class);
                            break;

                        default:
                            break;
                    }
                    startActivity(intent.putExtras(bundle));
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
