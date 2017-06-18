package com.blog.record.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blog.record.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gao on 2017/6/18.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> mDatas;

    public HomeAdapter(Context context) {
        this.context = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_home_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setDatas(List<String> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        if (position >= mDatas.size()) {
            return;
        }
        mDatas.remove(position);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        private MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);

            // 如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getLayoutPosition();
                        mOnItemClickLitener.onItemClick(v, pos);
                    }
                });

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(v, pos);
                        return false;
                    }
                });
            }
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}