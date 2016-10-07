package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.SerieTreinoDao;
import com.gymproject.model.SerieTreino;

public class SerieTreinoServicesImpl implements SerieTreinoServices {

	@Autowired
	SerieTreinoDao serieDao;
	
	@Override
	public List<SerieTreino> listar(String id) throws Exception {
		return serieDao.listar(id);
	}

	@Override
	public boolean save(SerieTreino dados) throws Exception {
		return serieDao.save(dados);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return serieDao.delete(id);
	}

}
