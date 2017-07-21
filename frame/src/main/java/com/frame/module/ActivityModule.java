package com.frame.module;

import com.frame.activity.DaggerActivity;
import com.frame.bean.dagger.Movie;
import com.frame.presenter.DaggerPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gao on 2017/7/21.
 */
@Module
public class ActivityModule {
    private DaggerActivity activity;

    public ActivityModule(DaggerActivity activity) {
        this.activity = activity;
    }

    @Provides
    public DaggerActivity provideActivity() {
        return activity;
    }

//    @Provides
//    public Movie provideMovie() {
//        return new Movie("movie from ActivityModule");
//    }

    @Provides
    public DaggerPresenter provideDaggerPresenter(DaggerActivity activity, Movie movie) {
        return new DaggerPresenter(activity, movie);
    }

}
