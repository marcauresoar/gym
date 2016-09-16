package com.gymproject.app.services;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Serie;
import com.gymproject.app.models.UpdateSerie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SerieService {

    @GET("serie/listar/{id}")
    Call<Status<List<Serie>>> listar(@Path("id") String id);

    @POST("serie/update")
    Call<Status> update(@Body List<UpdateSerie> update);
}
