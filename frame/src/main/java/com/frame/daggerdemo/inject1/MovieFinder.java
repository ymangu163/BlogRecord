package com.frame.daggerdemo.inject1;

import com.frame.bean.dagger.Movie;

import java.util.List;

/**
 * Created by gao on 2017/7/21.
 */

public interface MovieFinder {
    List<Movie> findAll();
}
