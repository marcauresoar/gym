package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Treino;
import com.gymproject.app.models.UpdateTreino;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TreinoService {

    @GET("treino/listar/{id}")
    Call<Status<List<Treino>>> listar(@Path("id") String id);

    @POST("treino/update")
    Call<Status> update(@Body List<UpdateTreino> updateTreino);
}
