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
import ypsiptv.prison.model.bean.Dir;

public class DirAdapter extends BaseAdapter {

    private List<Dir> list;
    private Context context;

    public DirAdapter(Context context, List<Dir> list) {
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

    int[] img = {R.drawable.vod_1, R.drawable.vod_2, R.drawable.vod_3};

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        try {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_dir,
                    null);
            vod_name = (TextView) view.findViewById(R.id.vod_name);
            vod_img = (ImageView) view.findViewById(R.id.vod_img);
            if ((position + 1) % 3 == 0) {
                vod_img.setBackgroundResource(R.drawable.vod_3);
            } else if ((position + 1) % 3 == 1) {
                vod_img.setBackgroundResource(R.drawable.vod_1);
            } else if ((position + 1) % 3 == 2) {
                vod_img.setBackgroundResource(R.drawable.vod_2);
            }
            vod_name.setText(list.get(position).getName());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return view;
    }
}
