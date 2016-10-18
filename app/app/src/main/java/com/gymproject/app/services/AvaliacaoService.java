package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Avaliacao;
import com.gymproject.app.models.UpdateAvaliacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AvaliacaoService {

    @GET("avaliacao/listar/{id}")
    Call<Status<List<Avaliacao>>> listar(@Path("id") String id);

    @POST("avaliacao/update")
    Call<Status> update(@Body List<UpdateAvaliacao> updateTreino);
}
