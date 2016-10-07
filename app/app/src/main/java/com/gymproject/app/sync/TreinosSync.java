package com.gymproject.app.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gymproject.app.classes.Status;
import com.gymproject.app.dao.TreinoDao;
import com.gymproject.app.dao.UpdateTreinoDao;
import com.gymproject.app.models.Treino;
import com.gymproject.app.models.UpdateTreino;
import com.gymproject.app.restful.RestfulAPI;
import com.gymproject.app.services.TreinoService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.SessionUtils;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class TreinosSync extends AbsSync {

    Context context;

    TreinosSync(@NonNull Context context) {
        super(context);
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.TREINOS;
    }

    @Override
    protected void post() {

        SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS);

        Log.i("FichasSync", "execute post method");

        List<UpdateTreino> updates = UpdateTreinoDao.getAll(Realm.getDefaultInstance());

        TreinoService apiService =
                RestfulAPI.getClient().create(TreinoService.class);

        Call<Status> call = apiService.update(updates);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status>call, Response<Status> response) {
                Status status = response.body();
                if(status != null) {
                    Log.e("updateTreinos", status.getMensagem());
                } else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                UpdateTreinoDao.removeAll();
                get();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.e("updateTreinos", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });

    }

    @Override
    protected void get() {
        Log.i("FichasSync", "execute get method");

        TreinoService apiService =
                RestfulAPI.getClient().create(TreinoService.class);

        Call<Status<List<Treino>>> call = apiService.listar(SessionUtils.getInstance(context).getIdUsuario());
        call.enqueue(new Callback<Status<List<Treino>>>() {
            @Override
            public void onResponse(Call<Status<List<Treino>>>call, Response<Status<List<Treino>>> response) {
                Status status = response.body();
                if(status != null) {
                    if(status.getCodigo() == 1) {
                        List<Treino> treinos = (List<Treino>) status.getDados();
                        TreinoDao.removeAll();
                        TreinoDao.save(treinos);
                    }
                }else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }

            @Override
            public void onFailure(Call<Status<List<Treino>>> call, Throwable t) {
                Log.e("listarTreinos", t.toString());
                SyncEvent.send(getSyncType(), SyncStatus.COMPLETED);
            }
        });
    }

}
