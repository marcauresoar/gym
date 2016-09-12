package com.gymproject.app.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.UpdateFicha;
import com.gymproject.app.dao.FichaDao;
import com.gymproject.app.dao.UpdateFichaDao;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.restful.RestfulAPI;
import com.gymproject.app.services.FichaService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.SessionUtils;

import java.util.*;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FichasSync extends AbsSync {

    Context context;

    FichasSync(@NonNull Context context) {
        super(context);
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.FICHAS;
    }

    @Override
    protected void post() {

        SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS);

        Log.i("FichasSync", "execute post method");

        List<UpdateFicha> updates = UpdateFichaDao.getAll(Realm.getDefaultInstance());
        if(updates.size()>0){
            System.out.println(updates.get(0).getMid());
        }

        FichaService apiService =
                RestfulAPI.getClient().create(FichaService.class);

        Call<Status> call = apiService.updateFicha(updates);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status>call, Response<Status> response) {
                Status status = response.body();
                if(status != null) {
                    Log.e("updateFicha", status.getMensagem());
                } else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                UpdateFichaDao.removeAll();
                get();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.e("updateFichas", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });

    }

    @Override
    protected void get() {
        Log.i("FichasSync", "execute get method");

        FichaService apiService =
                RestfulAPI.getClient().create(FichaService.class);

        Call<Status<List<Ficha>>> call = apiService.listarFichas(SessionUtils.getInstance(context).getIdUsuario());
        call.enqueue(new Callback<Status<List<Ficha>>>() {
            @Override
            public void onResponse(Call<Status<List<Ficha>>>call, Response<Status<List<Ficha>>> response) {
                Status status = response.body();
                if(status != null) {
                    if(status.getCodigo() == 1) {
                        List<Ficha> fichas = (List<Ficha>) status.getDados();
                        FichaDao.removeAll();
                        FichaDao.save(fichas);
                    }
                }else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }

            @Override
            public void onFailure(Call<Status<List<Ficha>>> call, Throwable t) {
                Log.e("listarFichas", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });
    }

}
