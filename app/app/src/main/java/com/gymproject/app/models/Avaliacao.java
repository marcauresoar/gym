package com.gymproject.app.models;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Avaliacao extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;

    private String data;
    private String altura;
    private String peso;

    private String peitoral;
    private String biceps;
    private String triceps;
    private String subescapular;
    private String supra_iliaca;
    private String axiliar_media;
    private String abdominal;
    private String coxa;
    private String panturrilha_media;

    private String torax;
    private String abdomen;
    private String cintura;
    private String quadril;
    private String braco_direito;
    private String braco_esquerdo;
    private String antebraco_direito;
    private String antebraco_esquerdo;
    private String coxa_direita;
    private String coxa_esquerda;
    private String perna_direita;
    private String perna_esquerda;
    private String ombro;
    private String pescoco;
    private String punho;
    private String joelho;
    private String tornozelo;

    private Usuario usuario;

    public Avaliacao() {
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

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getPeitoral() {
        return peitoral;
    }

    public void setPeitoral(String peitoral) {
        this.peitoral = peitoral;
    }

    public String getBiceps() {
        return biceps;
    }

    public void setBiceps(String biceps) {
        this.biceps = biceps;
    }

    public String getTriceps() {
        return triceps;
    }

    public void setTriceps(String triceps) {
        this.triceps = triceps;
    }

    public String getSubescapular() {
        return subescapular;
    }

    public void setSubescapular(String subescapular) {
        this.subescapular = subescapular;
    }

    public String getSupra_iliaca() {
        return supra_iliaca;
    }

    public void setSupra_iliaca(String supra_iliaca) {
        this.supra_iliaca = supra_iliaca;
    }

    public String getAxiliar_media() {
        return axiliar_media;
    }

    public void setAxiliar_media(String axiliar_media) {
        this.axiliar_media = axiliar_media;
    }

    public String getAbdominal() {
        return abdominal;
    }

    public void setAbdominal(String abdominal) {
        this.abdominal = abdominal;
    }

    public String getCoxa() {
        return coxa;
    }

    public void setCoxa(String coxa) {
        this.coxa = coxa;
    }

    public String getPanturrilha_media() {
        return panturrilha_media;
    }

    public void setPanturrilha_media(String panturrilha_media) {
        this.panturrilha_media = panturrilha_media;
    }

    public String getTorax() {
        return torax;
    }

    public void setTorax(String torax) {
        this.torax = torax;
    }

    public String getAbdomen() {
        return abdomen;
    }

    public void setAbdomen(String abdomen) {
        this.abdomen = abdomen;
    }

    public String getCintura() {
        return cintura;
    }

    public void setCintura(String cintura) {
        this.cintura = cintura;
    }

    public String getQuadril() {
        return quadril;
    }

    public void setQuadril(String quadril) {
        this.quadril = quadril;
    }

    public String getBraco_direito() {
        return braco_direito;
    }

    public void setBraco_direito(String braco_direito) {
        this.braco_direito = braco_direito;
    }

    public String getBraco_esquerdo() {
        return braco_esquerdo;
    }

    public void setBraco_esquerdo(String braco_esquerdo) {
        this.braco_esquerdo = braco_esquerdo;
    }

    public String getAntebraco_direito() {
        return antebraco_direito;
    }

    public void setAntebraco_direito(String antebraco_direito) {
        this.antebraco_direito = antebraco_direito;
    }

    public String getAntebraco_esquerdo() {
        return antebraco_esquerdo;
    }

    public void setAntebraco_esquerdo(String antebraco_esquerdo) {
        this.antebraco_esquerdo = antebraco_esquerdo;
    }

    public String getCoxa_direita() {
        return coxa_direita;
    }

    public void setCoxa_direita(String coxa_direita) {
        this.coxa_direita = coxa_direita;
    }

    public String getCoxa_esquerda() {
        return coxa_esquerda;
    }

    public void setCoxa_esquerda(String coxa_esquerda) {
        this.coxa_esquerda = coxa_esquerda;
    }

    public String getPerna_direita() {
        return perna_direita;
    }

    public void setPerna_direita(String perna_direita) {
        this.perna_direita = perna_direita;
    }

    public String getPerna_esquerda() {
        return perna_esquerda;
    }

    public void setPerna_esquerda(String perna_esquerda) {
        this.perna_esquerda = perna_esquerda;
    }

    public String getOmbro() {
        return ombro;
    }

    public void setOmbro(String ombro) {
        this.ombro = ombro;
    }

    public String getPescoco() {
        return pescoco;
    }

    public void setPescoco(String pescoco) {
        this.pescoco = pescoco;
    }

    public String getPunho() {
        return punho;
    }

    public void setPunho(String punho) {
        this.punho = punho;
    }

    public String getJoelho() {
        return joelho;
    }

    public void setJoelho(String joelho) {
        this.joelho = joelho;
    }

    public String getTornozelo() {
        return tornozelo;
    }

    public void setTornozelo(String tornozelo) {
        this.tornozelo = tornozelo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
