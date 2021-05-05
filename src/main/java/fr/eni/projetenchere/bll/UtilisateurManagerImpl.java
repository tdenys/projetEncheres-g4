package fr.eni.projetenchere.bll;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class UtilisateurManagerImpl implements UtilisateurManager{
	
	private UtilisateurDAO DAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	//Utilisé pour l'inscription d'un utilisateur
	@Override
	public Utilisateur getUtilisateurByPseudoAndMDP(String login, String MDP) throws Exception{
		Utilisateur u1 = new Utilisateur();
		
		try {
			u1 = DAO.getUtilisateurByPseudoOrEmail(login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(MDP.getBytes());
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		String mdpEncode = sb.toString();
		
		if(u1 == null) {
			throw new Exception("Identifiant ou mot de passe incorrect !");
		}else if(mdpEncode.equals(u1.getMot_de_passe())) {
			return u1;
		}else {
			throw new Exception("Identifiant ou mot de passe incorrect !");
		}	
	}
	
	@Override
	public Utilisateur getUtilisateurByPseudo(String pseudo) throws Exception {
		Utilisateur u = new Utilisateur();
		
		try {
			u = DAO.getUtilisateurByPseudo(pseudo);
		} catch(Exception e) {
			throw new Exception("Le compte utilisateur n'a pas été trouvé.");
		}
			
		return u;
	}
	
	
	/*
	 * Return true si l'utilisateur connecté veut regarder son profil ou false s'il veut voir celui d'un autre utilisateur	
	*/
	@Override
	public boolean isConnectedUser(String pseudoActuelle, String pseudoAVerifier) throws Exception {
		boolean isConnectedUser = false;
		
		if(pseudoActuelle.equals(pseudoAVerifier)) {
			isConnectedUser = true;
		}
		
		return isConnectedUser;
	}

	@Override
	public Utilisateur insertUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mots_de_passe, String mots_de_passe_confirmation)
			throws Exception {
		
		if(DAO.getUtilisateurByPseudo(pseudo) != null) {
			throw new Exception("Pseudo déjà existant !");
		}
		
		if(DAO.getUtilisateurByEmail(email) != null) {
			throw new Exception("Email déjà existant !");
		}
		
		if(!pseudo.matches("^[a-zA-Z0-9]*$")) {
			throw new Exception("Le pseudo ne peut contenir que des caractères alphanuméric !");
		}
		
		if(!mots_de_passe.equals(mots_de_passe_confirmation)) {
			throw new Exception("Les 2 mots de passe ne correspondent pas !");
		}
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(mots_de_passe.getBytes());
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		String mdpEncode = sb.toString();
		
		Utilisateur u1 = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdpEncode, 100, false);
		DAO.insertUtilisateur(u1);
		
		return u1;
	}

}
