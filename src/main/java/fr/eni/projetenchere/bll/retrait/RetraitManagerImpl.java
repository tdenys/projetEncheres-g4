package fr.eni.projetenchere.bll.retrait;

import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.dal.retrait.*;

public class RetraitManagerImpl implements RetraitManager{

	private RetraitDAO retraitDAO = RetraitDAOFactory.getRetraitDAO();
	
	@Override
	public Retrait getRetraitByIdArticle(int numArticle) throws Exception {
		
		return retraitDAO.getRetraitById(numArticle);
	}
	
	@Override
	public Retrait updateRetrait(int numArticle, String rue, String codePostal, String ville) throws Exception {
		
		//Validation du formulaire
		stringValide(rue, "Libellé rue");
		stringValide(codePostal, "Code postal");
		stringValide(ville, "Libellé ville");
		
		//Création de l'objet à insérer en BDD
		Retrait retraitAModifier = new Retrait(numArticle, rue, codePostal, ville);
		
		return retraitDAO.updateRetrait(retraitAModifier);
	}
	
	@Override
	public Retrait insertRetrait(int numArticle, String rue, String codePostal, String ville) throws Exception {
		
		//Validation du formulaire
		stringValide(rue, "Libellé rue");
		stringValide(codePostal, "Code postal");
		stringValide(ville, "Libellé ville");
		
		//Création de l'objet à insérer en BDD
		Retrait retraitAInserer = new Retrait(numArticle, rue, codePostal, ville);
		
		return retraitDAO.insertRetrait(retraitAInserer);
	}
	
	@Override
	public void removeRetrait(Retrait retrait) throws Exception {
		
		retraitDAO.removeRetrait(retrait);
	}
	
	private void stringValide(String str, String type) throws Exception {
        if(str.contains("<")) {
        	throw new Exception(type + " incorrect"); 
		}
	}

}
