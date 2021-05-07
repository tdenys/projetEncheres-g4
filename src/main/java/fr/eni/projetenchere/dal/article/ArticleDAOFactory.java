package fr.eni.projetenchere.dal.article;

public class ArticleDAOFactory {

	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOImpl();
	}
	
}
