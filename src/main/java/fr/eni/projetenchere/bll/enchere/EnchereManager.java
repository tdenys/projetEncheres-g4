package fr.eni.projetenchere.bll.enchere;

import java.util.Date;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Utilisateur;

public interface EnchereManager {
	
	public Enchere insertEnchere(int montant_enchere, Article article, Utilisateur utilisateur) throws Exception;

	/*public Enchere getEnchereByIdArticle(int numArticle) throws Exception;

	void removeEnchere(Enchere e) throws Exception;*/
}
