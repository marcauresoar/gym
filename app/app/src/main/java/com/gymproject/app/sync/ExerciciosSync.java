package com.gymproject.app.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gymproject.app.classes.Status;
import com.gymproject.app.dao.ExercicioDao;
import com.gymproject.app.dao.FichaDao;
import com.gymproject.app.dao.UpdateExercicioDao;
import com.gymproject.app.dao.UpdateFichaDao;
import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.models.UpdateExercicio;
import com.gymproject.app.models.UpdateFicha;
import com.gymproject.app.restful.RestfulAPI;
import com.gymproject.app.services.ExercicioService;
import com.gymproject.app.services.FichaService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncRequestEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.SessionUtils;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ExerciciosSync extends AbsSync {

    Context context;

    ExerciciosSync(@NonNull Context context) {
        super(context);
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.EXERCICIOS;
    }

    @Override
    protected void post() {

        SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS);

        Log.i("ExerciciosSync", "execute post method");

        List<UpdateExercicio> updates = UpdateExercicioDao.getAll(Realm.getDefaultInstance());

        ExercicioService apiService =
                RestfulAPI.getClient().create(ExercicioService.class);

        Call<Status> call = apiService.update(updates);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status>call, Response<Status> response) {
                Status status = response.body();
                if(status != null) {
                    Log.e("updateExercicios", status.getMensagem());
                } else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                UpdateExercicioDao.removeAll();
                get();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.e("updateExercicios", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });

    }

    @Override
    protected void get() {
        Log.i("ExerciciosSync", "execute get method");

        ExercicioService apiService =
                RestfulAPI.getClient().create(ExercicioService.class);

        Call<Status<List<Exercicio>>> call = apiService.listar(SessionUtils.getInstance(context).getIdUsuario());
        call.enqueue(new Callback<Status<List<Exercicio>>>() {
            @Override
            public void onResponse(Call<Status<List<Exercicio>>>call, Response<Status<List<Exercicio>>> response) {
                Status status = response.body();
                if(status != null) {
                    if(status.getCodigo() == 1) {
                        List<Exercicio> itens = (List<Exercicio>) status.getDados();
                        ExercicioDao.removeAll();
                        ExercicioDao.save(itens);
                    }
                }else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
                SyncService.request(SyncType.SERIES);
            }

            @Override
            public void onFailure(Call<Status<List<Exercicio>>> call, Throwable t) {
                Log.e("listarExercicios", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });
    }

}
