package fr.eni.projetenchere.bll.enchere;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.article.ArticleDAO;
import fr.eni.projetenchere.dal.article.ArticleDAOFactory;
import fr.eni.projetenchere.dal.ConnectionProvider;
import fr.eni.projetenchere.dal.enchere.EnchereDAO;
import fr.eni.projetenchere.dal.enchere.EnchereDAOFactory;
import fr.eni.projetenchere.dal.utilisateur.UtilisateurDAO;
import fr.eni.projetenchere.dal.utilisateur.UtilisateurDAOFactory;

public class EnchereManagerImpl implements EnchereManager {
	
	private EnchereDAO enchereDAO = EnchereDAOFactory.getEnchereDAO();
	private ArticleDAO articleDAO = ArticleDAOFactory.getArticleDAO();
	private UtilisateurDAO utilisateurDAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	@Override
	public Enchere insertEnchere(int montant_enchere, Article article, Utilisateur nouvel_utilisateur) throws Exception {
		Connection cnx = ConnectionProvider.getConnection();
		int credit_actuel_nouvel_utilisateur = nouvel_utilisateur.getCredit();
		Enchere en = null;
		
		if(article.getPrix_vente() > montant_enchere && (credit_actuel_nouvel_utilisateur - montant_enchere) >= 0) {
			try {
				// l'utilisateur qui avait encherie recupère ses credit
				Utilisateur ancien_utilisateur = enchereDAO.getUtilisateurLastEnchere(article.getNo_article());
				int credit_actuel_ancien_utilisateur = article.getUtilisateur().getCredit();
				article.getUtilisateur().setCredit(credit_actuel_ancien_utilisateur + article.getPrix_vente());
				
				// l'utilisateur qui a encherie perd ses credit
				nouvel_utilisateur.setCredit(credit_actuel_nouvel_utilisateur - montant_enchere);
				
				// la connexion ne doit pas commit auto
				cnx.setAutoCommit(false);
				
				// maj du nouvel utilisateur avec les credit en moins
				utilisateurDAO.updateUtilisateur(cnx, nouvel_utilisateur, nouvel_utilisateur.getNom());
				
				// maj de l'article dans la bdd
				article.setPrix_vente(montant_enchere);
				articleDAO.updateArticle(cnx, article);
				
				// insert de l'enchere
				en = new Enchere(new Date(), montant_enchere, article, nouvel_utilisateur);
				enchereDAO.insertEnchere(cnx, en);
				cnx.commit();
			} catch (Exception e) {
				cnx.rollback();
			}
		}
		cnx.close();
		return en;
	}
	
	/*@Override
	public void removeEnchere(Article enchere) throws Exception {
		articleDAO.removeArticle(article);
	}

	@Override
	public Enchere getEnchereByIdArticle(int numArticle) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEnchere(Enchere e) throws Exception {
		// TODO Auto-generated method stub
		
	}*/
	
}
