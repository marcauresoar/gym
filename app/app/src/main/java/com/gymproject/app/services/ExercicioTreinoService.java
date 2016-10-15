package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.ExercicioTreino;
import com.gymproject.app.models.UpdateExercicioTreino;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExercicioTreinoService {

    @GET("exercicio_treino/listar/{id}")
    Call<Status<List<ExercicioTreino>>> listar(@Path("id") String id);

    @POST("exercicio_treino/update")
    Call<Status> update(@Body List<UpdateExercicioTreino> update);
}
