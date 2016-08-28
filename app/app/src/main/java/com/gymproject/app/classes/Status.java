package com.gymproject.app.classes;


import com.google.gson.annotations.SerializedName;

public class Status<T> {

    @SerializedName("codigo")
    private int codigo;

    @SerializedName("mensagem")
    private String mensagem;

    @SerializedName("dados")
    private T dados;

    public Status() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }

    public Status(int codigo, String mensagem, T dados) {
        super();
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.dados = dados;
    }

}
