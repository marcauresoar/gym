package com.gymproject.app.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ficha extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;

    private String nome;
    private String dias_semana;
    private Usuario usuario;

    public Ficha() {
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

    public String getDias_semana() {
        return dias_semana;
    }

    public void setDias_semana(String dias_semana) {
        this.dias_semana = dias_semana;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
