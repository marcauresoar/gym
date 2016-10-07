package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.SerieTreino;
import com.gymproject.app.models.UpdateSerieTreino;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SerieTreinoDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SerieTreino> results = realm.where(SerieTreino.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final SerieTreino data, final String acao) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);

                UpdateSerieTreino update = new UpdateSerieTreino();
                update.setId(HashUtils.generateId());
                update.setAcao(acao);
                update.setSerie_treino(data);

                realm.copyToRealmOrUpdate(update);
            }
        });
    }

    public static void save(@NonNull final List<SerieTreino> dataList) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dataList);
            }
        });
    }

    @NonNull
    public static List<SerieTreino> getAll(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        RealmResults<SerieTreino> results = realm.where(SerieTreino.class).equalTo("exercicio.id", id).findAll();
        realm.commitTransaction();
        List<SerieTreino> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static SerieTreino getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        SerieTreino data = realm.where(SerieTreino.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }

    public static void delete(@NonNull final SerieTreino data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String id = data.getId();

                RealmResults<UpdateSerieTreino> results = realm.where(UpdateSerieTreino.class)
                        .beginGroup()
                            .equalTo("acao", "insert")
                            .or()
                            .equalTo("acao", "update")
                        .endGroup()
                        .findAll();
                if (results.size() > 0) {
                    for(UpdateSerieTreino update : results){
                        if(update.getSerie_treino().getId().equals(data.getId())){
                            update.deleteFromRealm();
                        }
                    }
                } else {
                    UpdateSerieTreino update = new UpdateSerieTreino();
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
