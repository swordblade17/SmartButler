package com.example.sword.smartbutler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.entity.CourierData;
import com.example.sword.smartbutler.entity.CourierData.ResultBean.ListBean;

import java.util.List;

/**
 * Created by sword on 2017/5/27.
 */

public class CourierAdapter extends BaseAdapter {
    private Context mContext;
    private List<ListBean> mList;
    private LayoutInflater mInflater;
    private ListBean mData;

    public CourierAdapter( Context context,List<ListBean> list){
        mContext = context;
        mList = list;
        //获取系统服务
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.mTVRemark = (TextView) view.findViewById(R.id.tv_remark);
            viewHolder.mTVZone = (TextView) view.findViewById(R.id.tv_zone);
            viewHolder.mTVDate = (TextView) view.findViewById(R.id.tv_date_time);
            //设置缓存
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //设置数据
        mData = mList.get(i);
        viewHolder.mTVRemark.setText(mData.getRemark());
        viewHolder.mTVZone.setText(mData.getZone());
        viewHolder.mTVDate.setText(mData.getDatetime());

        return view;
    }

    class ViewHolder{
        private TextView mTVRemark;
        private TextView mTVZone;
        private TextView mTVDate;

    }
}
