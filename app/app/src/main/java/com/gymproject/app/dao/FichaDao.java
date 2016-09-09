package com.gymproject.app.dao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gymproject.app.models.Ficha;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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

    public static void save(@NonNull final Ficha data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
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
        return realm.where(Ficha.class).findAll();
    }

    @NonNull
    public static Ficha getById(@NonNull Realm realm, String id) {
        return realm.where(Ficha.class).equalTo("id", id).findFirst();
    }

}
