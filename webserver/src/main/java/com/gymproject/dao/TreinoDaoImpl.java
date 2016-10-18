package com.gymproject.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gymproject.model.Exercicio;
import com.gymproject.model.ExercicioTreino;
import com.gymproject.model.Serie;
import com.gymproject.model.SerieTreino;
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
	
	ExercicioTreinoDao exercicioTreinoDao;
	SerieTreinoDao serieTreinoDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean save(Treino dados, String acao) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		session.saveOrUpdate(dados);
		
		if(acao.equals("insert")){
			String ficha_id = dados.getFicha_id();
			
			List<Exercicio> exercicios;
			Criteria c = session.createCriteria(Exercicio.class, "exercicio");
			c.createAlias("exercicio.ficha", "ficha");
			c.add(Restrictions.eq("ficha.id", Long.valueOf(ficha_id)));
			c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			exercicios = c.list();
			
			for(Exercicio exerc : exercicios){
				ExercicioTreino newExerc = new ExercicioTreino();
				newExerc.setNome(exerc.getNome());
				newExerc.setGrupo_muscular(exerc.getGrupo_muscular());
				newExerc.setTreino(dados);
				
				session.saveOrUpdate(newExerc);
				
				List<Serie> series;
				Criteria c1 = session.createCriteria(Serie.class, "serie");
				c1.createAlias("serie.exercicio", "exercicio");
				c1.createAlias("exercicio.ficha", "ficha");
				c1.add(Restrictions.eq("exercicio.id", exerc.getId()));
				c1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				series = c1.list();
				
				for(Serie serie: series){
					SerieTreino newSerie = new SerieTreino();
					newSerie.setExercicio_treino(newExerc);
					newSerie.setFeito(false);
					newSerie.setPeso(serie.getPeso());
					newSerie.setRepeticoes(serie.getRepeticoes());
					newSerie.setTempo(serie.getTempo());
					newSerie.setTipo(serie.getTipo());
					
					session.saveOrUpdate(newSerie);
				}
			}	
		}
		
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
