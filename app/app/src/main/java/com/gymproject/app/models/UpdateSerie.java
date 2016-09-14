package com.gymproject.app.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UpdateSerie extends RealmObject {
    @PrimaryKey
    private String id;
    private String acao;
    private String mid;
    private Serie serie;

    public UpdateSerie(){};

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

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
