package com.example.sword.smartbutler.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.entity.WeChatData;
import com.example.sword.smartbutler.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sword on 2017/6/5.
 */

public class WeChatAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<WeChatData> mList;
    private WeChatData mData;
    int width,height;
    private WindowManager wm;


    public WeChatAdapter(Context context, List<WeChatData> list) {
        mContext = context;
        mList = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

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
            view = mInflater.inflate(R.layout.wechat_item, null);
            viewHolder.mImageView = (ImageView) view.findViewById(R.id.iv_img);
            viewHolder.mTitle = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.mSourceTV = (TextView) view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        mData = mList.get(i);
        viewHolder.mTitle.setText(mData.getTitle());
        viewHolder.mSourceTV.setText(mData.getSource());
        //加载图片
        if (!TextUtils.isEmpty(mData.getImgUrl())){
            PicassoUtils.loadImageViewSize(mContext,mData.getImgUrl(),width/3,200,viewHolder.mImageView);
        }




        return view;
    }

    class ViewHolder {
        private ImageView mImageView;
        private TextView mTitle;
        private TextView mSourceTV;
    }
}
