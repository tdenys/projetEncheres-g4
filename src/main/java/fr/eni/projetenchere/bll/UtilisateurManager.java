package fr.eni.projetenchere.bll;

import fr.eni.projetenchere.bo.Utilisateur;

public interface UtilisateurManager {
	
	public Utilisateur getUtilisateurByPseudoAndMDP(String pseudo, String MDP) throws Exception;

}
