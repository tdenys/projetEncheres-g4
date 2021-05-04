package fr.eni.projetenchere.bll;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class UtilisateurManagerImpl implements UtilisateurManager{
	
	private UtilisateurDAO DAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	public Utilisateur getUtilisateurByPseudoAndMDP(String login, String MDP) throws Exception{
		Utilisateur u1 = new Utilisateur();
		
		try {
			u1 = DAO.getUtilisateurByPseudoOrEmail(login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		String mdpEncode = md.digest(MDP.getBytes()).toString();
		
		if(u1 == null) {
			throw new Exception("Le compte utilisateur n'a pas été trouvé.");
		}else if(mdpEncode.equals(u1.getMot_de_passe())) {
			return u1;
		}else {
			throw new Exception("Mauvais mot de passe !");
		}
		
	}

	@Override
	public Utilisateur insertUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mots_de_passe, String mots_de_passe_confirmation)
			throws Exception {
		
		if(!pseudo.matches("^[a-zA-Z0-9]*$")) {
			throw new Exception("Le pseudo ne peut contenir que des caractères alphanuméric !");
		}
		
		if(!mots_de_passe.equals(mots_de_passe_confirmation)) {
			throw new Exception("Les 2 mots de passe ne correspondent pas !");
		}
		MessageDigest md = MessageDigest.getInstance("MD5");
		String mdp = md.digest(mots_de_passe.getBytes()).toString();
		System.out.println(mdp);
		
		Utilisateur u1 = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp, 100, false);
		DAO.insertUtilisateur(u1);
		
		return u1;
	}

}
