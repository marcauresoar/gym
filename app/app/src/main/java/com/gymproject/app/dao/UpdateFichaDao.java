package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.UpdateFicha;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpdateFichaDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UpdateFicha> results = realm.where(UpdateFicha.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final UpdateFicha data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }

    @NonNull
    public static List<UpdateFicha> getAll(@NonNull Realm realm) {
        return realm.where(UpdateFicha.class).findAll();
    }

    @NonNull
    public static UpdateFicha getById(@NonNull Realm realm, String id) {
        return realm.where(UpdateFicha.class).equalTo("id", id).findFirst();
    }
}
