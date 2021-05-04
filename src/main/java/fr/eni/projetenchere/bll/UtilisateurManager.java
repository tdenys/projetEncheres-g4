package fr.eni.projetenchere.bll;

import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class UtilisateurManager {
	
	private UtilisateurDAO DAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	public Utilisateur getUtilisateurByPseudoAndMDP(String pseudo, String MDP) throws Exception {
		Utilisateur u  = DAO.getUtilisateurByPseudo(pseudo);
		if(u.getMot_de_passe() == MDP) {
			return u;
		}else {
			throw new Exception("Erreur BLL: getUtilisateurByPseudoAndMDP");
		}
	}

}
