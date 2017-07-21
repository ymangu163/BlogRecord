package com.blog.record.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blog.record.R;
import com.blog.record.adapter.HomeAdapter;
import com.blog.record.view.DividerItemDecoration;
import com.frame.activity.DaggerActivity;
import com.frame.activity.RxJavaActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gao on 2017/6/19.
 */

public class FrameActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new HomeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        initData();
        mAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                handleClick(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mAdapter.removeData(position);
            }
        });
    }

    private void initData() {
        String[] array = getResources().getStringArray(R.array.frame_array);
        mDatas = Arrays.asList(array);
        mAdapter.setDatas(mDatas);
    }

    private void handleClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, RxJavaActivity.class);
                break;
            case 1:
                intent = new Intent(this, DaggerActivity.class);
                break;



        }
        startActivity(intent);

    }





}
