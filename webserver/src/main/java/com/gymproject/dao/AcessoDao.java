package com.gymproject.dao;

import java.util.List;

import com.gymproject.model.Usuario;

public interface AcessoDao {
	public List<Usuario> autenticarLogin(String email, String senha) throws Exception;
}
