package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.UpdateSerie;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpdateSerieDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UpdateSerie> results = realm.where(UpdateSerie.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final UpdateSerie data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @NonNull
    public static List<UpdateSerie> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<UpdateSerie> results = realm.where(UpdateSerie.class).findAll();
        realm.commitTransaction();
        List<UpdateSerie> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static UpdateSerie getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        UpdateSerie data = realm.where(UpdateSerie.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }
}
