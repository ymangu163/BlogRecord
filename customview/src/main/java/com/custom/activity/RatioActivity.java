package com.custom.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.custom.view.HSelectView;
import com.custom.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gao on 2017/6/22.
 */

public class RatioActivity extends AppCompatActivity implements View.OnClickListener {
    private View leftImageView;
    private View rightImageView;
    private HSelectView hsMain;
    private Button btMain;
    List<String> strings = new ArrayList<String>();
    private TextView tvMain;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ration);
        initView();
        initdata();

    }

    private void initdata() {

        for (int i = 0; i < 20; i++) {
            strings.add(i + "00");
        }
        hsMain.setData(strings);
    }

    private void initView() {
        hsMain = (HSelectView) findViewById(R.id.hd_main);
        leftImageView = findViewById(R.id.iv_left);
        rightImageView = findViewById(R.id.iv_right);

        leftImageView.setOnClickListener(this);
        rightImageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int vid = view.getId();
        if (vid == R.id.iv_left) {
            hsMain.setAnLeftOffset();
        } else if (vid == R.id.iv_right) {
            hsMain.setAnRightOffset();
        }
    }
}
