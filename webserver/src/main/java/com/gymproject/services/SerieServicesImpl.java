package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.SerieDao;
import com.gymproject.model.Serie;

public class SerieServicesImpl implements SerieServices {

	@Autowired
	SerieDao serieDao;
	
	@Override
	public List<Serie> listar(String id) throws Exception {
		return serieDao.listar(id);
	}

	@Override
	public boolean save(Serie dados) throws Exception {
		return serieDao.save(dados);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return serieDao.delete(id);
	}

}
