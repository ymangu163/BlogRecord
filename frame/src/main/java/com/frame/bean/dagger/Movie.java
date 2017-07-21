package com.frame.bean.dagger;

/**
 * Created by gao on 2017/7/21.
 */

public class Movie {
    private String name;
    private String director;

    public Movie(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
