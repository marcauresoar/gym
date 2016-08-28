package com.gymproject.app.models;

public class Ficha {

    private Integer id;
    private String nome;
    private String dias_semana;
    private Usuario usuario;

    public Ficha() {
    }

    public Ficha(Integer id, String nome, String dias_semana, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.dias_semana = dias_semana;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
