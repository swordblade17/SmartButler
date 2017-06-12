package com.example.sword.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.adapter.GridAdapter;
import com.example.sword.smartbutler.entity.GirlData;
import com.example.sword.smartbutler.utils.L;
import com.example.sword.smartbutler.utils.PicassoUtils;
import com.example.sword.smartbutler.utils.StaticClass;
import com.example.sword.smartbutler.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by sword on 2017/2/26.
 */

public class BeautyFragment extends Fragment {
    private GridView mGridView;
    //数据
    private List<GirlData> mList = new ArrayList<>();
    //适配器
    private GridAdapter mAdapter;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();
    //提示框
    private CustomDialog mDialog;
    //预览图片
    private ImageView mImage;
    //PhotoView
    private PhotoViewAttacher mAttacher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty, null);
        findView(view);

        return view;
    }

    private void findView(View view) {
        mGridView = (GridView) view.findViewById(R.id.mGridView);

        mDialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,R.layout.dialog_girl
                ,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        mImage = (ImageView) mDialog.findViewById(R.id.iv_img);

        String welfare = null;
        try {
            welfare = URLEncoder.encode("福利","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //解析
        RxVolley.get("http://gank.io/api/search/query/listview/category/"+welfare+"/count/50/page/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.e("Json:" + t);
                parsingJson(t);
            }
        });

        //监听点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //解析图片
                PicassoUtils.loadImageView(getActivity(),mListUrl.get(i),mImage);
                //缩放
                mAttacher = new PhotoViewAttacher(mImage);
                //刷新
                mAttacher.update();
                mDialog.show();
            }
        });
    }


    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);

                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);
            }
            mAdapter = new GridAdapter(getActivity(), mList);
            //设置适配器
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
