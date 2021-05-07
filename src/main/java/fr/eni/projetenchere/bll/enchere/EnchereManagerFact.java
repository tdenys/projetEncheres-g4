package fr.eni.projetenchere.bll.enchere;

public class EnchereManagerFact {
	
	private EnchereManagerFact() {
	}
	
	public static EnchereManager getInstance() {
		return new EnchereManagerImpl();
	}
	
}
