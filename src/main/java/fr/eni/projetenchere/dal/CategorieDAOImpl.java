package fr.eni.projetenchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetenchere.bo.Categorie;

public class CategorieDAOImpl implements CategorieDAO { 
	
	private String GET_CATEGORIE_BY_ID = "SELECT * FROM CATEGORIES WHERE NO_CATEGORIE = ?";
	private String GET_ALL_CATEGORIES = "SELECT * FROM CATEGORIES";
	
	@Override
	public Categorie getCategorieById(int id) throws Exception {
		Categorie c = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_CATEGORIE_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				c = new Categorie(id, rs.getString("libelle"));
			}
		}
		catch(Exception e) {
			throw new Exception(GET_CATEGORIE_BY_ID);
		}
		
		return c;
	}
	
	public List<Categorie> getAll() throws Exception {
		Categorie c = null;
		List<Categorie> result = new ArrayList<Categorie>();
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_ALL_CATEGORIES);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				c = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
				result.add(c);
			}
		}
		catch(Exception e) {
			throw new Exception(GET_ALL_CATEGORIES);
		}	
		return result;
	} 
	
}
