package com.gymproject.app.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.FichaRealmProxy;
import io.realm.RealmObject;
import io.realm.UpdateFichaRealmProxy;
import io.realm.annotations.PrimaryKey;

@Parcel(value = Parcel.Serialization.BEAN,
        analyze = { Ficha.class })
public class Ficha extends RealmObject {

    @PrimaryKey
    private String id;

    private String nome;
    private String dias_semana;
    private Usuario usuario;

    public Ficha() {
    }

    public Ficha(String id, String nome, String dias_semana, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.dias_semana = dias_semana;
        this.usuario = usuario;
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

    public Ficha createCopy(){
        Ficha ficha = new Ficha();
        ficha.setId(this.id);
        ficha.setNome(this.nome);
        ficha.setDias_semana(this.dias_semana);
        ficha.setUsuario(this.usuario.createCopy());
        return ficha;
    }
}
