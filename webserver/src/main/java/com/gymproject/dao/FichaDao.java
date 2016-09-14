package com.gymproject.dao;

import java.util.List;

import com.gymproject.model.Ficha;

public interface FichaDao {
	public List<Ficha> listar(String id) throws Exception;
	boolean save(Ficha dados) throws Exception;
	boolean delete(String id) throws Exception;
}
