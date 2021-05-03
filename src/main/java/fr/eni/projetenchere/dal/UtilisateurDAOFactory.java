package fr.eni.projetenchere.dal;

public class UtilisateurDAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOImpl();
	}
	
}
