package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Ficha;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FichaService {

    @GET("ficha/listar/{id}")
    Call<Status<List<Ficha>>> listarFichas(@Path("id") String id);

}
