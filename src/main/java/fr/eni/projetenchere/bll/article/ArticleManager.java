package fr.eni.projetenchere.bll.article;

import java.util.Date;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;

public interface ArticleManager {
	
	public List<Article> getAll() throws Exception; 
	
	public Article insertArticle(String pseudoVendeur, String nomArticle, String description, Date dateDebutEnchere, Date dateFinEnchere, int prixInitial, int numCategorie) throws Exception;
	
	public List<Article> getAllWithFilter(String filtres, int categorie, String type, boolean param1, boolean param2, boolean param3 ) throws Exception;
	
	void removeArticle(Article article) throws Exception;

	Article updateArticle(String pseudoVendeur, String nomArticle, String description, Date dateDebutEnchere,
			Date dateFinEnchere, int prixInitial, int numCategorie) throws Exception;
}
