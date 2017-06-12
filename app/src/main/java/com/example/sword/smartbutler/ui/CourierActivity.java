package com.example.sword.smartbutler.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sword.smartbutler.R;
import com.example.sword.smartbutler.adapter.CourierAdapter;
import com.example.sword.smartbutler.entity.CourierData;
import com.example.sword.smartbutler.entity.CourierData.ResultBean.ListBean;
import com.example.sword.smartbutler.utils.L;
import com.example.sword.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class CourierActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mNameET;
    private EditText mNumET;
    private Button mCourierBtn;
    private ListView mListView;

    List<ListBean> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }
    //初始化View
    private void initView() {
        mNameET = (EditText) findViewById(R.id.et_name);
        mNumET = (EditText) findViewById(R.id.et_number);
        mCourierBtn = (Button) findViewById(R.id.btn_get_courier);
        mListView = (ListView) findViewById(R.id.mListView);
        mCourierBtn.setOnClickListener(this);
        mList = new ArrayList<>();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get_courier:
                //1.获取输入框的内容
                String name = mNameET.getText().toString().trim();
                String num = mNumET.getText().toString().trim();

                String url = "http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY
                        +"&com="+name+"&no="+num;
                //2.判断是否为空
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(num)){
                    //3.请求数据
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CourierActivity.this,t,Toast.LENGTH_SHORT).show();
                            L.i("json:"+t);
                            //4.解析json
                            parsingJson(t);
                        }
                    });
                }else {
                    Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                }


                //5.listview适配器
                //6.实体类
                //7.设置数据显示效果

                break;
        }
    }
    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                ListBean listBean = new ListBean();
                listBean.setRemark(json.getString("remark"));
                listBean.setZone(json.getString("zone"));
                listBean.setDatetime(json.getString("datetime"));
                mList.add(listBean);
            }
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
