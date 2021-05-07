package fr.eni.projetenchere.dal;

import java.sql.Connection;

import fr.eni.projetenchere.bo.Enchere;

public interface EnchereDAO {

	public Enchere insertEnchere(Enchere e) throws Exception;
	
	public Enchere insertEnchere(Connection connection, Enchere e) throws Exception;
	
	public Enchere getEnchereById(int id) throws Exception;
}
