package com.gymproject.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.model.Ficha;
import com.gymproject.model.Usuario;

public class FichaDaoImpl implements FichaDao {
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ficha> listarFichas(String id) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		List<Ficha> fichas = session.createCriteria(Ficha.class).add(Restrictions.eq("usuario.id", Long.valueOf(id)))
				.list();
		tx.commit();
		session.close();
		return fichas;
	}
}
