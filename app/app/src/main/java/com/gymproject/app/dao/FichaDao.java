package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.Ficha;
import com.gymproject.app.models.UpdateFicha;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class FichaDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Ficha> results = realm.where(Ficha.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final Ficha data, final String acao) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);

                UpdateFicha update = new UpdateFicha();
                update.setId(HashUtils.generateId());
                update.setAcao(acao);
                update.setFicha(data);

                realm.copyToRealmOrUpdate(update);
            }
        });
    }

    public static void save(@NonNull final List<Ficha> dataList) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dataList);
            }
        });
    }

    @NonNull
    public static List<Ficha> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<Ficha> results = realm.where(Ficha.class).findAll();
        realm.commitTransaction();
        List<Ficha> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static Ficha getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        Ficha data = realm.where(Ficha.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }

    public static void delete(@NonNull final Ficha data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String id = data.getId();

                RealmResults<UpdateFicha> results = realm.where(UpdateFicha.class)
                        .beginGroup()
                            .equalTo("acao", "insert")
                            .or()
                            .equalTo("acao", "update")
                        .endGroup()
                        .findAll();
                if (results.size() > 0) {
                    for(UpdateFicha update : results){
                        if(update.getFicha().getId().equals(data.getId())){
                            update.deleteFromRealm();
                        }
                    }
                } else {
                    UpdateFicha update = new UpdateFicha();
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
