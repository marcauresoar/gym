package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.Usuario;

import java.util.List;

import io.realm.Realm;

public class UsuarioDao {
    public static void save(@NonNull final Usuario data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }
    @NonNull
    public static Usuario getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        Usuario data = realm.where(Usuario.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }

    @NonNull
    public static List<Usuario> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        List<Usuario> lista = realm.where(Usuario.class).findAll();
        realm.commitTransaction();
        return lista;
    }
}
