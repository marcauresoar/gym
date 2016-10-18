package com.gymproject.app.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gymproject.app.classes.Status;
import com.gymproject.app.dao.AvaliacaoDao;
import com.gymproject.app.dao.UpdateAvaliacaoDao;
import com.gymproject.app.models.Avaliacao;
import com.gymproject.app.models.UpdateAvaliacao;
import com.gymproject.app.restful.RestfulAPI;
import com.gymproject.app.services.AvaliacaoService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.SessionUtils;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AvaliacoesSync extends AbsSync {

    Context context;

    AvaliacoesSync(@NonNull Context context) {
        super(context);
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.AVALIACOES;
    }

    @Override
    protected void post() {

        SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS);

        Log.i("AvaliacaoSync", "execute post method");

        List<UpdateAvaliacao> updates = UpdateAvaliacaoDao.getAll(Realm.getDefaultInstance());

        AvaliacaoService apiService =
                RestfulAPI.getClient().create(AvaliacaoService.class);

        Call<Status> call = apiService.update(updates);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status>call, Response<Status> response) {
                Status status = response.body();
                if(status != null) {
                    Log.e("updateAvaliacao", status.getMensagem());
                } else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                UpdateAvaliacaoDao.removeAll();
                get();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.e("updateAvaliacao", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });

    }

    @Override
    protected void get() {
        Log.i("AvaliacaoSync", "execute get method");

        AvaliacaoService apiService =
                RestfulAPI.getClient().create(AvaliacaoService.class);

        Call<Status<List<Avaliacao>>> call = apiService.listar(SessionUtils.getInstance(context).getIdUsuario());
        call.enqueue(new Callback<Status<List<Avaliacao>>>() {
            @Override
            public void onResponse(Call<Status<List<Avaliacao>>>call, Response<Status<List<Avaliacao>>> response) {
                Status status = response.body();
                if(status != null) {
                    if(status.getCodigo() == 1) {
                        List<Avaliacao> treinos = (List<Avaliacao>) status.getDados();
                        AvaliacaoDao.removeAll();
                        AvaliacaoDao.save(treinos);
                    }
                }else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }

            @Override
            public void onFailure(Call<Status<List<Avaliacao>>> call, Throwable t) {
                Log.e("listarAvaliacao", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });
    }

}
