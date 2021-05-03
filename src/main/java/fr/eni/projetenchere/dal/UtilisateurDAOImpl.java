package fr.eni.projetenchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetenchere.dal.ConnectionProvider;
import fr.eni.projetenchere.bo.Utilisateur;

public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	private String GET_UTILISATEUR_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
	
	@Override
	public Utilisateur getUtilisateurByPseudo(String pseudo) throws Exception {
		
		Utilisateur u = null;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(GET_UTILISATEUR_BY_PSEUDO);
			stmt.setString(1, pseudo);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = new Utilisateur(rs.getString("pseudo"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
						rs.getString("rue"),rs.getString("code_postal"),rs.getString("ville"),rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur"));
			}
		}
		catch(Exception e) {
			throw new Exception(GET_UTILISATEUR_BY_PSEUDO);
		}
		
		return u;
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
