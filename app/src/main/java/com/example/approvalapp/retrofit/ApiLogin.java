package com.example.approvalapp.retrofit;


import com.example.approvalapp.Model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiLogin {
//  http://localhost:8081/Login?USERNO=1000&PASSWORD=000
    @GET("Login")
    Call<UserInfo> gaUserInfo(@Query("USERNO") String userNo, @Query("PASSWORD") String pass   );

}
