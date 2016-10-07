package com.gymproject.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.model.Treino;

public class TreinoDaoImpl implements TreinoDao {
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Treino> listar(String id) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		List<Treino> dados = session.createCriteria(Treino.class)
				.add(Restrictions.eq("usuario.id", Long.valueOf(id)))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		tx.commit();
		session.close();
		return dados;
	}
	
	@Override
	public boolean save(Treino dados) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.saveOrUpdate(dados);
		tx.commit();
		session.close();
		return false;
	}
	
	@Override
	public boolean delete(String id)
			throws Exception {
		session = sessionFactory.openSession();
		Object o = session.load(Treino.class, Long.valueOf(id));
		tx = session.getTransaction();
		session.beginTransaction();
		session.delete(o);
		tx.commit();
		return false;
	}
}
