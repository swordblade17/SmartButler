package com.example.sword.smartbutler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sword.smartbutler.fragment.BeautyFragment;
import com.example.sword.smartbutler.fragment.BulterFragment;
import com.example.sword.smartbutler.fragment.UserFragment;
import com.example.sword.smartbutler.fragment.WechatFragment;
import com.example.sword.smartbutler.ui.SettingActivity;
import com.example.sword.smartbutler.utils.ShareUtil;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TabLayout mTabLayout;

    private ViewPager mViewPager;
    //Title
    private List<String>mTitle;

    private List<Fragment>mFragments;

    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();
    }
    //初始化数据
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.butler));
        mTitle.add(getString(R.string.wechat));
        mTitle.add(getString(R.string.beauty));
        mTitle.add(getString(R.string.user_info));

        mFragments = new ArrayList<>();
        mFragments.add(new BulterFragment());
        mFragments.add(new WechatFragment());
        mFragments.add(new BeautyFragment());
        mFragments.add(new UserFragment());
    }
    //初始化view
    private void initView(){
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_setting);
        mFloatingActionButton.setVisibility(View.GONE);
        mFloatingActionButton.setOnClickListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragments.size());

        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG,"position"+position);
                if(position == 0){
                    mFloatingActionButton.setVisibility(View.GONE);
                }else {
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

           //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
            //返回item的个数
            @Override
            public int getCount() {
                return mFragments.size();
            }


            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
