package com.frame.agent;

import com.frame.component.ApiComponent;
import com.frame.component.DaggerApiComponent;
import com.frame.module.ApiModule;

/**
 * Created by gao on 2017/7/21.
 */

public class DaggerAgent {
    public static ApiComponent apiComponent;

    public static ApiComponent getApiComponent() {
        if (apiComponent == null) {
            apiComponent = DaggerApiComponent.builder().apiModule(new ApiModule()).build();
        }
       return apiComponent;
    }

}
