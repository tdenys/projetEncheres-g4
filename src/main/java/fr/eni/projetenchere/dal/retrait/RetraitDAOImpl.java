package fr.eni.projetenchere.dal.retrait;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.dal.ConnectionProvider;

public class RetraitDAOImpl implements RetraitDAO {
	
	private String GET_RETRAIT_BY_ID = "SELECT * FROM RETRAITS WHERE no_article = ?";
	private String INSERT_RETRAIT = "INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES (?,?,?,?)";
	private String UPDATE_RETRAIT = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
	private String DELETE_RETRAIT = "DELETE FROM RETRAITS WHERE no_article = ?";
	
	@Override
	public Retrait getRetraitById(int id) throws Exception {
		Retrait r = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_RETRAIT_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				r = new Retrait(id, rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
			}
		}
		catch(Exception e) {
			throw new Exception(GET_RETRAIT_BY_ID);
		}
		
		return r;
	}
	
	@Override
	public Retrait insertRetrait(Retrait r) throws Exception {	
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(INSERT_RETRAIT);
			stmt.setInt(1, r.getNo_article());
			stmt.setString(2, r.getRue());
			stmt.setString(3, r.getCode_postal());
			stmt.setString(4, r.getVille());
			int nbRows = stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(INSERT_RETRAIT);
		}
		return r;
	}
	
	@Override
	public Retrait insertRetrait(Connection cnx, Retrait r) throws Exception {	
		try{
			PreparedStatement stmt = cnx.prepareStatement(INSERT_RETRAIT);
			stmt.setInt(1, r.getNo_article());
			stmt.setString(2, r.getRue());
			stmt.setString(3, r.getCode_postal());
			stmt.setString(4, r.getVille());
			int nbRows = stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(INSERT_RETRAIT);
		}
		return r;
	}
	
	@Override
	public Retrait updateRetrait(Retrait r) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_RETRAIT);
			stmt.setString(1, r.getRue());
			stmt.setString(2, r.getCode_postal());
			stmt.setString(3, r.getVille());
			stmt.setInt(4, r.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(UPDATE_RETRAIT);
		}
		return r;
	}
	
	@Override
	public Retrait updateRetrait(Connection cnx, Retrait r) throws Exception {
		try {
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_RETRAIT);
			stmt.setString(1, r.getRue());
			stmt.setString(2, r.getCode_postal());
			stmt.setString(3, r.getVille());
			stmt.setInt(4, r.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(UPDATE_RETRAIT);
		}
		return r;
	}
	
	@Override
	public void removeRetrait(Retrait r) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(DELETE_RETRAIT);
			stmt.setInt(1, r.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(DELETE_RETRAIT);
		}
	}
	
	@Override
	public void removeRetrait(Connection cnx, Retrait r) throws Exception {
		try {
			PreparedStatement stmt = cnx.prepareStatement(DELETE_RETRAIT);
			stmt.setInt(1, r.getNo_article());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(DELETE_RETRAIT);
		}
	}
	
	
}
