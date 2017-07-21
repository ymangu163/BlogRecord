package com.frame.component;

import com.frame.activity.DaggerActivity;
import com.frame.module.ActivityModule;

import dagger.Component;

/**
 * Created by gao on 2017/7/21.
 */

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = ApiComponent.class)
public interface ActivityComponent {
    void inject(DaggerActivity daggerActivity);
}
