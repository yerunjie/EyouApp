package com.fitibo.eyouapp.login.api;

import com.fitibo.eyouapp.login.request.LoginRequest;
import com.fitibo.eyouapp.login.response.AuthenticationResp;
import com.lemon.support.request.SimpleCall;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yerunjie on 17/11/22.
 */

public interface LoginApi {
    //登录
    @POST("signin")
    SimpleCall<AuthenticationResp> doLogin(@Body LoginRequest request);

}
