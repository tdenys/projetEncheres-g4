package fr.eni.projetenchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetenchere.bo.Utilisateur;

public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	private String GET_UTILISATEUR_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
	private String GET_UTILISATEUR_BY_PSEUDO_OR_EMAIL = "SELECT * FROM UTILISATEURS WHERE pseudo = ? OR email = ?";
	private String GET_UTILISATEUR_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
	private String GET_UTILISATEUR_BY_EMAIL = "SELECT * FROM UTILISATEURS WHERE email = ?";
	private String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?,"
			+ "rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE pseudo = ?";
	private String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE pseudo = ?";
	
	@Override
	public Utilisateur getUtilisateurById(int id) throws Exception {
		
		Utilisateur u = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_UTILISATEUR_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						rs.getString("rue"),rs.getString("code_postal"),rs.getString("ville"),rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur"));
			}
		}
		catch(Exception e) {
			throw new Exception(GET_UTILISATEUR_BY_PSEUDO_OR_EMAIL);
		}
		
		return u;
	}
	
	@Override
	public Utilisateur getUtilisateurByPseudoOrEmail(String pseudo) throws Exception {
		
		Utilisateur u = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_UTILISATEUR_BY_PSEUDO_OR_EMAIL);
			stmt.setString(1, pseudo);
			stmt.setString(2, pseudo);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = new Utilisateur(rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						rs.getString("rue"),rs.getString("code_postal"),rs.getString("ville"),rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur"));
			}
		}
		catch(Exception e) {
			throw new Exception(GET_UTILISATEUR_BY_PSEUDO_OR_EMAIL);
		}
		
		return u;
	}
	
	@Override
	public Utilisateur getUtilisateurByPseudo(String pseudo) throws Exception {
		
		Utilisateur u = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_UTILISATEUR_BY_PSEUDO);
			stmt.setString(1, pseudo);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						rs.getString("rue"),rs.getString("code_postal"),rs.getString("ville"),rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur"));
			}
		}
		catch(Exception e) {
			throw new Exception(GET_UTILISATEUR_BY_PSEUDO);
		}
		
		return u;
	}
	
	@Override
	public Utilisateur getUtilisateurByEmail(String email) throws Exception {
		
		Utilisateur u = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_UTILISATEUR_BY_EMAIL);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = new Utilisateur(rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						rs.getString("rue"),rs.getString("code_postal"),rs.getString("ville"),rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur"));
			}
		}
		catch(Exception e) {
			throw new Exception(GET_UTILISATEUR_BY_EMAIL);
		}
		
		return u;
	}
	
	@Override
	public void insertUtilisateur(Utilisateur u) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(INSERT_UTILISATEUR);
			stmt.setString(1, u.getPseudo());
			stmt.setString(2, u.getNom());
			stmt.setString(3, u.getPrenom());
			stmt.setString(4, u.getEmail());
			stmt.setString(5, u.getTelephone());
			stmt.setString(6, u.getRue());
			stmt.setString(7, u.getCode_postal());
			stmt.setString(8, u.getVille());
			stmt.setString(9, u.getMot_de_passe());
			stmt.setInt(10, u.getCredit());
			stmt.setBoolean(11, u.isAdministrateur());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(INSERT_UTILISATEUR);
		}
	}
	
	@Override
	public Utilisateur updateUtilisateur(Utilisateur u, String ancienPseudo) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_UTILISATEUR);
			stmt.setString(1, u.getPseudo());
			stmt.setString(2, u.getNom());
			stmt.setString(3, u.getPrenom());
			stmt.setString(4, u.getEmail());
			stmt.setString(5, u.getTelephone());
			stmt.setString(6, u.getRue());
			stmt.setString(7, u.getCode_postal());
			stmt.setString(8, u.getVille());
			stmt.setString(9, u.getMot_de_passe());
			stmt.setString(10, ancienPseudo);

			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(UPDATE_UTILISATEUR);
		}
		
		return u;
	}

	@Override
	public void removeUtilisateur(Utilisateur u) throws Exception {
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(DELETE_UTILISATEUR);
			stmt.setString(1, u.getPseudo());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(DELETE_UTILISATEUR);
		}
	}
	
	/*public List<Utilisateur> getAll() throws Exception {
		
		Utilisateur r = null;
		List<Utilisateur> result = new ArrayList<Utilisateur>();
		String req = "SELECT * FROM UTILISATEURS";
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(req);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				r = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"), rs.getTime("heure_Utilisateur"), this.getUtilisateursById(rs.getInt("id")));
				result.add(r);
			}
		}
		catch(Exception e) {
			throw new Exception(req);
		}	
		return result;
	}*/
	
}
