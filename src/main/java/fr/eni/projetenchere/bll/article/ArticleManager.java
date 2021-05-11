package fr.eni.projetenchere.bll.article;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;

public interface ArticleManager {
	
	public List<Article> getAll() throws Exception; 
	
	public Article insertArticle(Article a, String rue, String cp, String ville) throws Exception;
	
	public List<Article> getAllWithFilter(String filtres, int categorie, String type, boolean param1, boolean param2, boolean param3, Utilisateur u) throws Exception;
	
	void removeArticle(Article article) throws Exception;

	public Article updateArticle(String pseudoVendeur, String nomArticle,
	 String description, Date dateDebutEnchere,
			Date dateFinEnchere, int prixInitial, Categorie categorie) throws Exception;

	public Article getById(int id) throws Exception; 
	
	public int getNbArticlesByUtilisateur(Connection cnx, Utilisateur u) throws Exception;
	
	public void updateArticleAndRetrait(Article a, Retrait r) throws Exception;
}
