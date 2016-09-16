package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.UpdateExercicio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExercicioService {

    @GET("exercicio/listar/{id}")
    Call<Status<List<Exercicio>>> listar(@Path("id") String id);

    @POST("exercicio/update")
    Call<Status> update(@Body List<UpdateExercicio> update);
}
