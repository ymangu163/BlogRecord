package com.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recycleview.R;
import com.recycleview.bean.HomeCategory;
import com.recycleview.wedget.AsImageTextView;

import java.util.List;

/**
 * Created by 666 on 2017/1/3.
 * 首页分类
 */
public class TypeCategoryAdapter extends RecyclerView.Adapter<TypeCategoryAdapter.TypetypeHolder> {

    private Context mContext;

    private List<HomeCategory> mHomeCategory;

    private LayoutInflater inflater;


    public TypeCategoryAdapter(Context mContext, List<HomeCategory> mHomeCategory) {
        this.mContext = mContext;
        this.mHomeCategory = mHomeCategory;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public TypetypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypetypeHolder(inflater.inflate(R.layout.item_homepageradapter_ivtv, null));
    }

    @Override
    public void onBindViewHolder(TypetypeHolder holder, int position) {
        HomeCategory homeCategory = mHomeCategory.get(position);
        holder.asivtvHomepageradapter.setTvImagetext(homeCategory.getTypename());
        holder.asivtvHomepageradapter.setIvImagetext(homeCategory.getImageid());
    }

    @Override
    public int getItemCount() {
        return mHomeCategory == null ? 0 : mHomeCategory.size();
    }

    //中间的四个type
    public class TypetypeHolder extends RecyclerView.ViewHolder {
        AsImageTextView asivtvHomepageradapter;

        public TypetypeHolder(View view) {
            super(view);
            asivtvHomepageradapter = (AsImageTextView) view.findViewById(R.id.asivtv_homepageradapter);
        }
    }
}
