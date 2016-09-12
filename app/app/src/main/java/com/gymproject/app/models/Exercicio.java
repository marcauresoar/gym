package com.gymproject.app.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Exercicio extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;

    private String nome;
    private String grupos_musculares;
    private RealmList<Serie> series;

    public Exercicio() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGrupos_musculares() {
        return grupos_musculares;
    }

    public void setGrupos_musculares(String grupos_musculares) {
        this.grupos_musculares = grupos_musculares;
    }

    public RealmList<Serie> getSeries() {
        return series;
    }

    public void setSeries(RealmList<Serie> series) {
        this.series = series;
    }
}
