package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.UpdateFicha;
import com.gymproject.app.models.Ficha;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FichaService {

    @GET("ficha/listar/{id}")
    Call<Status<List<Ficha>>> listar(@Path("id") String id);

    @POST("ficha/update")
    Call<Status> update(@Body List<UpdateFicha> updateFicha);
}
