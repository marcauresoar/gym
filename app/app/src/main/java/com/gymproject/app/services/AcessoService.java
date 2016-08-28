package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Usuario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AcessoService {

    @FormUrlEncoded
    @POST("acesso/autenticarLogin")
    Call<Status<Usuario>> autenticarLogin(@Field("email") String  email, @Field("senha") String  senha);

    @FormUrlEncoded
    @POST("acesso/criarConta")
    Call<Status<Usuario>> criarConta(@Field("nome") String  nome, @Field("email") String  email, @Field("senha") String  senha);

}
