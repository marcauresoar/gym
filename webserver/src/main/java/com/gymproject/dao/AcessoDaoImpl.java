package com.gymproject.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.model.Usuario;

public class AcessoDaoImpl implements AcessoDao {

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> autenticarLogin(String email, String senha) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		List<Usuario> employeeList = session.createCriteria(Usuario.class).add(Restrictions.eq("email", email))
				.add(Restrictions.eq("senha", md5(senha))).list();
		tx.commit();
		session.close();
		return employeeList;
	}
	
	
	
	
	/**
	 * Converter uma string para md5
	 * @param str
	 * @return
	 */
	public String md5(String str){
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(str.getBytes(), 0, str.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
