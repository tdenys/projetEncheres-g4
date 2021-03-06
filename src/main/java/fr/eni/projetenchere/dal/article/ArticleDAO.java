package fr.eni.projetenchere.dal.article;

import java.sql.Connection;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Utilisateur;

public interface ArticleDAO {

	public Article getArticleById(int id) throws Exception;
	
	public List<Article> getAll() throws Exception;
	 
	public List<Article> getAllWithFilter(String filtres, int categorie, String type, boolean param1, boolean param2, boolean param3, Utilisateur u) throws Exception;
	
	public Article insertArticle(Article a) throws Exception;
	Article insertArticle(Connection cnx, Article a) throws Exception;

	public void removeArticle(Article a) throws Exception;
	public void removeArticle(Connection cnx, Article a) throws Exception;

	public Article updateArticle(Article a) throws Exception;
	public Article updateArticle(Connection cnx, Article a) throws Exception;
	
	public void updateArticleVendu(Article article,  boolean vendu) throws Exception;

	public int getNbArticlesByUtilisateur(Connection cnx, Utilisateur u) throws Exception;
}
