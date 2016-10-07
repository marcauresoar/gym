package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.UpdateTreino;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpdateTreinoDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UpdateTreino> results = realm.where(UpdateTreino.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final UpdateTreino data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @NonNull
    public static List<UpdateTreino> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<UpdateTreino> results = realm.where(UpdateTreino.class).findAll();
        realm.commitTransaction();
        List<UpdateTreino> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static UpdateTreino getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        UpdateTreino data = realm.where(UpdateTreino.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }
}
