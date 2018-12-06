package ypsiptv.prison.view.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.model.bean.LiveData;
import ypsiptv.prison.model.bean.ModelData;
import ypsiptv.prison.model.bean.UpdateData;
import ypsiptv.prison.model.bean.gSon;
import ypsiptv.prison.utils.LogUtils;
import ypsiptv.prison.utils.VolleyListenerInterface;
import ypsiptv.prison.utils.VolleyRequestUtil;
import ypsiptv.prison.utils.adb;
import ypsiptv.prison.view.activity.video.ResActivity;
import ypsiptv.prison.view.adapter.ModelAdapter;
import ypsiptv.prison.view.base.BaseActivity;
import ypsiptv.prison.view.widget.Update;
import ypsiptv.prison.view.widget.center.ModuleLayoutManager;
import ypsiptv.prison.view.widget.center.TvRecyclerView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
        handler.sendEmptyMessageDelayed(0, 5 * 1000);
    }

    private void loadData() {
        userLoginAction_getModels();
        updateJobAction_checkUpdate();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        handler.removeMessages(0);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    userLoginAction_getUserProductInfo();
                    break;

            }
        }
    };

    private TvRecyclerView mTvRecyclerView;
    private ModuleLayoutManager manager;


    private void initView() {
        mTvRecyclerView = findViewById(R.id.tv_recycler_view);

        manager = new MainActivity.MyModuleLayoutManager(3, LinearLayoutManager.HORIZONTAL,
                400, 260);
        mTvRecyclerView.setLayoutManager(manager);

        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerView_item_space);
        mTvRecyclerView.addItemDecoration(new MainActivity.SpaceItemDecoration(itemSpace));


        mTvRecyclerView.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                //点击事件
                try {
                    LogUtils.d(position + "");
                    toActivity(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {
            }
        });
        mTvRecyclerView.setSelectPadding(5, 5, 5, 5);
    }

    private void toActivity(int p) {
        switch (modelData.get(p).getId()) {
            case 1://监所信息
                startActivity(new Intent(MainActivity.this, ResActivity.class));
                break;
            case 2://电视直播
                userLoginAction_getUserProductInfo();
                break;
            case 3://影音娱乐
                startActivity(new Intent(MainActivity.this, ResActivity.class));
                break;
            case 4://自主购物
                startActivity(new Intent(MainActivity.this, ResActivity.class));
                break;
            case 5://生活指南
                startActivity(new Intent(MainActivity.this, ResActivity.class));
                break;
            case 6://消息公告
                startActivity(new Intent(MainActivity.this, ResActivity.class));
                break;
        }
    }


    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = space;
            outRect.left = space;
        }
    }

    //    public int[] mStartIndex = {0, 1, 2, 3, 5, 8, 9, 11, 14, 15, 17, 20};
//    public int[] mStartIndex = {0, 3, 6, 9, 12, 15, 9, 11, 14, 15, 17, 20};
    private class MyModuleLayoutManager extends ModuleLayoutManager {

        MyModuleLayoutManager(int rowCount, int orientation, int baseItemWidth, int baseItemHeight) {
            super(rowCount, orientation, baseItemWidth, baseItemHeight);
        }

        @Override
        protected int getItemStartIndex(int position) {
            if (position == 0) {
                return position;
            } else if (position == 1) {
                return position + 1;
            } else {
                return position + 3;
            }
        }

        @Override
        protected int getItemRowSize(int position) {
            if (position == 0) {
                return 2;
            } else {
                return 1;
            }
        }

        @Override
        protected int getItemColumnSize(int position) {
            if (position == 0) {
                return 2;
            } else {
                return 1;
            }
        }

        @Override
        protected int getColumnSpacing() {
            return 5;
        }

        @Override
        protected int getRowSpacing() {
            return 5;
        }
    }

    //直播
    private void userLoginAction_getUserProductInfo() {

        String api = "userLoginAction_getUserProductInfo";
        String url = MyApp.requrl("userLoginAction_getUserProductInfo", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<LiveData> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<LiveData>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("key", data.getData());
                                startActivity(new Intent(MainActivity.this, IPLiveActivity.class)
                                        .putExtras(bundle));
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

    private void updateJobAction_checkUpdate() {//升级
        String api = "updateJobAction_checkUpdate";
        String url = MyApp.requrl("updateJobAction_checkUpdate", "&version=" + MyApp.version);
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<UpdateData> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<UpdateData>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                boolean getUpdate = data.getData().getUpdate();
                                if (getUpdate) {
                                    new Update(MainActivity.this, data.getData().getApkUrl());
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

    private List<ModelData> modelData = new ArrayList<>();
    private ModelAdapter mAdapter;

    private void userLoginAction_getModels() {//菜单
        String api = "userLoginAction_getModels";
        String url = MyApp.requrl(api, "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            gSon<List<ModelData>> data = MyApp.gson.fromJson(json,
                                    new TypeToken<gSon<List<ModelData>>>() {
                                    }.getType());
                            if (!data.getData().isEmpty()) {
                                modelData = data.getData();
//                                modelData.addAll(data.getData());
//                                modelData.addAll(data.getData());
                                mAdapter = new ModelAdapter(MainActivity.this, modelData);
                                mTvRecyclerView.setAdapter(mAdapter);
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
//
//    @Override
//    public void onItemClick(View view, int position) {
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
