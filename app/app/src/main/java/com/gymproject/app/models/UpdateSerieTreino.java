package com.gymproject.app.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UpdateSerieTreino extends RealmObject {
    @PrimaryKey
    private String id;
    private String acao;
    private String mid;
    private SerieTreino serie_treino;

    public UpdateSerieTreino(){};

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

    public SerieTreino getSerie_treino() {
        return serie_treino;
    }

    public void setSerie_treino(SerieTreino serie_treino) {
        this.serie_treino = serie_treino;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
