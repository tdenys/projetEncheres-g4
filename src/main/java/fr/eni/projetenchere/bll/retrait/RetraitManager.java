package fr.eni.projetenchere.bll.retrait;

import fr.eni.projetenchere.bo.Retrait;

public interface RetraitManager {

	public Retrait insertRetrait(int numArticle, String rue, String codePostal, String ville) throws Exception;

	public Retrait getRetraitByIdArticle(int numArticle);

	public Retrait updateRetrait(int numArticle, String rue, String codePostal, String ville) throws Exception;

	public Retrait removeRetrait(int numArticle) throws Exception;
	
}
