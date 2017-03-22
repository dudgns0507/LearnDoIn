package com.example.dudgns0507.learndoin.Activity;

import com.example.dudgns0507.learndoin.Activity.model.UserData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by pyh42 on 2017-03-22.
 */

public interface Login {
    @FormUrlEncoded
    @POST("/api/signin")
    Call<UserData> login(@Field("id") String id, @Field("pw") String pw);
}
