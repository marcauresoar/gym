package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.UpdateExercicioTreino;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpdateExercicioTreinoDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UpdateExercicioTreino> results = realm.where(UpdateExercicioTreino.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final UpdateExercicioTreino data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @NonNull
    public static List<UpdateExercicioTreino> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<UpdateExercicioTreino> results = realm.where(UpdateExercicioTreino.class).findAll();
        realm.commitTransaction();
        List<UpdateExercicioTreino> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static UpdateExercicioTreino getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        UpdateExercicioTreino data = realm.where(UpdateExercicioTreino.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }
}
