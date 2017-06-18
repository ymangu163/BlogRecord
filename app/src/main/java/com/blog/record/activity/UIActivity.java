package com.blog.record.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.blog.record.R;
import com.blog.record.adapter.HomeAdapter;
import com.blog.record.view.DividerItemDecoration;
import com.recycleview.RecycleActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gao on 2017/6/18.
 */

public class UIActivity extends AppCompatActivity {
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
                Toast.makeText(UIActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
                mAdapter.removeData(position);
            }
        });
    }


    private void initData() {
        String[] array = getResources().getStringArray(R.array.ui_array);
        mDatas = Arrays.asList(array);
//
//        for (int i = 'A'; i < 'z'; i++) {
//            mDatas.add("" + (char) i);
//        }
        mAdapter.setDatas(mDatas);
    }

    private void handleClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, RecycleActivity.class);

                break;




        }
        startActivity(intent);

    }
}
