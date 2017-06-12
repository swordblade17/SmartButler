package com.example.sword.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.entity.GirlData;
import com.example.sword.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * Created by sword on 2017/6/6.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private List<GirlData> mList;
    private LayoutInflater mInflater;
    private GirlData mGirlData;
    private WindowManager wm;
    private int width;

    public GridAdapter(Context context, List<GirlData> list) {
        mContext = context;
        mList = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.girl_item, null);
            viewHolder.mImageView = (ImageView) view.findViewById(R.id.imageview);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        mGirlData = mList.get(i);

        //解析图片
        String url = mGirlData.getImgUrl();
        PicassoUtils.loadImageViewSize(mContext,url,width/2,500,viewHolder.mImageView);

        return view;
    }


    class ViewHolder {
        private ImageView mImageView;
    }
}
