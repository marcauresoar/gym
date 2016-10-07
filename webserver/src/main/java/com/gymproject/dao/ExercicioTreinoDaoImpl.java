package com.gymproject.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.model.ExercicioTreino;

public class ExercicioTreinoDaoImpl implements ExercicioTreinoDao {
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExercicioTreino> listar(String id) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		List<ExercicioTreino> dados;
		
		Criteria c = session.createCriteria(ExercicioTreino.class, "exercicio_treino");
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
	public boolean save(ExercicioTreino dados) throws Exception {
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
		Object o = session.load(ExercicioTreino.class, Long.valueOf(id));
		tx = session.getTransaction();
		session.beginTransaction();
		session.delete(o);
		tx.commit();
		return false;
	}
}
