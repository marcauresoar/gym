package com.gymproject.app.dao;

import android.support.annotation.NonNull;

import com.gymproject.app.models.Avaliacao;
import com.gymproject.app.models.UpdateAvaliacao;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class AvaliacaoDao {
    public static void removeAll() {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Avaliacao> results = realm.where(Avaliacao.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void save(@NonNull final Avaliacao data, final String acao) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);

                UpdateAvaliacao update = new UpdateAvaliacao();
                update.setId(HashUtils.generateId());
                update.setAcao(acao);
                update.setAvaliacao(data);

                realm.copyToRealmOrUpdate(update);
            }
        });
    }

    public static void save(@NonNull final List<Avaliacao> dataList) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dataList);
            }
        });
    }

    @NonNull
    public static List<Avaliacao> getAll(@NonNull Realm realm) {
        realm.beginTransaction();
        RealmResults<Avaliacao> results = realm.where(Avaliacao.class).findAll();
        realm.commitTransaction();
        List<Avaliacao> lista = new ArrayList<>(results.subList(0, results.size()));
        return lista;
    }

    @NonNull
    public static Avaliacao getById(@NonNull Realm realm, String id) {
        realm.beginTransaction();
        Avaliacao data = realm.where(Avaliacao.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return data;
    }

    public static void delete(@NonNull final Avaliacao data) {
        DB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String id = data.getId();

                RealmResults<UpdateAvaliacao> results = realm.where(UpdateAvaliacao.class)
                        .beginGroup()
                            .equalTo("acao", "insert")
                            .or()
                            .equalTo("acao", "update")
                        .endGroup()
                        .findAll();
                if (results.size() > 0) {
                    for(UpdateAvaliacao update : results){
                        if(update.getAvaliacao().getId().equals(data.getId())){
                            update.deleteFromRealm();
                        }
                    }
                } else {
                    UpdateAvaliacao update = new UpdateAvaliacao();
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
