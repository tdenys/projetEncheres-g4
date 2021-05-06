package fr.eni.projetenchere.bll.utilisateur;

public class UtilisateurManagerFact {
	
	private UtilisateurManagerFact() {
	}
	
	public static UtilisateurManager getInstance() {
		return new UtilisateurManagerImpl();
	}
	
}
