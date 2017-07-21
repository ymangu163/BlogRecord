package com.frame.component;

import com.frame.bean.dagger.Movie;
import com.frame.module.ApiModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by gao on 2017/7/21.
 */

@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    OkHttpClient getClient();

    Retrofit getRetrofit();

    Movie getMovie();
}
