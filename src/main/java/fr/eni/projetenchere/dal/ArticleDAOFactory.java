package fr.eni.projetenchere.dal;

public class ArticleDAOFactory {

	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOImpl();
	}
	
}
