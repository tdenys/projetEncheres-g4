package fr.eni.projetenchere.bll;

import fr.eni.projetenchere.bo.Utilisateur;

public interface UtilisateurManager {
	
	public Utilisateur getUtilisateurByPseudoAndMDP(String pseudo, String MDP) throws Exception;
	
	public Utilisateur insertUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String code_postal, String ville, String mots_de_passe, String mots_de_passe_confirmation) throws Exception;

}
