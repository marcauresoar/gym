package com.gymproject.app.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UpdateExercicio extends RealmObject {
    @PrimaryKey
    private String id;
    private String acao;
    private String mid;
    private Exercicio exercicio;

    public UpdateExercicio(){};

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

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
