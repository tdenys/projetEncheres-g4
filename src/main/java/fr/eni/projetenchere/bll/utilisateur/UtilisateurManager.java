package fr.eni.projetenchere.bll.utilisateur;

import fr.eni.projetenchere.bo.Utilisateur;

public interface UtilisateurManager {
	
	public Utilisateur getUtilisateurByPseudoAndMDP(String pseudo, String MDP) throws Exception;
	
	public Utilisateur getUtilisateurByPseudo(String pseudo) throws Exception;
	
	public Utilisateur getUtilisateurById(int no_utilisateur) throws Exception;
	 
	public Utilisateur insertUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String code_postal, String ville, String mots_de_passe, String mots_de_passe_confirmation) throws Exception;

	boolean isConnectedUser(String pseudoActuelle, String pseudoAVerifier) throws Exception;

	public Utilisateur updateUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String code_postal, String ville, String motDePasseActuel, String nouveauMotDePasse, String motDePasseConfirmation, String pseudoActuel) throws Exception;

	public void removeUtilisateur(Utilisateur u) throws Exception;

	public Utilisateur getUtilisateurByPseudoAndMDPWithCookies(String login, String MDP) throws Exception;
}
