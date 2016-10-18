package com.gymproject.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.model.Avaliacao;

public class AvaliacaoDaoImpl implements AvaliacaoDao {
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Avaliacao> listar(String id) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		List<Avaliacao> fichas = session.createCriteria(Avaliacao.class)
				.add(Restrictions.eq("usuario.id", Long.valueOf(id)))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		System.out.println(fichas.size());
		tx.commit();
		session.close();
		return fichas;
	}
	
	
	@Override
	public boolean save(Avaliacao dados) throws Exception {
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
		Object o = session.load(Avaliacao.class, Long.valueOf(id));
		tx = session.getTransaction();
		session.beginTransaction();
		session.delete(o);
		tx.commit();
		return false;
	}
}
