package fr.eni.projetenchere.bll.categorie;

import fr.eni.projetenchere.bll.article.ArticleManager;
import fr.eni.projetenchere.bll.article.ArticleManagerImpl;

public class CategorieManagerFact {

	private CategorieManagerFact() {
	}
	
	public static CategorieManager getInstance() {
		return new CategorieManagerImpl();
	}
}
