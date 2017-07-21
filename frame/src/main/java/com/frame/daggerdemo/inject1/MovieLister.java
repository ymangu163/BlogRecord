package com.frame.daggerdemo.inject1;


import com.frame.bean.dagger.Movie;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gao on 2017/7/21.
 */
/*
    依赖注入原理
    http://codethink.me/2015/08/01/dependency-injection-theory/
 */
public class MovieLister implements InjectFinder{
    private MovieFinder finder;
    public MovieLister() {
        /**
         * 在MovieLister中创建MovieFinderImpl的方式，使得MovieLister不仅仅依赖于MovieFinder这个接口，它还依赖于MovieListImpl这个实现
         * 和硬编码一样，导致耦合
         ***/
        finder = new MovieFinderImpl();
    }

    /**
     * 依赖注入方法一：
     *  MovieFinderImpl的实现在MovieLister类之外创建。这样，MovieLister就只依赖于我们定义的MovieFinder接口，
     * @param finder
     */
    public MovieLister(MovieFinder finder) {
        this.finder = finder;
    }

    /**
     * 方法二：setter 方法
     * @param finder
     */
    public void setFinder(MovieFinder finder) {
        this.finder = finder;
    }



    public Movie[] moviesDirectedBy(String name) {
        List<Movie> allMovies = finder.findAll();
        for (Iterator it = allMovies.iterator(); it.hasNext();) {
            Movie movie = (Movie) it.next();
            if (!movie.getDirector().equals(name)) it.remove();
        }
        return allMovies.toArray(new Movie[allMovies.size()]);
    }


    /**
     * 方法三：接口注入
     * @param finder
     */
    @Override
    public void injectFinder(MovieFinder finder) {
        this.finder = finder;
    }
}
