package com.gymproject.services;

import java.util.List;

import com.gymproject.model.Usuario;

public interface AcessoServices {
	public List<Usuario> autenticarLogin(String email, String senha) throws Exception;
}
