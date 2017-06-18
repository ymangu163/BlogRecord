package com.recycleview.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.recycleview.R;
import com.recycleview.bean.Headerbean;
import com.recycleview.bean.HomeCategory;
import com.recycleview.bean.RefreshBean;
import com.recycleview.utils.ImageUtils;
import com.recycleview.wedget.AsHomepageHeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gao on 2017/6/8.
 */

public class HomepagerRecycleAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private LayoutInflater inflater;
    private RecyclerView recyclerView;
    private int count = 3;
    private List<Headerbean.DataBean> headerData;
    private List<RefreshBean.DataBean> refreshbean;
    private List<RefreshBean.DataBean> centerBean;
    private ArrayList<HomeCategory> mHomeCategories;

    private List<Integer> mHeights = new ArrayList<>();
    private final int TYPE_TOP = 1;//头部布局
    private final int TYPE_CENTER = 2;//
    private final int TYPE_CATEGORY = 3;//中间的四个快速入口
    private final int TYPE_HEADER = 4;//每个分类的head
    private int REFRESHPOSITION = 5;//下部head的位置
    private final int TYPE_REFRESH = 6;//最下面的布局
    private int CENTERPOSITION;//中间head的位置

    public HomepagerRecycleAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        //初始化各我数据源
        headerData = new ArrayList<>();
        refreshbean = new ArrayList<>();
        centerBean = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            //头部轮播图
            View viewtop = inflater.inflate(R.layout.adapter_slider, parent, false);
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) viewtop.getLayoutParams();
            params.setFullSpan(true);//最为重要的一个方法，占满全屏,以下同理
            viewtop.setLayoutParams(params);
            return new TypeTopsliderHolder(viewtop);
        } else if (viewType == TYPE_HEADER) {

            View view2 = inflater.inflate(R.layout.item_homepagertypeheader_type, parent, false);

            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) view2.getLayoutParams();
            params.setFullSpan(true);
            view2.setLayoutParams(params);
            return new TypeheadHolder(view2);
        } else if (viewType == TYPE_CENTER) {
            //中间head下面的布局
            View view = inflater.inflate(R.layout.itam_homepageradapter_rv2, parent, false);
            StaggeredGridLayoutManager.LayoutParams params2 =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            params2.setFullSpan(true);
            view.setLayoutParams(params2);
            return new TypetypeHolder2(view);

        } else if (viewType == TYPE_CATEGORY) {
//四个快速入口的holder
//这里的TypetypeHolder和上面的TypetypeHolder2 其实可以写成一个holder，这里为了简单，避免引起复用带来的问题，分开了
            View view = inflater.inflate(R.layout.itam_homepageradapter_rv2, parent, false);
            StaggeredGridLayoutManager.LayoutParams params2 =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            params2.setFullSpan(true);
            view.setLayoutParams(params2);
            return new TypetypeHolder(view);

        } else if (viewType == TYPE_REFRESH) {
            return new TypeRefresh(inflater.inflate(R.layout.item_raiders2, parent, false));
        } else {

            View viewtop = inflater.inflate(R.layout.adapter_slider, parent, false);
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) viewtop.getLayoutParams();
            params.setFullSpan(true);
            viewtop.setLayoutParams(params);
            return new TypeTopsliderHolder(viewtop);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeTopsliderHolder && headerData.size() != 0 && ((TypeTopsliderHolder) holder).linearLayout.getChildCount() == 0) {
            //如果是TypeTopsliderHolder， 并且header有数据，并且TypeTopsliderHolder的linearLayout没有子view（因为这个布局只出现一次，如果他没有子view，
            // 也就是他是第一次加载，才加载数据）
            initslider(((TypeTopsliderHolder) holder), headerData);//加载头部数据源
        }  else if (holder instanceof TypetypeHolder && centerBean.size() != 0) {
            initcategory(((TypetypeHolder) holder));//加载四个category数据源
        } else if (holder instanceof TypeheadHolder) {
            initTop(((TypeheadHolder) holder), position);//加载heade数据源（其实这里可以每个head单独设置，因为有的需求head去各式各样）
        } else if (holder instanceof TypetypeHolder2 && centerBean.size() != 0) {
            initCenterBean(((TypetypeHolder2) holder));//加载中间head下面的数据源
        } else if (holder instanceof TypeRefresh && refreshbean.size() != 0) {
            initrefreshdata(((TypeRefresh) holder), position - REFRESHPOSITION - 1);//加载瀑布流数据源
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        //此处是根据数据源有无数据来判定分类条的位置；可自行拓展，自由发挥
        CENTERPOSITION = mHomeCategories.size() == 0 ? 1 : 2;
        REFRESHPOSITION = centerBean.size() == 0 ? 3 : 4;

        Log.e("getItemViewType", "getItemViewType: " + CENTERPOSITION + ",:" + REFRESHPOSITION);

        if (position == 0) {
            return TYPE_TOP;
        } else if (position == CENTERPOSITION || position == REFRESHPOSITION) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_CATEGORY;
        } else if (position == CENTERPOSITION + 1) {
            return TYPE_CENTER;
        } else {
            return TYPE_REFRESH;
        }
    }

    private void initrefreshdata(TypeRefresh holder, int position) {
        Log.e("position", "initrefreshdata: " + position);
        if (mHeights.size() <= getItemCount() + 2) {
            //这里只是随机数模拟瀑布流， 实际过程中， 应该根据图片高度来实现瀑布流
            mHeights.add((int) (500 + Math.random() * 400));
        }

        ViewGroup.LayoutParams layoutParams = holder.homeReadPivIv.getLayoutParams();
        if (mHeights.size() > position)
            //此处取得随机数，如果mheight里面有数则取， 没有则邹走else
            layoutParams.height = mHeights.get(position);
        else layoutParams.height = 589;
        holder.homeReadPivIv.setLayoutParams(layoutParams);

        holder.homeReadPivIv.setScaleType(ImageView.ScaleType.FIT_XY);
        if (refreshbean.size() > position) {
            ImageUtils.load(mContext, refreshbean.get(position).getCpOne().getImgUrl(), holder.homeReadPivIv);
        } else {
            ImageUtils.load(mContext, refreshbean.get(0).getCpTwo().getImgUrl(), holder.homeReadPivIv);
        }

    }

    private void initslider(TypeTopsliderHolder holder, List<Headerbean.DataBean> data) {
        LinearLayout linearLayout = holder.linearLayout;
        for (int i = 0; i < data.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.rightMargin = 40;
            imageView.setLayoutParams(layoutParams);
            ImageUtils.load(mContext, data.get(i).getImgUrl(), imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            linearLayout.addView(imageView);
        }
    }

    private void initCenterBean(TypetypeHolder2 holder) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, 2));

        TypeHistoryAdapter centerAdapter = new TypeHistoryAdapter(mContext, centerBean);


        holder.rvtype.setAdapter(centerAdapter);


    }

    private void initcategory(TypetypeHolder holder) {

        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, mHomeCategories.size()));

        TypeCategoryAdapter categoryAdapter = new TypeCategoryAdapter(mContext, mHomeCategories);

        holder.rvtype.setAdapter(categoryAdapter);


    }

    private void initTop(TypeheadHolder holder, int position) {
        if (position == CENTERPOSITION) {
            holder.hview.setTypeName("中间head");

        } else if (position == REFRESHPOSITION) {
            holder.hview.setTypeName("下部head");

        }

    }


    public void setheaderbean(Headerbean headerbean) {
        headerData.clear();
        headerData.addAll(headerbean.getData());
        notifyDataSetChanged();
    }

    public void setRefreshBean(RefreshBean refreshBean, boolean flagFirst) {
        refreshbean.addAll(refreshBean.getData());
        int count1 = this.count;
        this.count += refreshBean.getData().size();
        notifyDataSetChanged();
        if (!flagFirst) {
            recyclerView.smoothScrollToPosition(count1 + 2);//加载完以后向上滚动3个条目
        }
    }

    public void setCenterBean(RefreshBean refreshBean) {
        centerBean = refreshBean.getData();
        count++;
        notifyDataSetChanged();
    }

    public void setCategoryBean(ArrayList<HomeCategory> homeCategories) {
        mHomeCategories = homeCategories;
        count++;
        notifyDataSetChanged();
    }

    //头部Viewpager viewholder
    public class TypeTopsliderHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        public TypeTopsliderHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.ll_slider);
        }
    }

    public class TypeheadHolder extends RecyclerView.ViewHolder {

        AsHomepageHeaderView hview;

        public TypeheadHolder(View view) {
            super(view);
            hview = (AsHomepageHeaderView) view.findViewById(R.id.ashv_homepager);
            hview.setMoreclicklistenser(new AsHomepageHeaderView.MoreclickListenser() {
                @Override
                public void setmoreclicklistenser() {
                }
            });
        }
    }

    public class TypetypeHolder2 extends RecyclerView.ViewHolder {
        RecyclerView rvtype;

        public TypetypeHolder2(View view) {
            super(view);
            rvtype = (RecyclerView) view.findViewById(R.id.rv_homepageradapter_artist);
        }
    }

    //中间的四个type
    public class TypetypeHolder extends RecyclerView.ViewHolder {
        RecyclerView rvtype;

        public TypetypeHolder(View view) {
            super(view);
            rvtype = (RecyclerView) view.findViewById(R.id.rv_homepageradapter_artist);
        }
    }

    static class TypeRefresh extends RecyclerView.ViewHolder {
        ImageView homeReadPivIv;

        TypeRefresh(View view) {
            super(view);
            homeReadPivIv = (ImageView) view.findViewById(R.id.home_read_piv_iv);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

       recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               if (((Activity)mContext).isFinishing()) {
                   return;
               }
               //如果快速滑动， 不加载图片
               if (newState == 2) {
                   Glide.with(mContext).pauseRequests();
               } else {
                   Glide.with(mContext).resumeRequests();

               }
           }

           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
           }
       });


    }
}
