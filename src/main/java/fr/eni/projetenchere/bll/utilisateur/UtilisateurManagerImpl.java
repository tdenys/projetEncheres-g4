package fr.eni.projetenchere.bll.utilisateur;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.projetenchere.bll.article.ArticleManager;
import fr.eni.projetenchere.bll.article.ArticleManagerFact;
import fr.eni.projetenchere.bll.enchere.EnchereManager;
import fr.eni.projetenchere.bll.enchere.EnchereManagerFact;
import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.ConnectionProvider;
import fr.eni.projetenchere.dal.article.ArticleDAO;
import fr.eni.projetenchere.dal.article.ArticleDAOFactory;
import fr.eni.projetenchere.dal.enchere.EnchereDAO;
import fr.eni.projetenchere.dal.enchere.EnchereDAOFactory;
import fr.eni.projetenchere.dal.utilisateur.*;

public class UtilisateurManagerImpl implements UtilisateurManager{
	
	private UtilisateurDAO DAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	private ArticleManager articleManager = ArticleManagerFact.getInstance();
	
	private EnchereManager enchereManager = EnchereManagerFact.getInstance();
	
	//Expression régulière pour tester la validité d'un email
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	//Utilisé pour l'inscription d'un utilisateur
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
		
		//Vérification du formulaire
		validateEmail(email);
		validateSimpleString(nom, "Nom");
		validateSimpleString(prenom, "Prénom");
		validateSimpleString(ville, "Libellé ville");
		validateSimpleString(telephone, "Téléphone");
		validateSimpleString(rue, "Libellé rue");
		validateSimpleString(code_postal, "Code postal");
		
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
		
		//Vérification du formulaire
		validateEmail(email);
		validateSimpleString(nom, "Nom");
		validateSimpleString(prenom, "Prénom");
		validateSimpleString(ville, "Libellé ville");
		validateSimpleString(telephone, "Téléphone");
		validateSimpleString(rue, "Libellé rue");
		validateSimpleString(code_postal, "Code postal");

		
		/* mot de passe avant modif */
		ancienMotDePasse = DAO.getUtilisateurByPseudo(ancienPseudo).getMot_de_passe();
		
		/* test si pseudo unique */
		if(!pseudo.equals(ancienPseudo) && DAO.getUtilisateurByPseudo(pseudo) != null) {
			throw new Exception("Pseudo déjà existant !");
		}
		
		/* test si email unique */
		ancienEmail = DAO.getUtilisateurByPseudo(ancienPseudo).getEmail();
		if(!email.equals(ancienEmail) && DAO.getUtilisateurByEmail(email) != null) {
			throw new Exception("Email déjà existant !");
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
	
	@Override
	public void removeUtilisateur(Utilisateur u) throws Exception {
		Connection cnx = ConnectionProvider.getConnection();
		cnx.setAutoCommit(false);
		
		System.out.println("Utilisateur impl");
		
		System.out.println("Resultat articles " + articleManager.getNbArticlesByUtilisateur(cnx, u));
		
		if(articleManager.getNbArticlesByUtilisateur(cnx, u) >= 1) {
			System.out.println("exception : trop d'articles");
			throw new Exception("Impossible de supprimer un compte ayant un article en vente.");
		}
		
		/*if(enchereManager.getNbEncheresByUtilisateur(cnx, u) >= 1) {
		throw new Exception("Impossible de supprimer un compte ayant des enchères en cours.");
		}*/
		
		try {	
			DAO.removeUtilisateur(cnx, u); 			
			cnx.commit();
		} catch (Exception e) {
			cnx.rollback(); 
			throw new Exception(e);
		}
		cnx.close(); 
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
	
	private void validateSimpleString(String str, String type) throws Exception {
        if(str.contains("<")) {
        	throw new Exception(type + " incorrect"); 
		}
	}

}
