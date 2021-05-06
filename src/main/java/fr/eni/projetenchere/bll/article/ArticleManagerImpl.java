package fr.eni.projetenchere.bll.article;

import java.util.Date;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class ArticleManagerImpl implements ArticleManager {
	
	//private ArticleDAO DAO = ArticleDAOFactory.getArticleDAO();

	@Override
	public Article insertArticle(String pseudoVendeur, String nomArticle, String description, Date dateDebutEnchere,
			Date dateFinEnchere, int prixInitial) throws Exception {

		return null;
	}
	
	
}
