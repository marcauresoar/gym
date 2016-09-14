package com.gymproject.dao;

import java.util.List;

import com.gymproject.model.Serie;

public interface SerieDao {
	public List<Serie> listar(String id) throws Exception;
	boolean save(Serie dados) throws Exception;
	boolean delete(String id) throws Exception;
}
