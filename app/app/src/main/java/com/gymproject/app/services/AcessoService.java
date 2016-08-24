package com.gymproject.app.services;

import com.gymproject.app.classes.Status;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AcessoService {

    @FormUrlEncoded
    @POST("acesso/autenticarLogin")
    Call<Status> autenticarLogin(@Field("email") String  email, @Field("senha") String  senha);

}
