package com.gymproject.app.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Another;
import com.gymproject.app.models.Test;
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
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FichasSync extends AbsSync {

    Context context;
    Realm realm;

    FichasSync(@NonNull Context context) {
        super(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.FICHAS;
    }

    @Override
    protected void post() {

        SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS);

        Log.i("FichasSync", "execute post method");





        /*UpdateFicha updateFicha = new UpdateFicha();
        List<Change> changes = ChangeDao.getAll(Realm.getDefaultInstance(), "ficha");
        Log.i("qtd", String.valueOf(changes.size()));
        updateFicha.setChanges(changes);
        if(changes.size() > 0){
            List<Ficha> objetos = new ArrayList<>();
            for(Change change : changes){
                System.out.println("HERE");
                objetos.add(FichaDao.getById(Realm.getDefaultInstance(), change.getId()));
            }
            updateFicha.setObjetos(objetos);
        }*/

        List<UpdateFicha> lista = UpdateFichaDao.getAll(Realm.getDefaultInstance());
        List<UpdateFicha> novaLista = new ArrayList<>();
        if(lista.size() > 0){
            System.out.println("nada a ve");
            /*for(UpdateFicha updateFicha : lista){
                novaLista.add(updateFicha.createCopy());
            }*/
        }

        Another another = new Another();
        another.setId("2");
        another.setOutro("blablabla");

        Test test = new Test();
        test.setId("1");
        test.setNome("marcoooooo");
        test.setAnother(another);

        FichaService apiService =
                RestfulAPI.getClient().create(FichaService.class);

        Call<String> call = apiService.test(test);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String>call, Response<String> response) {
                String status = response.body();
                if(status != null) {
                    Log.e("updateFicha", status);
                } else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("updateFichas", t.toString());
            }
        });
        /*UpdateFicha updateFicha = new UpdateFicha();
        updateFicha.setAcao("insert");
        updateFicha.setId("1");
        updateFicha.setFicha(new Ficha("1", "minha ficha", "Segunda", new Usuario("1", "masdas", "sadasd@dads.com", "asdasdsa")));
        novaLista.add(updateFicha);*/

        /*FichaService apiService =
                RestfulAPI.getClient().create(FichaService.class);

        Call<Status> call = apiService.updateFicha(lista);
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
            }
        });*/
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
