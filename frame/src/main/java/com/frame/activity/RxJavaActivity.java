package com.frame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.common.utils.DebugLog;
import com.frame.R;
import com.frame.rxdemo.ChapterFour;


/**
 * Created by gao on 2017/7/7.
 */

public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "RxJavaActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        findViewById(R.id.rx_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DebugLog.d(TAG,"RxJavaActivity");
        ChapterFour.demo1();
    }
}
