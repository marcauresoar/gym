package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.Serie;
import com.gymproject.app.models.UpdateSerie;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SerieDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Serie> results = realm.where(Serie.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final Serie data, final String acao) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);

                UpdateSerie update = new UpdateSerie();
                update.setId(HashUtils.generateId());
                update.setAcao(acao);
                update.setSerie(data);

                realm.copyToRealmOrUpdate(update);
            }
        });
    }

    public static void save(@NonNull final List<Serie> dataList) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dataList);
            }
        });
    }

    @NonNull
    public static List<Serie> getAll(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        RealmResults<Serie> results = realm.where(Serie.class).equalTo("exercicio.id", id).findAll();
        realm.commitTransaction();
        List<Serie> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static Serie getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        Serie data = realm.where(Serie.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }

    public static void delete(@NonNull final Serie data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String id = data.getId();

                RealmResults<UpdateSerie> results = realm.where(UpdateSerie.class)
                        .beginGroup()
                            .equalTo("acao", "insert")
                            .or()
                            .equalTo("acao", "update")
                        .endGroup()
                        .findAll();
                if (results.size() > 0) {
                    for(UpdateSerie update : results){
                        if(update.getSerie().getId().equals(data.getId())){
                            update.deleteFromRealm();
                        }
                    }
                } else {
                    UpdateSerie update = new UpdateSerie();
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
