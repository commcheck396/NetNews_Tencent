package com.example.netnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.netnews.R;
import com.example.netnews.util.MySqliteOpenHelper;
import com.example.netnews.util.SPUtils;

import java.text.SimpleDateFormat;


public class OpenActivity extends AppCompatActivity {
    MySqliteOpenHelper helper = null;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        helper = new MySqliteOpenHelper(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Integer userId = (Integer) SPUtils.get(OpenActivity.this,SPUtils.USER_ID,0);
                Intent intent2 = new Intent();
                if (userId > 0) {
                    intent2.setClass(OpenActivity.this, MainActivity.class);
                }else {
                    intent2.setClass(OpenActivity.this, LoginActivity.class);
                }
                startActivity(intent2);
                finish();

            }
        }, 2000);
    }
}
