package com.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recycleview.R;
import com.recycleview.bean.RefreshBean;
import com.recycleview.utils.ImageUtils;

import java.util.List;

/**
 * Created by 丁瑞 on 2017/1/3.
 * 首页热议
 */
public class TypeHistoryAdapter extends RecyclerView.Adapter<TypeHistoryAdapter.TypeHistoryHolder> {

    private Context mContext;

    private List<RefreshBean.DataBean> mHomehopspot;

    private LayoutInflater inflater;


    public TypeHistoryAdapter(Context mContext, List<RefreshBean.DataBean> mHomeCategory) {
        this.mContext = mContext;
        this.mHomehopspot = mHomeCategory;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public TypeHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeHistoryHolder(inflater.inflate(R.layout.item_raiders, parent, false));
    }

    @Override
    public void onBindViewHolder(TypeHistoryHolder holder, int position) {
        RefreshBean.DataBean contentBean = mHomehopspot.get(position);
        ImageUtils.load(mContext, contentBean.getCpThree().getImgUrl(), holder.homeReadPivIv);
        holder.homeReadTitle.setText("#" + contentBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return mHomehopspot == null ? 0 : mHomehopspot.size();
    }

    public class TypeHistoryHolder extends RecyclerView.ViewHolder {
        ImageView homeReadPivIv;
        TextView homeReadTitle;


        public TypeHistoryHolder(View view) {
            super(view);
            homeReadTitle = (TextView) view.findViewById(R.id.home_read_title);
            homeReadPivIv = (ImageView) view.findViewById(R.id.home_read_piv_iv);
        }
    }


}
