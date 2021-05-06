package fr.eni.projetenchere.bll.article;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.dal.ArticleDAO;
import fr.eni.projetenchere.dal.ArticleDAOFactory;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class ArticleManagerImpl implements ArticleManager {
	
	private ArticleDAO articleDAO = ArticleDAOFactory.getArticleDAO();

	private UtilisateurDAO utilisateurDAO = UtilisateurDAOFactory.getUtilisateurDAO(); 
	
	@Override
	public List<Article> getAll() throws Exception {
		return articleDAO.getAll();
	}
	
	@Override
	public Article insertArticle(String pseudoVendeur, String nomArticle, String description, Date dateDebutEnchere,
			Date dateFinEnchere, int prixInitial, int numCategorie) throws Exception {
		
		//Validation des saisies utilisateur
		datesValides(dateDebutEnchere, dateFinEnchere);
		stringValide(description, "Description"); 
		
		//on récupère l'id du vendeur de l'article
		int numVendeur = utilisateurDAO.getUtilisateurByPseudo(pseudoVendeur).getNo_utilisateurs();
		
		//création de l'article à insérer
		Article articleAInserer = new Article(nomArticle, description, dateDebutEnchere, dateFinEnchere, prixInitial, prixInitial, numVendeur, numCategorie);
		
		
		return articleDAO.insertArticle(articleAInserer); 
	}
	
	private void datesValides(Date dateDebut, Date dateFin) throws Exception {
		if(dateDebut.after(dateFin)) {
			throw new Exception("La date de début ne peut pas être supérieur à la date de fin.");
		}
		
	}
	
	private void stringValide(String str, String type) throws Exception {
        if(str.contains("<")) {
        	throw new Exception(type + " incorrect"); 
		}
	}
	
	public List<Article> getAllWithFilter(String filtres, int categorie, String type, boolean param1, boolean param2, boolean param3 ) throws Exception{
		List<Article> listeDeBase = articleDAO.getAll();
		List<Article> listeResult = new ArrayList<Article>();
		for( Article a : listeDeBase ) {
			if(categorie != 0) {
				if(filtres.contains(a.getNom_article()) && a.getNo_categorie() == categorie) {
	            	listeResult.add(a);
	            }
			}else {
				if(filtres.contains(a.getNom_article())) {
	            	listeResult.add(a);
	            }
			}
            
        }
		return listeResult;
	}
	
}
