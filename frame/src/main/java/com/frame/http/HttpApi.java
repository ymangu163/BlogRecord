package com.frame.http;

import com.frame.bean.rxjava.LoginRequest;
import com.frame.bean.rxjava.LoginResponse;
import com.frame.bean.rxjava.RegisterRequest;
import com.frame.bean.rxjava.RegisterResponse;
import com.frame.bean.rxjava.UserBaseInfoRequest;
import com.frame.bean.rxjava.UserBaseInfoResponse;
import com.frame.bean.rxjava.UserExtraInfoRequest;
import com.frame.bean.rxjava.UserExtraInfoResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by gao on 2017/7/8.
 */

public interface HttpApi {
    //一定要有数据，没有 可以用'/'或'.'
    @POST("/")
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET("/")
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @GET
    Observable<UserBaseInfoResponse> getUserBaseInfo(@Body UserBaseInfoRequest request);

    @GET
    Observable<UserExtraInfoResponse> getUserExtraInfo(@Body UserExtraInfoRequest request);

    @GET("v2/movie/top250")
    Observable<Response<ResponseBody>> getTop250(@Query("start") int start, @Query("count") int count);
}
