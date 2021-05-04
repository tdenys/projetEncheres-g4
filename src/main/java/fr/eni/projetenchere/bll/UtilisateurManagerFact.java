package fr.eni.projetenchere.bll;

public class UtilisateurManagerFact {
	
	private UtilisateurManagerFact() {
	}
	
	public static UtilisateurManager getInstance() {
		return new UtilisateurManagerImpl();
	}
	
}
