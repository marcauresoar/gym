package com.gymproject.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.UpdateFichaRealmProxy;
import io.realm.UsuarioRealmProxy;
import io.realm.annotations.PrimaryKey;

@Parcel(implementations = { UsuarioRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Usuario.class })
public class Usuario extends RealmObject {

    @PrimaryKey
    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(){}

    public Usuario(String id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public Usuario createCopy(){
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        return usuario;
    }

}
