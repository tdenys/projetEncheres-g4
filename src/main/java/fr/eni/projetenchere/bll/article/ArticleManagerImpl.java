package fr.eni.projetenchere.bll.article;

import java.util.Date;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class ArticleManagerImpl implements ArticleManager {
	
	//private ArticleDAO articleDAO = ArticleDAOFactory.getArticleDAO();

	private UtilisateurDAO utilisateurDAO = UtilisateurDAOFactory.getUtilisateurDAO(); 
	
	@Override
	public Article insertArticle(String pseudoVendeur, String nomArticle, String description, Date dateDebutEnchere,
			Date dateFinEnchere, int prixInitial, int numCategorie) throws Exception {
		
		//on récupère l'id du vendeur de l'article
		int numVendeur = utilisateurDAO.getUtilisateurByPseudo(pseudoVendeur).getNo_utilisateurs();
		
		//création de l'article à insérer
		Article articleAInserer = new Article(nomArticle, description, dateDebutEnchere, dateFinEnchere, prixInitial, prixInitial, numVendeur, numCategorie);
		
		
		return articleDAO.insertArticle(articleAInserer); 
	}
	
	
}
