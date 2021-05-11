package fr.eni.projetenchere.bll.article;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.ConnectionProvider;
import fr.eni.projetenchere.dal.article.*;
import fr.eni.projetenchere.dal.retrait.RetraitDAO;
import fr.eni.projetenchere.dal.retrait.RetraitDAOFactory;
import fr.eni.projetenchere.dal.utilisateur.*;

public class ArticleManagerImpl implements ArticleManager {
	
	private ArticleDAO articleDAO = ArticleDAOFactory.getArticleDAO();
	private RetraitDAO retraitDAO = RetraitDAOFactory.getRetraitDAO();

	private UtilisateurDAO utilisateurDAO = UtilisateurDAOFactory.getUtilisateurDAO(); 
	
	@Override
	public List<Article> getAll() throws Exception {
		return articleDAO.getAll();
	}
	
	@Override
	public Article getById(int id) throws Exception {
		return articleDAO.getArticleById(id); 
	}
	
	@Override
	public Article insertArticle(Article a, String rue, String cp, String ville) throws Exception {
		Connection cnx = ConnectionProvider.getConnection();
		cnx.setAutoCommit(false);
		try {
			//Validation des saisies utilisateur
			datesValides(a.getDate_debut_encheres(), a.getDate_fin_encheres());
			stringValide(a.getDescription(), "Description");
			prixInitialValide(a.getPrix_initial());
			
			a = articleDAO.insertArticle(cnx, a);
			
			Retrait r = new Retrait(a.getNo_article(), rue, cp, ville);
			r = retraitDAO.insertRetrait(cnx, r);	
			
			cnx.commit();
		} catch (Exception e) {
			cnx.rollback();
			throw new Exception(e);
		}
		cnx.close();
		return a;
	}
	
	public List<Article> getAllWithFilter(String filtres, int categorie, String type, boolean param1, boolean param2, boolean param3, Utilisateur u) throws Exception{
		return articleDAO.getAllWithFilter(filtres, categorie, type, param1, param2, param3, u); 
	}
	
	@Override
	public Article updateArticle(String pseudoVendeur, String nomArticle, String description, Date dateDebutEnchere,
			Date dateFinEnchere, int prixInitial, Categorie categorie) throws Exception {
		
		//Validation des saisies utilisateur
		datesValides(dateDebutEnchere, dateFinEnchere);
		stringValide(description, "Description"); 
		
		//on récupère l'id du vendeur de l'article
		Utilisateur vendeur = utilisateurDAO.getUtilisateurByPseudo(pseudoVendeur);
		
		//création de l'article à insérer
		Article articleAUpdate = new Article(nomArticle, description, dateDebutEnchere, dateFinEnchere, prixInitial, prixInitial, vendeur, categorie);
		
		return articleDAO.updateArticle(articleAUpdate);
	}
	
	@Override
	public int getNbArticlesByUtilisateur(Connection cnx, Utilisateur u) throws Exception {
		return articleDAO.getNbArticlesByUtilisateur(cnx, u);
	}	
	
	@Override
	public void removeArticle(Article article) throws Exception {
		articleDAO.removeArticle(article);
	}
	
	private void datesValides(Date dateDebut, Date dateFin) throws Exception {
		if(dateDebut.after(dateFin)) {
			throw new Exception("La date de début ne peut pas être supérieur à la date de fin.");
		}
		
	}
	
	private void stringValide(String str, String type) throws Exception {
        if(str.contains("<")) {
        	throw new Exception(type + " incorrect"); 
		}
	}
	
	private void prixInitialValide(int prix) throws Exception {
		if(prix <= 0) {
			throw new Exception("Le prix saisi est incorrect");
		}
	}

	@Override
	public Object[] updateArticleAndRetrait(Article a, Retrait r) throws Exception {
		Connection cnx = ConnectionProvider.getConnection();
		cnx.setAutoCommit(false);
		Object [] result = {null, null};
		try {
			//Validation des saisies utilisateur
			datesValides(a.getDate_debut_encheres(), a.getDate_fin_encheres());
			stringValide(a.getDescription(), "Description");
			prixInitialValide(a.getPrix_initial());
			
			a = articleDAO.updateArticle(cnx, a);
			
			r = retraitDAO.updateRetrait(cnx, r);
			
			result[0] = a;
			result[1] = r;
			
			cnx.commit();
		} catch (Exception e) {
			cnx.rollback();
			e.printStackTrace();
			throw new Exception(e);
		}
		cnx.close();
		return result;
	}


}
