package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.ExercicioTreino;
import com.gymproject.app.models.UpdateExercicioTreino;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ExercicioTreinoDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ExercicioTreino> results = realm.where(ExercicioTreino.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final ExercicioTreino data, final String acao) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);

                UpdateExercicioTreino update = new UpdateExercicioTreino();
                update.setId(HashUtils.generateId());
                update.setAcao(acao);
                update.setExercicio_treino(data);

                realm.copyToRealmOrUpdate(update);
            }
        });
    }

    public static void save(@NonNull final List<ExercicioTreino> dataList) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dataList);
            }
        });
    }

    @NonNull
    public static List<ExercicioTreino> getAll(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        RealmResults<ExercicioTreino> results = realm.where(ExercicioTreino.class).equalTo("ficha.id", id).findAll();
        realm.commitTransaction();
        List<ExercicioTreino> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static ExercicioTreino getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        ExercicioTreino data = realm.where(ExercicioTreino.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }

    public static void delete(@NonNull final ExercicioTreino data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String id = data.getId();

                RealmResults<UpdateExercicioTreino> results = realm.where(UpdateExercicioTreino.class)
                        .beginGroup()
                            .equalTo("acao", "insert")
                            .or()
                            .equalTo("acao", "update")
                        .endGroup()
                        .findAll();
                if (results.size() > 0) {
                    for(UpdateExercicioTreino update : results){
                        if(update.getExercicio_treino().getId().equals(data.getId())){
                            update.deleteFromRealm();
                        }
                    }
                } else {
                    UpdateExercicioTreino update = new UpdateExercicioTreino();
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
