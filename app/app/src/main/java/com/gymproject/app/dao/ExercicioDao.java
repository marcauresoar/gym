package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.UpdateExercicio;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ExercicioDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Exercicio> results = realm.where(Exercicio.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final Exercicio data, final String acao) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);

                UpdateExercicio update = new UpdateExercicio();
                update.setId(HashUtils.generateId());
                update.setAcao(acao);
                update.setExercicio(data);

                realm.copyToRealmOrUpdate(update);
            }
        });
    }

    public static void save(@NonNull final List<Exercicio> dataList) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dataList);
            }
        });
    }

    @NonNull
    public static List<Exercicio> getAll(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        RealmResults<Exercicio> results = realm.where(Exercicio.class).equalTo("ficha.id", id).findAll();
        realm.commitTransaction();
        List<Exercicio> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static Exercicio getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        Exercicio data = realm.where(Exercicio.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }

    public static void delete(@NonNull final Exercicio data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String id = data.getId();

                RealmResults<UpdateExercicio> results = realm.where(UpdateExercicio.class)
                        .beginGroup()
                            .equalTo("acao", "insert")
                            .or()
                            .equalTo("acao", "update")
                        .endGroup()
                        .findAll();
                if (results.size() > 0) {
                    for(UpdateExercicio update : results){
                        if(update.getExercicio().getId().equals(data.getId())){
                            update.deleteFromRealm();
                        }
                    }
                } else {
                    UpdateExercicio update = new UpdateExercicio();
                    update.setId(HashUtils.generateId());
                    update.setAcao("delete");
                    update.setMid(id);
                    realm.copyToRealm(update);
                }

                data.deleteFromRealm();
            }
        });
    }

}
