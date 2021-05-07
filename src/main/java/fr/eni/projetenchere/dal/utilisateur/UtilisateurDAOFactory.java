package fr.eni.projetenchere.dal.utilisateur;

public class UtilisateurDAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOImpl();
	}
	
}
