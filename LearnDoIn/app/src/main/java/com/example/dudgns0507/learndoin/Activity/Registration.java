package com.example.dudgns0507.learndoin.Activity;

import com.example.dudgns0507.learndoin.Activity.model.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by pyh42 on 2017-03-23.
 */

public interface Registration {
    @FormUrlEncoded
    @POST("/api/signup")
    Call<Result> registration(@Field("id") String id, @Field("pw") String pw, @Field("email") String email, @Field("name") String name);
}
