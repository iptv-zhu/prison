package ypsiptv.prison.view.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.List;

import ypsiptv.prison.R;
import ypsiptv.prison.model.bean.ModelData;
import ypsiptv.prison.utils.ContantUtil;
import ypsiptv.prison.view.widget.FULL;


public class ModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private Context mContext;
    private int mItemCount;
    private List<ModelData> modelData;

    public ModelAdapter(Context context, List<ModelData> modelData) {
        mContext = context;
//        mItemCount = itemCount;
        this.modelData = modelData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(View.inflate(mContext, R.layout.adapter_app, null));
    }

   private RecyclerViewHolder viewHolder;


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        viewHolder = (RecyclerViewHolder) holder;
//        viewHolder.mName.setText(position + "." + modelData.get(position).getName());
        try {
            GradientDrawable drawable = (GradientDrawable) viewHolder.mFrameLayout.getBackground();
            drawable.setColor(ContextCompat.getColor(mContext, ContantUtil.getRandColor()));

            viewHolder.mName.setText(modelData.get(position).getName());
            String icon = modelData.get(position).getPath();
            String temp = icon.substring(icon.lastIndexOf(".")).toLowerCase();
            if ("gif".equals(temp)) {
                Glide.with(mContext).load(icon).asGif().into(viewHolder.icon);
            } else {
                Glide.with(mContext).load(icon).into(viewHolder.icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return modelData.size();
    }

    private void ViewGone() {
        viewHolder.icon.setVisibility(View.GONE);
        viewHolder.video.setVisibility(View.GONE);

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        FrameLayout mFrameLayout;
        TextView mName;
        ImageView icon;
        VideoView video;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            mName = itemView.findViewById(R.id.tv_item_tip);
            mFrameLayout = itemView.findViewById(R.id.fl_main_layout);
            video = itemView.findViewById(R.id.video);
            FULL.star(video);
            video.setOnPreparedListener(ModelAdapter.this);
            video.setOnCompletionListener(ModelAdapter.this);
            video.setOnErrorListener(ModelAdapter.this);
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
    }

    @Override
    public boolean onError(MediaPlayer mp, int i, int i1) {
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
        mp.start();
        mp.setVolume(0, 0);

    }
}
