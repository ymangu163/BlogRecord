package com.frame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.common.utils.DebugLog;
import com.frame.R;
import com.frame.agent.DaggerAgent;
import com.frame.component.ApiComponent;
import com.frame.component.DaggerActivityComponent;
import com.frame.module.ActivityModule;
import com.frame.presenter.DaggerPresenter;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * Created by gao on 2017/7/21.
 */

public class DaggerActivity extends AppCompatActivity {

    private TextView nameTv;

    @Inject
    DaggerPresenter presenter;

    @Inject
    OkHttpClient client;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        nameTv = (TextView) findViewById(R.id.name_tv);
        inject();

        presenter.showMovieName();
        DebugLog.d("gao", "client = " + (client == null ? "null" : client));
        DebugLog.d("gao", "retrofit = " + (retrofit == null ? "null" : retrofit));
    }

    private void inject() {
        ApiComponent apiComponent = DaggerAgent.getApiComponent();
        DaggerActivityComponent.builder().apiComponent(apiComponent)
                .activityModule(new ActivityModule(this))
                .build().inject(this);

    }

    public void showMovieName(String name) {
        nameTv.setText(name);
    }
}
