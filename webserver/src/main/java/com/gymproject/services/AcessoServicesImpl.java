package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.AcessoDao;
import com.gymproject.model.Usuario;

public class AcessoServicesImpl implements AcessoServices {
	
	@Autowired
	AcessoDao acessoDao;
	
	@Override
	public List<Usuario> autenticarLogin(String email, String senha) throws Exception {
		return acessoDao.autenticarLogin(email, senha);
	}

	@Override
	public boolean emailDisponivel(String email) throws Exception {
		return acessoDao.emailDisponivel(email);
	}

	@Override
	public Usuario inserirUsuario(String nome, String email, String senha) throws Exception {
		return acessoDao.inserirUsuario(nome, email, senha);
	}
}
