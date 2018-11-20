package ypsiptv.prison.utils;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;  
  
/** 
 * ViewHolder 优雅写法 
 *  
 * @author syt
 * 
 */  
public class ViewHolder {

    private final SparseArray<View> views;

    private View convertView;

    private ViewHolder(Context context, int layoutId) {
        super();
        this.views = new SparseArray<View>();

        this.convertView = View.inflate(context, layoutId, null);

        convertView.setTag(this);
    }
    /**
     * 获取 holder
     *
     * @param context
     * @param convertView   getView 的 convertView
     * @param layoutId      item View 的  布局文件 id
     * @return
     */
    public static ViewHolder getHolder(Context context, View convertView,int layoutId) {
//        Log.wtf("ViewHolder", "getHolder: "+convertView);
        if (null == convertView || null == convertView.getTag()) {
            return new ViewHolder(context, layoutId);
        }

        return (ViewHolder) convertView.getTag();
    }

    @SuppressWarnings("unchecked")
    public <T extends View>T getChildView(int viewId) {
        View childView = views.get(viewId);

        if (childView == null) {
            childView = convertView.findViewById(viewId);
            views.put(viewId, childView);
        }

        return (T) childView;
    }

    public View getConvertView() {
        return convertView;
    }
}
