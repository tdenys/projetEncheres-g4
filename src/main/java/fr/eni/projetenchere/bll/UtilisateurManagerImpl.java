package fr.eni.projetenchere.bll;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.UtilisateurDAO;
import fr.eni.projetenchere.dal.UtilisateurDAOFactory;

public class UtilisateurManagerImpl implements UtilisateurManager{
	
	private UtilisateurDAO DAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	//Expression r�guli�re pour tester la validit� d'un email
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	//Utilis� pour l'inscription d'un utilisateur
	@Override
	public Utilisateur getUtilisateurByPseudoAndMDP(String login, String MDP) throws Exception{
		Utilisateur u1 = new Utilisateur();
		
		try {
			u1 = DAO.getUtilisateurByPseudoOrEmail(login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String mdpEncode = crypt(MDP);
		
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
			throw new Exception("Le compte utilisateur n'a pas �t� trouv�.");
		}
			
		return u;
	}
	
	
	/*
	 * Return true si l'utilisateur connect� veut regarder son profil ou false s'il veut voir celui d'un autre utilisateur	
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
		
		//V�rification de la validit� de l'email
		validateEmail(email);
		
		if(DAO.getUtilisateurByPseudo(pseudo) != null) {
			throw new Exception("Pseudo d�j� existant !");
		}
		
		if(DAO.getUtilisateurByEmail(email) != null) {
			throw new Exception("Email d�j� existant !");
		}
		
		if(!pseudo.matches("^[a-zA-Z0-9]*$")) {
			throw new Exception("Le pseudo ne peut contenir que des caract�res alphanum�ric !");
		}
		
		if(!mots_de_passe.equals(mots_de_passe_confirmation)) {
			throw new Exception("Les 2 mots de passe ne correspondent pas !");
		}
	
		String mdpEncode = crypt(mots_de_passe);
		
		Utilisateur u1 = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdpEncode, 100, false);
		DAO.insertUtilisateur(u1);
		
		return u1;
	}
	
	@Override
	public Utilisateur updateUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String motDePasseActuel, String nouveauMotDePasse, String motDePasseConfirmation, String ancienPseudo)
			throws Exception {
		
		String motDePasseAMettre;
		String ancienEmail;
		String ancienMotDePasse;
		
		//V�rification de la validit� de l'email
		validateEmail(email);
		
		/* mot de passe avant modif */
		ancienMotDePasse = DAO.getUtilisateurByPseudo(ancienPseudo).getMot_de_passe();
		
		/* test si pseudo unique */
		if(!pseudo.equals(ancienPseudo) && DAO.getUtilisateurByPseudo(pseudo) != null) {
			throw new Exception("Pseudo d�j� existant !");
		}
		
		/* test si email unique */
		ancienEmail = DAO.getUtilisateurByPseudo(ancienPseudo).getEmail();
		if(!email.equals(ancienEmail) && DAO.getUtilisateurByEmail(email) != null) {
			throw new Exception("Email d�j� existant !");
		}
		
		/* cryptage du nouveau mdp */
		motDePasseActuel = crypt(motDePasseActuel);
		
		/* test si mdp saisi est egal au mdp de l'user */
		if(!ancienMotDePasse.equals(motDePasseActuel)) {
			throw new Exception("Mot de passe incorrect !");
		}
		
		/* si il a ete modifier, cryptage + verif si les 2 ok */
		if(!nouveauMotDePasse.isEmpty()) {
			if(!nouveauMotDePasse.equals(motDePasseConfirmation)) {
				throw new Exception("Les 2 mots de passe ne correspondent pas !");
			}
			motDePasseAMettre = crypt(nouveauMotDePasse);
		}else {
			motDePasseAMettre = ancienMotDePasse;
		}
		
		/* envoi de l'utilisateur avec un nouveau parametre*/
		Utilisateur u1 = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, motDePasseAMettre, 0, false);
		DAO.updateUtilisateur(u1, ancienPseudo);
		
		return u1;
	}
	
	private String crypt(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(text.getBytes());
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}
	
	private void validateEmail(String emailStr) throws Exception {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        if(!matcher.find()) {
        	throw new Exception("Email incorrect"); 
        }
	}

}
