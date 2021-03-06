package com.gymproject.services;

import java.util.List;

import com.gymproject.model.Usuario;

public interface AcessoServices {
	public List<Usuario> autenticarLogin(String email, String senha) throws Exception;
	public boolean emailDisponivel(String email) throws Exception;
	public Usuario inserirUsuario(String nome, String email, String senha) throws Exception;
}
