package fr.eni.projetenchere.dal;

import fr.eni.projetenchere.bo.Utilisateur;

public interface UtilisateurDAO {

	public Utilisateur getUtilisateurByPseudo(String pseudo) throws Exception;
}
