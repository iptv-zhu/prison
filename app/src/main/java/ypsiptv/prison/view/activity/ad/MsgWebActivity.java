package ypsiptv.prison.view.activity.ad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import ypsiptv.prison.R;
import ypsiptv.prison.view.activity.ad.bean.MsgData;
import ypsiptv.prison.view.base.BaseActivity;

public class MsgWebActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msgweb);

		initview();
		setvalue();
		isstop();

		reg();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	protected void reg() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finishMsgWebActivity");
		this.registerReceiver(receiver, filter);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			finish();
		}
	};

	private void isstop() {

		handler.sendEmptyMessageDelayed(0, data
				.getEndtime().getTime()
				- System.currentTimeMillis());

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					finish();
					break;

				default:
					break;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	MsgData data;

	private void setvalue() {
		// TODO Auto-generated method stub
		data = (MsgData) getIntent().getSerializableExtra("key");


		msgtitle.setText(data.getTitle());
		msgweb.loadDataWithBaseURL(null, data.getContent(), "text/html",
				"utf-8", null);
	}

	WebView msgweb;
	TextView msgtitle;

	private void initview() {
		// TODO Auto-generated method stub

		msgtitle = (TextView) findViewById(R.id.msgtitle);
		msgweb = (WebView) findViewById(R.id.msgweb);
		WebSettings websettings = msgweb.getSettings();
		websettings.setJavaScriptEnabled(true);
		websettings.setBuiltInZoomControls(true);
		msgweb.setBackgroundColor(Color.TRANSPARENT);
		msgweb.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

}
