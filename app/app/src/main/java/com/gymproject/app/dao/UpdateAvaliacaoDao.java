package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.UpdateAvaliacao;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpdateAvaliacaoDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UpdateAvaliacao> results = realm.where(UpdateAvaliacao.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final UpdateAvaliacao data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @NonNull
    public static List<UpdateAvaliacao> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<UpdateAvaliacao> results = realm.where(UpdateAvaliacao.class).findAll();
        realm.commitTransaction();
        List<UpdateAvaliacao> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static UpdateAvaliacao getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        UpdateAvaliacao data = realm.where(UpdateAvaliacao.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }
}
