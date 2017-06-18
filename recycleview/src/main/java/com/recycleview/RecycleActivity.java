package com.recycleview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.recycleview.adapter.HomepagerRecycleAdapter;
import com.recycleview.bean.Headerbean;
import com.recycleview.bean.HomeCategory;
import com.recycleview.bean.RefreshBean;
import com.recycleview.utils.Constants;
import com.recycleview.utils.RefreshUtil;
import com.recycleview.wedget.MyStaggerGrildLayoutManger;

import java.util.ArrayList;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gao on 2017/6/8.
 */

public class RecycleActivity extends AppCompatActivity implements RefreshUtil.RefreshListenser {
    private Context mContext;
    private RefreshUtil refreshUtil;
    RecyclerView nRecycleView;
    MaterialRefreshLayout mRefreshLayout;
    private boolean flagFirst = true;
    private HomepagerRecycleAdapter homepagerRecycleAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        ButterKnife.bind(this);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh_layout);
        nRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mContext = this;
        initrefresh();
        initdata();
    }

    /**
     * 初始化刷新控件
     */
    private void initrefresh() {
        //用的是帮助类， 无需关心具体，可以用其他刷新控件
        refreshUtil = new RefreshUtil();
        refreshUtil.newBuilder(mContext).setRefreshLayout(mRefreshLayout)
                .setLoadMore(true).build(this);
    }

    public void initdata() {

        homepagerRecycleAdapter = new HomepagerRecycleAdapter(mContext);
        nRecycleView.setAdapter(homepagerRecycleAdapter);
        //这里不用自定义的流式布局也是可以的，这里这是根据特定需要简单自定义了一个
        nRecycleView.setLayoutManager(new MyStaggerGrildLayoutManger(mContext, 2, StaggeredGridLayoutManager.VERTICAL));

        //头部数据源
        getHeaderData();

        //获得分类数据源
        getCategoryData();

        //获取中间部分的数据源
        getCenterBean();

        //获取底部数据
        getRefreshData();
    }

    private void getHeaderData() {
        OkGo.get(Constants.HOST_SLIDLAYOUT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        s = "{" + "\"data\":" + s + "}";
                        Headerbean headerbean = new Gson().fromJson(s, Headerbean.class);
                        if (headerbean == null || headerbean.getData().size() == 0) {
                            return;
                        }
                        homepagerRecycleAdapter.setheaderbean(headerbean);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("ssss", "onError: " + e.toString());
                    }
                });
    }

    private void getCategoryData() {
        ArrayList<HomeCategory> homeCategories = new ArrayList<>();
        HomeCategory c1 = new HomeCategory(R.drawable.icon_cart, "购物车");
        HomeCategory c2 = new HomeCategory(R.drawable.icon_discover, "发现");
        HomeCategory c3 = new HomeCategory(R.drawable.icon_hot, "热门");
        HomeCategory c4 = new HomeCategory(R.drawable.icon_user, "寻找");
        homeCategories.add(c1);
        homeCategories.add(c2);
        homeCategories.add(c3);
        homeCategories.add(c4);
        homepagerRecycleAdapter.setCategoryBean(homeCategories);
    }

    private void getCenterBean() {

        OkGo.get(Constants.CAMPAIGN_HOME)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        s = "{" + "\"data\":" + s + "}";
                        RefreshBean refreshBean = new Gson().fromJson(s, RefreshBean.class);
                        if (refreshBean != null) {
                            if (refreshBean.getData().size() != 0) {
                                homepagerRecycleAdapter.setCenterBean(refreshBean);
                                refreshUtil.finishrefreshSleep();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("ssss", "onError: " + e.toString());
                        refreshUtil.finishrefresh();
                    }
                });
    }

    private void getRefreshData() {
        OkGo.get(Constants.CAMPAIGN_HOME)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        s = "{" + "\"data\":" + s + "}";
                        RefreshBean refreshBean = new Gson().fromJson(s, RefreshBean.class);
                        if (refreshBean != null) {
                            if (refreshBean.getData().size() != 0) {
                                homepagerRecycleAdapter.setRefreshBean(refreshBean, flagFirst);
                                if (flagFirst) {
                                    refreshUtil.finishrefreshSleep();
                                    flagFirst = false;
                                } else
                                    refreshUtil.finishrefresh();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("ssss", "onError: " + e.toString());
                        refreshUtil.finishrefresh();
                    }
                });
    }

    @Override
    public void refreshdata() {
        //刷新控件下拉刷新的回掉方法
        flagFirst = true;
        initdata();//此处是为了模拟，直接用了这一个接口数据源
    }

    @Override
    public void loadMore() {
        //刷新控件上拉加载的回掉方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    getRefreshData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}



