package com.frame.presenter;

import com.frame.activity.DaggerActivity;
import com.frame.bean.dagger.Movie;

import javax.inject.Inject;

/**
 * Created by gao on 2017/7/21.
 */

public class DaggerPresenter {
    DaggerActivity activity;
    Movie movie;

    @Inject
    public DaggerPresenter(DaggerActivity activity, Movie movie) {
        this.activity = activity;
        this.movie = movie;
    }

    public void showMovieName() {
        activity.showMovieName(movie.getName());
    }

}
