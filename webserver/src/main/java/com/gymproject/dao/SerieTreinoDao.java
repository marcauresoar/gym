package com.gymproject.dao;

import java.util.List;

import com.gymproject.model.SerieTreino;

public interface SerieTreinoDao {
	public List<SerieTreino> listar(String id) throws Exception;
	boolean save(SerieTreino dados) throws Exception;
	boolean delete(String id) throws Exception;
}
