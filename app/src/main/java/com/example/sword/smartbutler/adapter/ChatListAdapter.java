package com.example.sword.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.entity.ChatListData;

import java.util.List;

/**
 * Created by sword on 2017/6/2.
 */

public class ChatListAdapter extends BaseAdapter {
    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private LayoutInflater mInflater;
    private ChatListData mData;
    private List<ChatListData> mList;

    public ChatListAdapter(Context context,List<ChatListData> list){
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
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //获取当前要显示的type 根据这个type来区分数据的加载
        int type = getItemViewType(i);
        if (view == null){
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    view = mInflater.inflate(R.layout.left_item,null);
                    viewHolderLeftText.mLeftTV = (TextView) view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    view = mInflater.inflate(R.layout.right_item,null);
                    viewHolderRightText.mRightTV = (TextView) view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderRightText);
                    break;
            }
        }else {
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) view.getTag();
                    break;
            }
        }

        //赋值
        ChatListData data = mList.get(i);
        switch (type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.mLeftTV.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.mRightTV.setText(data.getText());
                break;
        }


        return view;
    }
    //根据数据源的position返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }
    //返回所有的layout数据
    @Override
    public int getViewTypeCount() {
        return 3;//mList.size+1
    }

    //左边的文本
    class ViewHolderLeftText{
        private TextView mLeftTV;
    }

    //右边的文本
    class ViewHolderRightText{
        private TextView mRightTV;
    }
}
