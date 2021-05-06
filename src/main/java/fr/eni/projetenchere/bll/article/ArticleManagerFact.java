package fr.eni.projetenchere.bll.article;

public class ArticleManagerFact {
	
	private ArticleManagerFact() {
	}
	
	public static ArticleManager getInstance() {
		return new ArticleManagerImpl();
	}
	
}
