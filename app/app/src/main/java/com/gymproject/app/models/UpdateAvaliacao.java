package com.gymproject.app.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UpdateAvaliacao extends RealmObject {
    @PrimaryKey
    private String id;
    private String acao;
    private String mid;
    private Avaliacao avaliacao;

    public UpdateAvaliacao(){};

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

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
