package fr.eni.projetenchere.dal.enchere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.dal.ConnectionProvider;

public class EnchereDAOImpl implements EnchereDAO {

	private String INSERT_ENCHERE = "INSERT INTO ENCHERE(date_enchere, montant_enchere, no_article, no_utilisateur) VALUES (?,?,?,?)";
	private String GET_ENCHERE_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE NO_ARTICLE = ?";
	
	@Override
	public Enchere insertEnchere(Enchere e) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ENCHERE, Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, new java.sql.Date(e.getDate_enchere().getTime()));
			stmt.setInt(2, e.getMontant_enchere());
			stmt.setInt(3, e.getArticle().getNo_article());
			stmt.setInt(4, e.getUtilisateur().getNo_utilisateurs());
			int nbRows = stmt.executeUpdate();
			if (nbRows == 1) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					e.setNo_enchere(rs.getInt(1));
				}
			}
		}
		catch(Exception exception) {
			throw new Exception(INSERT_ENCHERE);
		}
		return e;
	}
	

	@Override
	public Enchere insertEnchere(Connection cnx, Enchere e) throws Exception {
		try(cnx){
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ENCHERE, Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, new java.sql.Date(e.getDate_enchere().getTime()));
			stmt.setInt(2, e.getMontant_enchere());
			stmt.setInt(3, e.getArticle().getNo_article());
			stmt.setInt(4, e.getUtilisateur().getNo_utilisateurs());
			int nbRows = stmt.executeUpdate();
			if (nbRows == 1) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					e.setNo_enchere(rs.getInt(1));
				}
			}
		}
		catch(Exception exception) {
			throw new Exception(INSERT_ENCHERE);
		}
		return e;
	}

	@Override
	public Enchere getEnchereById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
