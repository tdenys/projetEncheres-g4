package fr.eni.projetenchere.dal.article;

import java.sql.Connection;
import java.util.List;

import fr.eni.projetenchere.bo.Article;

public interface ArticleDAO {

	public Article getArticleById(int id) throws Exception;
	
	public List<Article> getAll() throws Exception;
	
	public List<Article> getAllWithFilter(String filtres, int categorie, String type, boolean param1, boolean param2, boolean param3) throws Exception;
	
	public Article insertArticle(Article a) throws Exception;

	public void removeArticle(Article a) throws Exception;

	public Article updateArticle(Article a) throws Exception;
	public Article updateArticle(Connection cnx, Article a) throws Exception;

}
