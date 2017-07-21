package com.custom.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.utils.BitmapFillet;
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
    private ImageView roundIv;

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
        roundIv = (ImageView) findViewById(R.id.round_iv);

        leftImageView.setOnClickListener(this);
        rightImageView.setOnClickListener(this);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bleach_9);
        bitmap = BitmapFillet.fillet(BitmapFillet.ALL, bitmap, 22);
        roundIv.setImageBitmap(bitmap);

        roundIv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int vid = view.getId();
        if (vid == R.id.iv_left) {
            hsMain.setAnLeftOffset();
        } else if (vid == R.id.iv_right) {
            hsMain.setAnRightOffset();
        } else if (vid == R.id.round_iv) {
            Uri uri = Uri.parse("http://www.baidu.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
