package fr.eni.projetenchere.dal;

import fr.eni.projetenchere.bo.Utilisateur;

public interface UtilisateurDAO {

	public Utilisateur getUtilisateurByPseudo(String pseudo) throws Exception;
	
	public Utilisateur getUtilisateurByEmail(String email) throws Exception;
	
	public void insertUtilisateur(Utilisateur u) throws Exception;

	Utilisateur getUtilisateurByPseudoOrEmail(String pseudo) throws Exception;
}
