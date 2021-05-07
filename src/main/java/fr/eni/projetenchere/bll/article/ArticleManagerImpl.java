package fr.eni.projetenchere.bll.article;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Utilisateur;
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
		prixInitialValide(prixInitial);
		
		//on récupère l'id du vendeur de l'article
		Utilisateur vendeur = utilisateurDAO.getUtilisateurByPseudo(pseudoVendeur);
		
		//création de l'article à insérer
		Article articleAInserer = new Article(nomArticle, description, dateDebutEnchere, dateFinEnchere, prixInitial, prixInitial, vendeur, numCategorie);
		
		
		return articleDAO.insertArticle(articleAInserer);
	}
	
	public List<Article> getAllWithFilter(String filtres, int categorie, String type, boolean param1, boolean param2, boolean param3 ) throws Exception{
		List<Article> listeDeBase = articleDAO.getAll();
		List<Article> listeResult = new ArrayList<Article>();
		for( Article a : listeDeBase ) {
			if(categorie != 0) {
				if(!filtres.isEmpty()) {
					if(a.getNom_article().toLowerCase().contains(filtres.toLowerCase()) && a.getNo_categorie() == categorie) {
		            	listeResult.add(a);
		            }
				}else {
					if(a.getNo_categorie() == categorie) {
		            	listeResult.add(a);
		            }
				}
			}else {
				if(a.getNom_article().toLowerCase().contains(filtres.toLowerCase())) {
	            	listeResult.add(a);
	            }
			}
            
        }
		return listeResult; 
	}
	
	@Override
	public Article updateArticle(String pseudoVendeur, String nomArticle, String description, Date dateDebutEnchere,
			Date dateFinEnchere, int prixInitial, int numCategorie) throws Exception {
		
		//Validation des saisies utilisateur
		datesValides(dateDebutEnchere, dateFinEnchere);
		stringValide(description, "Description"); 
		
		//on récupère l'id du vendeur de l'article
		Utilisateur vendeur = utilisateurDAO.getUtilisateurByPseudo(pseudoVendeur);
		
		//création de l'article à insérer
		Article articleAUpdate = new Article(nomArticle, description, dateDebutEnchere, dateFinEnchere, prixInitial, prixInitial, vendeur, numCategorie);
		
		return articleDAO.updateArticle(articleAUpdate);
	}
	
	@Override
	public void removeArticle(Article article) throws Exception {
		articleDAO.removeArticle(article);
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
	
	private void prixInitialValide(int prix) throws Exception {
		if(prix <= 0) {
			throw new Exception("Le prix saisi est incorrect");
		}
	}
	
}
