package ypsiptv.prison.view.base;

import android.os.Bundle;

public interface IBaseView {
    void initView(Bundle savedInstanceState);
    void loadData();
    int getContentId();
}
