package fr.eni.projetenchere.dal.enchere;

import java.sql.Connection;

import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Utilisateur;

public interface EnchereDAO {

	public Enchere insertEnchere(Enchere e) throws Exception;
	
	public Enchere insertEnchere(Connection connection, Enchere e) throws Exception;
	
	public Enchere getEnchereById(int id) throws Exception;

	public Utilisateur getUtilisateurLastEnchere(Connection cnx, int no_article) throws Exception;
}
