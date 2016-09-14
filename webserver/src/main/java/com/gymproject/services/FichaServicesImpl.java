package com.gymproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.dao.FichaDao;
import com.gymproject.model.Ficha;

public class FichaServicesImpl implements FichaServices {

	@Autowired
	FichaDao fichaDao;
	
	@Override
	public List<Ficha> listar(String id) throws Exception {
		return fichaDao.listar(id);
	}

	@Override
	public boolean save(Ficha dados) throws Exception {
		return fichaDao.save(dados);
	}

	@Override
	public boolean delete(String id) throws Exception {
		return fichaDao.delete(id);
	}

}
