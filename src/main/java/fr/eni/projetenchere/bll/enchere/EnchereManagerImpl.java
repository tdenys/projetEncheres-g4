package fr.eni.projetenchere.bll.enchere;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.article.*;
import fr.eni.projetenchere.dal.enchere.*;
import fr.eni.projetenchere.dal.utilisateur.*;
import fr.eni.projetenchere.dal.ConnectionProvider;

public class EnchereManagerImpl implements EnchereManager {
	
	private EnchereDAO enchereDAO = EnchereDAOFactory.getEnchereDAO();
	private ArticleDAO articleDAO = ArticleDAOFactory.getArticleDAO();
	private UtilisateurDAO utilisateurDAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	@Override
	public Enchere insertEnchere(int montant_enchere, Article article, Utilisateur nouvel_utilisateur) throws Exception {
		Connection cnx = ConnectionProvider.getConnection();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		// la connexion ne doit pas commit auto
		cnx.setAutoCommit(false);
		
		int credit_actuel_nouvel_utilisateur = nouvel_utilisateur.getCredit();
		Enchere en = null;

		
		//Controle : l'encherisseur n'a pas un solde suffisant pour encherir
		if(credit_actuel_nouvel_utilisateur < montant_enchere) {
			throw new Exception("Votre solde est insuffisant.");
		//Controle : l'encherisseur n'a pas saisi un montant suffisant pour encherir
		} else if(article.getPrix_vente() > montant_enchere) {
			throw new Exception("Veuillez saisir un montant supérieur à la dernière enchère.");
		}
		
		if(article.getPrix_vente() < montant_enchere && (credit_actuel_nouvel_utilisateur - montant_enchere) >= 0) {
			try {
				// l'utilisateur qui avait encherie recupère ses credit
				Utilisateur ancien_utilisateur = enchereDAO.getUtilisateurLastEnchere(cnx, article.getNo_article());
				if(ancien_utilisateur != null) {
					int credit_actuel_ancien_utilisateur = ancien_utilisateur.getCredit();
					ancien_utilisateur.setCredit(credit_actuel_ancien_utilisateur + article.getPrix_vente());
				}
				
				// l'utilisateur qui a encherie perd ses credit
				nouvel_utilisateur.setCredit(credit_actuel_nouvel_utilisateur - montant_enchere);
				
				// maj du nouvel utilisateur avec les credit en moins
				utilisateurDAO.updateUtilisateurWithCredit(cnx, nouvel_utilisateur);
				
				// maj de l'article dans la bdd
				article.setPrix_vente(montant_enchere);
				articleDAO.updateArticle(cnx, article);
				
				// insert de l'enchere
				en = new Enchere(new Date(), montant_enchere, article, nouvel_utilisateur);
				enchereDAO.insertEnchere(cnx, en);
				cnx.commit();
			} catch (Exception e) {
				cnx.rollback();
				throw new Exception(e);
			} 
		}
		cnx.close();
		return en;
	}

	@Override
	public int getNbEncheresByUtilisateur(Connection cnx, Utilisateur u) throws Exception {
	
		return enchereDAO.getNbEncheresByUtilisateur(cnx, u);
	}

	@Override
	public Utilisateur getUtilisateurWhoWin(Article a) throws Exception {
		Utilisateur vendeur = a.getUtilisateur();
		
		if(!a.isVendu() && (new Date().after(a.getDate_fin_encheres())) && (a.getPrix_initial() != a.getPrix_vente())) {
			articleDAO.updateArticleVendu(a, true);
			utilisateurDAO.addCredit(vendeur, a.getPrix_vente());
		}
		
		return enchereDAO.getUtilisateurWhoWin(a); 
	}
	
	@Override
	public Utilisateur getDernierEnrechisseur(int no_article) throws Exception {		
		return enchereDAO.getDernierEncherisseur(no_article); 
	}
	
}
