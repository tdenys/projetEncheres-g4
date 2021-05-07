package fr.eni.projetenchere.bll.enchere;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Enchere;
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
		
		if(article.getPrix_vente() < montant_enchere && (credit_actuel_nouvel_utilisateur - montant_enchere) >= 0) {
			System.out.println("IF OK");
			try {
				// l'utilisateur qui avait encherie recupère ses credit
				Utilisateur ancien_utilisateur = enchereDAO.getUtilisateurLastEnchere(cnx, article.getNo_article());
				if(ancien_utilisateur != null) {
					System.out.println(ancien_utilisateur.toString());
					int credit_actuel_ancien_utilisateur = ancien_utilisateur.getCredit();
					ancien_utilisateur.setCredit(credit_actuel_ancien_utilisateur + article.getPrix_vente());
				}
				
				// l'utilisateur qui a encherie perd ses credit
				nouvel_utilisateur.setCredit(credit_actuel_nouvel_utilisateur - montant_enchere);
				
				// maj du nouvel utilisateur avec les credit en moins
				utilisateurDAO.updateUtilisateur(cnx, nouvel_utilisateur, nouvel_utilisateur.getPseudo());
				
				// maj de l'article dans la bdd
				article.setPrix_vente(montant_enchere);
				articleDAO.updateArticle(cnx, article);
				
				// insert de l'enchere
				int id_nouvel_utilisateur= utilisateurDAO.getUtilisateurByPseudo(nouvel_utilisateur.getPseudo()).getNo_utilisateurs();
				nouvel_utilisateur.setNo_utilisateurs(id_nouvel_utilisateur);
				en = new Enchere(new Date(), montant_enchere, article, nouvel_utilisateur);
				enchereDAO.insertEnchere(cnx, en);
				cnx.commit();
			} catch (Exception e) {
				cnx.rollback();
				throw new Exception(e);
			}
		} else {
			throw new Exception("Une erreur s'est produite");
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
