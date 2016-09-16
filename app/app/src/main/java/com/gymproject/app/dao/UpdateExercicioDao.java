package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.UpdateExercicio;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpdateExercicioDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UpdateExercicio> results = realm.where(UpdateExercicio.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final UpdateExercicio data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @NonNull
    public static List<UpdateExercicio> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<UpdateExercicio> results = realm.where(UpdateExercicio.class).findAll();
        realm.commitTransaction();
        List<UpdateExercicio> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static UpdateExercicio getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        UpdateExercicio data = realm.where(UpdateExercicio.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }
}
