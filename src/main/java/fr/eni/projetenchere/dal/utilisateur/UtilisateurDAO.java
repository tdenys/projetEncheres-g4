package fr.eni.projetenchere.dal.utilisateur;

import java.sql.Connection;

import fr.eni.projetenchere.bo.Utilisateur;

public interface UtilisateurDAO {

	public Utilisateur getUtilisateurByPseudo(String pseudo) throws Exception;
	
	public Utilisateur getUtilisateurByEmail(String email) throws Exception;

	public Utilisateur getUtilisateurByPseudoOrEmail(String pseudo) throws Exception;
	
	public void insertUtilisateur(Utilisateur u) throws Exception;

	public void removeUtilisateur(Utilisateur u) throws Exception;

	public Utilisateur getUtilisateurById(int id) throws Exception;
	
	public Utilisateur updateUtilisateur(Utilisateur u, String ancienPseudo) throws Exception;
	public Utilisateur updateUtilisateur(Connection cnx, Utilisateur u, String ancienPseudo) throws Exception;

}
