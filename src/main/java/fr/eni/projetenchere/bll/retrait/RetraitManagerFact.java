package fr.eni.projetenchere.bll.retrait;

public class RetraitManagerFact {

	public RetraitManagerFact() {
	}
	
	public static RetraitManager getInstance() {
		return new RetraitManagerImpl();
	}
	
}
