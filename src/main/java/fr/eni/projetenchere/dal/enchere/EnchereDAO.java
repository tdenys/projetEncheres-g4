package fr.eni.projetenchere.dal.enchere;

import java.sql.Connection;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Utilisateur;

public interface EnchereDAO {

	public Enchere insertEnchere(Enchere e) throws Exception;
	
	public Enchere insertEnchere(Connection connection, Enchere e) throws Exception;
	
	public Enchere getEnchereById(int id) throws Exception;

	public Utilisateur getUtilisateurLastEnchere(Connection cnx, int no_article) throws Exception;

	public int getNbEncheresByUtilisateur(Connection cnx, Utilisateur u) throws Exception;
	
	public Utilisateur getUtilisateurWhoWin(Article a) throws Exception;

	public Utilisateur getDernierEncherisseur(int no_article) throws Exception;
}
