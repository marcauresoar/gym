package com.gymproject.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.model.SerieTreino;

public class SerieTreinoDaoImpl implements SerieTreinoDao {
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SerieTreino> listar(String id) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		List<SerieTreino> dados;
		
		Criteria c = session.createCriteria(SerieTreino.class, "serie_treino");
		c.createAlias("serie_treino.exercicio_treino", "exercicio_treino");
		c.createAlias("exercicio_treino.treino", "treino");
		c.createAlias("treino.usuario", "usuario");
		c.add(Restrictions.eq("usuario.id", Long.valueOf(id)));
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		dados = c.list();
		
		tx.commit();
		session.close();
		return dados;
	}
	
	@Override
	public boolean save(SerieTreino dados) throws Exception {
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
		Object o = session.load(SerieTreino.class, Long.valueOf(id));
		tx = session.getTransaction();
		session.beginTransaction();
		session.delete(o);
		tx.commit();
		return false;
	}
}
