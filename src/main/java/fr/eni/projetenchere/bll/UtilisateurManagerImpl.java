package fr.eni.projetenchere.bll;

import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class UtilisateurManagerImpl implements UtilisateurManager{
	
	private UtilisateurDAO DAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	public Utilisateur getUtilisateurByPseudoAndMDP(String pseudo, String MDP) throws Exception {
		Utilisateur u1  = DAO.getUtilisateurByPseudo(pseudo);
		if(u1.getMot_de_passe().equals(MDP)) {
			return u1;
		}else {
			return null;
		}
	}

}
