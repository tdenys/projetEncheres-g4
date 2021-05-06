package fr.eni.projetenchere.bll.article;

import java.util.Date;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Categorie;

public interface ArticleManager {
	
	public List<Article> getAll() throws Exception;
	
	public Article insertArticle(String pseudoVendeur, String nomArticle, String description, Date dateDebutEnchere, Date dateFinEnchere, int prixInitial, int numCategorie) throws Exception;
}
