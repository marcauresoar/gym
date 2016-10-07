package com.gymproject.app.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UpdateExercicioTreino extends RealmObject {
    @PrimaryKey
    private String id;
    private String acao;
    private String mid;
    private ExercicioTreino exercicio_treino;

    public UpdateExercicioTreino(){};

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

    public ExercicioTreino getExercicio_treino() {
        return exercicio_treino;
    }

    public void setExercicio_treino(ExercicioTreino exercicio_treino) {
        this.exercicio_treino = exercicio_treino;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
