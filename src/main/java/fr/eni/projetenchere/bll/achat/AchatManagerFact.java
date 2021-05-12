package fr.eni.projetenchere.bll.achat;

public class AchatManagerFact {
	
	private AchatManagerFact() {
	}
	
	public static AchatManager getInstance() {
		return new AchatManagerImpl();
	}
}
