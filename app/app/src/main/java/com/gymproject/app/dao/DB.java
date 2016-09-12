package com.gymproject.app.dao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;

public class DB {
    public static void executeTransaction(@NonNull Realm.Transaction transaction) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(transaction);
        } catch (Throwable e) {
            Log.e("executeTransaction", e.toString());
        } finally {
            close(realm);
        }
    }

    public static void close(@Nullable Realm realm) {
        if (realm != null) {
            realm.close();
        }
    }
}
