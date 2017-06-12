package com.example.sword.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.adapter.WeChatAdapter;
import com.example.sword.smartbutler.entity.WeChatData;
import com.example.sword.smartbutler.ui.WebViewActivity;
import com.example.sword.smartbutler.utils.L;
import com.example.sword.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sword on 2017/2/26.
 */

public class WechatFragment extends Fragment {
    private ListView mListView;
    private List<WeChatData> mList;

    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat,null);
        mList = new ArrayList<>();
        findView(view);

        return view;
    }
    //初始化view
    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);

        //解析接口
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY + "&ps=100";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                L.i("wechat json:" + t);
                parsingJson(t);
            }
        });

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                L.i("position"+i);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title",mListTitle.get(i));
                intent.putExtra("url",mListUrl.get(i));
                startActivity(intent);
            }
        });



    }



    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            JSONArray jsonList = jsonresult.getJSONArray("list");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                WeChatData data = new WeChatData();

                String titlr = json.getString("title");
                String url = json.getString("url");

                data.setTitle(titlr);
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));

                mList.add(data);

                mListTitle.add(titlr);
                mListUrl.add(url);
            }
            WeChatAdapter adapter = new WeChatAdapter(getActivity(), mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
