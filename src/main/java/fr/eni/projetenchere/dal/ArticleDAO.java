package fr.eni.projetenchere.dal;

import java.util.List;

import fr.eni.projetenchere.bo.Article;

public interface ArticleDAO {

	public Article getArticleById(int id) throws Exception;
	
	public List<Article> getAll() throws Exception;
	
	public Article insertArticle(Article a) throws Exception;

	public Article updateArticle(Article a) throws Exception;

	public void removeArticle(Article a) throws Exception;

}
