package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.Treino;
import com.gymproject.app.models.UpdateTreino;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TreinoDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Treino> results = realm.where(Treino.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final Treino data, final String acao) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);

                UpdateTreino update = new UpdateTreino();
                update.setId(HashUtils.generateId());
                update.setAcao(acao);
                update.setTreino(data);

                realm.copyToRealmOrUpdate(update);
            }
        });
    }

    public static void save(@NonNull final List<Treino> dataList) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dataList);
            }
        });
    }

    @NonNull
    public static List<Treino> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<Treino> results = realm.where(Treino.class).findAll();
        realm.commitTransaction();
        List<Treino> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static Treino getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        Treino data = realm.where(Treino.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }

    public static void delete(@NonNull final Treino data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String id = data.getId();

                RealmResults<UpdateTreino> results = realm.where(UpdateTreino.class)
                        .beginGroup()
                            .equalTo("acao", "insert")
                            .or()
                            .equalTo("acao", "update")
                        .endGroup()
                        .findAll();
                if (results.size() > 0) {
                    for(UpdateTreino update : results){
                        if(update.getTreino().getId().equals(data.getId())){
                            update.deleteFromRealm();
                        }
                    }
                } else {
                    UpdateTreino update = new UpdateTreino();
                    update.setId(HashUtils.generateId());
                    update.setAcao("delete");
                    update.setMid(id);
                    realm.copyToRealm(update);
                }

                data.deleteFromRealm();
            }
        });
    }

}
