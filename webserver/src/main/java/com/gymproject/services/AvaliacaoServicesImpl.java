package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.AvaliacaoDao;
import com.gymproject.model.Avaliacao;

public class AvaliacaoServicesImpl implements AvaliacaoServices {

	@Autowired
	AvaliacaoDao avaliacaoDao;
	
	@Override
	public List<Avaliacao> listar(String id) throws Exception {
		return avaliacaoDao.listar(id);
	}

	@Override
	public boolean save(Avaliacao dados) throws Exception {
		return avaliacaoDao.save(dados);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return avaliacaoDao.delete(id);
	}

}
