package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.TreinoDao;
import com.gymproject.model.Treino;

public class TreinoServicesImpl implements TreinoServices {

	@Autowired
	TreinoDao treinoDao;
	
	@Override
	public List<Treino> listar(String id) throws Exception {
		return treinoDao.listar(id);
	}

	@Override
	public boolean save(Treino dados, String acao) throws Exception {
		return treinoDao.save(dados, acao);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return treinoDao.delete(id);
	}

}
