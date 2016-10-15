package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.SerieTreino;
import com.gymproject.app.models.UpdateSerieTreino;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SerieTreinoService {

    @GET("serie_treino/listar/{id}")
    Call<Status<List<SerieTreino>>> listar(@Path("id") String id);

    @POST("serie_treino/update")
    Call<Status> update(@Body List<UpdateSerieTreino> update);
}
