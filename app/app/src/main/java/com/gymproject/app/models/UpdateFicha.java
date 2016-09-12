package com.gymproject.app.models;

import io.realm.RealmObject;
import io.realm.UpdateFichaRealmProxy;
import io.realm.annotations.PrimaryKey;

public class UpdateFicha extends RealmObject {
    @PrimaryKey
    private String id;
    private String acao;
    private String mid;
    private Ficha ficha;

    public UpdateFicha(){};

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
