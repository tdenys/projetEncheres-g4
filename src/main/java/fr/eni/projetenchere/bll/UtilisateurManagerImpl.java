package fr.eni.projetenchere.bll;

import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class UtilisateurManagerImpl implements UtilisateurManager{
	
	private UtilisateurDAO DAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	public Utilisateur getUtilisateurByPseudoAndMDP(String login, String MDP) throws Exception{
		Utilisateur u1 = new Utilisateur();
		
		try {
			u1 = DAO.getUtilisateurByPseudo(login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(u1 == null) {
			try {
				u1 = DAO.getUtilisateurByEmail(login);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(u1 == null) {
			throw new Exception("Le compte utilisateur n'a pas été trouvé.");
		}else if(MDP.equals(u1.getMot_de_passe())) {
			return u1;
		}else {
			throw new Exception("Mauvais mot de passe !");
		}
		
	}

}
