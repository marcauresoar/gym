package com.gymproject.classes;

public class Status {

	private int codigo;
	private String mensagem;
	private Object dados;

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

	public Object getDados() {
		return dados;
	}

	public void setDados(Object dados) {
		this.dados = dados;
	}

	public Status(int codigo, String mensagem, Object dados) {
		super();
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.dados = dados;
	}
	
}
