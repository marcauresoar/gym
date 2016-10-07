package com.gymproject.app.models;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Treino extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;

    private String data;
    private String hora_inicio;
    private String hora_fim;

    private Usuario usuario;

    public Treino() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fim() {
        return hora_fim;
    }

    public void setHora_fim(String hora_fim) {
        this.hora_fim = hora_fim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
