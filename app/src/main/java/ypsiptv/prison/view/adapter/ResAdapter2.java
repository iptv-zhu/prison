package ypsiptv.prison.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import ypsiptv.prison.R;
import ypsiptv.prison.model.bean.ResData;
import ypsiptv.prison.utils.RsType;

public class ResAdapter2 extends BaseAdapter {

	private List<ResData> list;
	private Context context;

	public ResAdapter2(Context context, List<ResData> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		try {
			return list.size();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private View view;
	private ImageView vod_img;
	private TextView vod_name;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		view = LayoutInflater.from(context).inflate(R.layout.adapter_res2,
				null);
		try {
			vod_name = (TextView) view.findViewById(R.id.vod_name);
			vod_img = (ImageView) view.findViewById(R.id.vod_img);

			vod_name.setText(list.get(position).getName());

			if (list.get(position).getFileType() == 0) {
				vod_img.setBackgroundResource(R.drawable.vod2_dir);
			} else {
				String temp = list
						.get(position)
						.getPath()
						.substring(
								list.get(position).getPath().lastIndexOf("."));

				switch (RsType.type.get(temp.toLowerCase())) {
				case 1:
					vod_img.setBackgroundResource(R.drawable.vod2_img);
					break;
				case 2:
					vod_img.setBackgroundResource(R.drawable.vod2_audio);
					break;
				case 3:
					vod_img.setBackgroundResource(R.drawable.vod2_video);
					break;
				case 4:
					vod_img.setBackgroundResource(R.drawable.vod2_txt);
					break;
				default:
					break;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return view;
	}
}
