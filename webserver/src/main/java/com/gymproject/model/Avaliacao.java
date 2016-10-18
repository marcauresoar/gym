package com.gymproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "avaliacao")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Avaliacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "data", nullable = false)
	private String data;
	
	@Column(name = "altura", nullable = false)
	private Float altura;
	
	@Column(name = "peso", nullable = false)
	private Float peso;
	
	@Column(name = "peitoral", nullable = true)
	private Float peitoral;
	
	@Column(name = "biceps", nullable = true)
	private Float biceps;
	
	@Column(name = "triceps", nullable = true)
	private Float triceps;
	
	@Column(name = "subescapular", nullable = true)
	private Float subescapular;
	
	@Column(name = "supra_iliaca", nullable = true)
	private Float supra_iliaca;
	
	@Column(name = "axiliar_media", nullable = true)
	private Float axiliar_media;
	
	@Column(name = "abdominal", nullable = true)
	private Float abdominal;
	
	@Column(name = "coxa", nullable = true)
	private Float coxa;
	
	@Column(name = "panturrilha_media", nullable = true)
	private Float panturrilha_media;
	
	@Column(name = "torax", nullable = true)
	private Float torax;
	
	@Column(name = "abdomen", nullable = true)
	private Float abdomen;
	
	@Column(name = "cintura", nullable = true)
	private Float cintura;
	
	@Column(name = "quadril", nullable = true)
	private Float quadril;
	
	@Column(name = "braco_direito", nullable = true)
	private Float braco_direito;
	
	@Column(name = "braco_esquerdo", nullable = true)
	private Float braco_esquerdo;
	
	@Column(name = "antebraco_direito", nullable = true)
	private Float antebraco_direito;
	
	@Column(name = "antebraco_esquerdo", nullable = true)
	private Float antebraco_esquerdo;
	
	@Column(name = "coxa_direita", nullable = true)
	private Float coxa_direita;
	
	@Column(name = "coxa_esquerda", nullable = true)
	private Float coxa_esquerda;
	
	@Column(name = "perna_direita", nullable = true)
	private Float perna_direita;
	
	@Column(name = "perna_esquerda", nullable = true)
	private Float perna_esquerda;
	
	@Column(name = "ombro", nullable = true)
	private Float ombro;
	
	@Column(name = "pescoco", nullable = true)
	private Float pescoco;
	
	@Column(name = "punho", nullable = true)
	private Float punho;
	
	@Column(name = "joelho", nullable = true)
	private Float joelho;
	
	@Column(name = "tornozelo", nullable = true)
	private Float tornozelo;	
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Float getAltura() {
		return altura;
	}

	public void setAltura(Float altura) {
		this.altura = altura;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public Float getPeitoral() {
		return peitoral;
	}

	public void setPeitoral(Float peitoral) {
		this.peitoral = peitoral;
	}

	public Float getBiceps() {
		return biceps;
	}

	public void setBiceps(Float biceps) {
		this.biceps = biceps;
	}

	public Float getTriceps() {
		return triceps;
	}

	public void setTriceps(Float triceps) {
		this.triceps = triceps;
	}

	public Float getSubescapular() {
		return subescapular;
	}

	public void setSubescapular(Float subescapular) {
		this.subescapular = subescapular;
	}

	public Float getSupra_iliaca() {
		return supra_iliaca;
	}

	public void setSupra_iliaca(Float supra_iliaca) {
		this.supra_iliaca = supra_iliaca;
	}

	public Float getAxiliar_media() {
		return axiliar_media;
	}

	public void setAxiliar_media(Float axiliar_media) {
		this.axiliar_media = axiliar_media;
	}

	public Float getAbdominal() {
		return abdominal;
	}

	public void setAbdominal(Float abdominal) {
		this.abdominal = abdominal;
	}

	public Float getCoxa() {
		return coxa;
	}

	public void setCoxa(Float coxa) {
		this.coxa = coxa;
	}

	public Float getPanturrilha_media() {
		return panturrilha_media;
	}

	public void setPanturrilha_media(Float panturrilha_media) {
		this.panturrilha_media = panturrilha_media;
	}

	public Float getTorax() {
		return torax;
	}

	public void setTorax(Float torax) {
		this.torax = torax;
	}

	public Float getAbdomen() {
		return abdomen;
	}

	public void setAbdomen(Float abdomen) {
		this.abdomen = abdomen;
	}

	public Float getCintura() {
		return cintura;
	}

	public void setCintura(Float cintura) {
		this.cintura = cintura;
	}

	public Float getQuadril() {
		return quadril;
	}

	public void setQuadril(Float quadril) {
		this.quadril = quadril;
	}

	public Float getBraco_direito() {
		return braco_direito;
	}

	public void setBraco_direito(Float braco_direito) {
		this.braco_direito = braco_direito;
	}

	public Float getBraco_esquerdo() {
		return braco_esquerdo;
	}

	public void setBraco_esquerdo(Float braco_esquerdo) {
		this.braco_esquerdo = braco_esquerdo;
	}

	public Float getAntebraco_direito() {
		return antebraco_direito;
	}

	public void setAntebraco_direito(Float antebraco_direito) {
		this.antebraco_direito = antebraco_direito;
	}

	public Float getAntebraco_esquerdo() {
		return antebraco_esquerdo;
	}

	public void setAntebraco_esquerdo(Float antebraco_esquerdo) {
		this.antebraco_esquerdo = antebraco_esquerdo;
	}

	public Float getCoxa_direita() {
		return coxa_direita;
	}

	public void setCoxa_direita(Float coxa_direita) {
		this.coxa_direita = coxa_direita;
	}

	public Float getCoxa_esquerda() {
		return coxa_esquerda;
	}

	public void setCoxa_esquerda(Float coxa_esquerda) {
		this.coxa_esquerda = coxa_esquerda;
	}

	public Float getPerna_direita() {
		return perna_direita;
	}

	public void setPerna_direita(Float perna_direita) {
		this.perna_direita = perna_direita;
	}

	public Float getPerna_esquerda() {
		return perna_esquerda;
	}

	public void setPerna_esquerda(Float perna_esquerda) {
		this.perna_esquerda = perna_esquerda;
	}

	public Float getOmbro() {
		return ombro;
	}

	public void setOmbro(Float ombro) {
		this.ombro = ombro;
	}

	public Float getPescoco() {
		return pescoco;
	}

	public void setPescoco(Float pescoco) {
		this.pescoco = pescoco;
	}

	public Float getPunho() {
		return punho;
	}

	public void setPunho(Float punho) {
		this.punho = punho;
	}

	public Float getJoelho() {
		return joelho;
	}

	public void setJoelho(Float joelho) {
		this.joelho = joelho;
	}

	public Float getTornozelo() {
		return tornozelo;
	}

	public void setTornozelo(Float tornozelo) {
		this.tornozelo = tornozelo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
