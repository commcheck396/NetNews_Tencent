package com.example.netnews.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netnews.MyApplication;
import com.example.netnews.R;
import com.example.netnews.fragment.NewsFragment;
import com.example.netnews.fragment.UserFragment;
import com.example.netnews.util.NetworkReceiver;

/**
 * 主页面
 */
public class MainActivity extends Activity {
    private Activity myActivity;
    private LinearLayout llContent;
    private TextView tvTitle;
    private RadioButton rbNews;
    private RadioButton rbUser;
    private NetworkReceiver mNetReceiver;
    private Fragment[] fragments = new Fragment[]{null, null};//存放Fragment

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_main);
        MyApplication.Instance.setMainActivity(myActivity);
        llContent =  (LinearLayout) findViewById(R.id.ll_main_content);
        tvTitle =  (TextView) findViewById(R.id.title);
        rbNews = (RadioButton) findViewById(R.id.rb_main_news);
        rbUser = (RadioButton) findViewById(R.id.rb_main_user);
        initView();
        setViewListener();
        initNetworkReceiver();
    }

    private void initNetworkReceiver() {
        mNetReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetReceiver, filter);
    }

    private void setViewListener() {
        rbNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText("新闻");
                switchFragment(0);
            }
        });
        rbUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText("我的");
                switchFragment(1);
            }
        });
    }

    private void initView() {
        Drawable iconNews=getResources().getDrawable(R.drawable.selector_main_rb_news);
        iconNews.setBounds(0,0,68,68);
        rbNews.setCompoundDrawables(null,iconNews,null,null);
        rbNews.setCompoundDrawablePadding(5);
        Drawable iconUser=getResources().getDrawable(R.drawable.selector_main_rb_user);
        iconUser.setBounds(0,0,55,55);
        rbUser.setCompoundDrawables(null,iconUser,null,null);
        rbUser.setCompoundDrawablePadding(5);
        switchFragment(0);
        rbNews.setChecked(true);
    }

    private void switchFragment(int fragmentIndex) {
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragments[fragmentIndex] == null) {
            switch (fragmentIndex) {
                case 0://NewsFragment
                    fragments[fragmentIndex] = new NewsFragment();
                    break;
                case 1://UserFragment
                    fragments[fragmentIndex] = new UserFragment();
                    break;
            }

            transaction.add(R.id.ll_main_content, fragments[fragmentIndex]);
        }

        for (int i = 0; i < fragments.length; i++) {
            if (fragmentIndex != i && fragments[i] != null) {
                transaction.hide(fragments[i]);
            }
        }
        transaction.show(fragments[fragmentIndex]);

        transaction.commit();
    }
    /**
     * 双击退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }

        return false;
    }

    private long time = 0;

    public void exit() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(myActivity,"再点击一次退出应用程序", Toast.LENGTH_LONG).show();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetReceiver);
    }
}
