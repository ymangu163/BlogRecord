package com.recycleview.utils;

import android.content.Context;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

/**
 * Created by gao on 2017/6/8.
 */

public class RefreshUtil {

    private Builder builder;
    public Builder newBuilder(Context context) {
        builder = new Builder(context);
        return builder;
    }

    private void initRefreshLayout() {
        builder.mRefreshLayout.setLoadMore(builder.canLoadMore);
        builder.mRefreshLayout.setIsOverLay(builder.isOverLay);
        builder.mRefreshLayout.setWaveShow(builder.isWaveShow);
        builder.mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                if (mRefreshListenser != null) {
                    mRefreshListenser.refreshdata();
                }
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if (mRefreshListenser != null) {
                    mRefreshListenser.loadMore();
                }
            }
        });
    }

    //结束刷新
    public void finishrefresh() {
        builder.mRefreshLayout.finishRefresh();
        builder.mRefreshLayout.finishRefreshLoadMore();
    }

    public void finishrefreshSleep() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    builder.mRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            //结束刷新
                            finishrefresh();

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    builder.mRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            //结束刷新
                            finishrefresh();

                        }
                    });
                }
            }
        }).start();
    }


    public class Builder {
        private Context mContext;
        private boolean isOverLay = true;//是否入侵
        private boolean isWaveShow = false;
        private boolean canLoadMore;//能否加载更多
        private MaterialRefreshLayout mRefreshLayout;

        public Builder(Context context) {
            mContext = context;
        }
        public Builder setOverLay(boolean overLay) {
            isOverLay = overLay;
            return this;
        }

        public Builder setWaveShow(boolean isWaveShow) {
            this.isWaveShow = isWaveShow;
            return this;
        }

        public Builder setLoadMore(boolean loadMore) {
            this.canLoadMore = loadMore;
            return this;
        }

        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout) {
            this.mRefreshLayout = refreshLayout;
            return this;
        }

        public void build(RefreshListenser listener) {
            mRefreshListenser = listener;
            valid();
            initRefreshLayout();
        }

        //异常情况
        private void valid() {
            if (this.mContext == null) {
                throw new RuntimeException("content can't be null");
            }
            if (this.mRefreshLayout == null) {
                throw new RuntimeException("MaterialRefreshLayout can't be  null");
            }
        }
    }
    private RefreshListenser mRefreshListenser;
    public interface RefreshListenser {
        void refreshdata();
        void loadMore();
    }
}
