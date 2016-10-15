package com.gymproject.app.models;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SerieTreino extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;

    private String tipo;
    private int tempo;
    private int repeticoes;
    private int peso;
    private boolean feito;
    private ExercicioTreino exercicio_treino;

    public SerieTreino() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }

    public ExercicioTreino getExercicio_treino() {
        return exercicio_treino;
    }

    public void setExercicio_treino(ExercicioTreino exercicio_treino) {
        this.exercicio_treino = exercicio_treino;
    }
}
