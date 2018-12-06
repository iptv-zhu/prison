package ypsiptv.prison.view.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ypsiptv.prison.R;
import ypsiptv.prison.app.MyApp;
import ypsiptv.prison.utils.Utils;

public class Top extends LinearLayout {
    private View view;
    private Context context;


    public Top(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.top, this);
        findView();

        updateUI();
    }


    private ImageView logo;
    private TextView time;
    private TextView date;

    private void findView() {
        logo = view.findViewById(R.id.logo);
        time = view.findViewById(R.id.time);
        date = view.findViewById(R.id.date);
        date.setText(Utils.creatDate(MyApp.internettime));


    }

    private void updateUI() {
        handler.sendEmptyMessage(0);
        handler.sendEmptyMessage(1);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    time.setText(Utils.creatTime(MyApp.internettime));
                    handler.sendEmptyMessageDelayed(0, 1 * 1000);
                    break;
                case 1:
                    if (MyApp.logoData == null) {
                        handler.sendEmptyMessageDelayed(1, 5 * 1000);
                        return;
                    }
                    String path = MyApp.logoData.getLogoPath();
                    String temp = path.substring(path.lastIndexOf(".")).toLowerCase();
                    if ("gif".equals(temp)) {
                        Glide.with(context).load(path).asGif().into(logo);
                    } else {
                        Glide.with(context).load(path).into(logo);
                    }

                    break;
            }
        }
    };
}
