package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.UpdateSerieTreino;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpdateSerieTreinoDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UpdateSerieTreino> results = realm.where(UpdateSerieTreino.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final UpdateSerieTreino data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @NonNull
    public static List<UpdateSerieTreino> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<UpdateSerieTreino> results = realm.where(UpdateSerieTreino.class).findAll();
        realm.commitTransaction();
        List<UpdateSerieTreino> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static UpdateSerieTreino getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        UpdateSerieTreino data = realm.where(UpdateSerieTreino.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }
}
