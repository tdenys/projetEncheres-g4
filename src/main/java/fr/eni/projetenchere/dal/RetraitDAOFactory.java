package fr.eni.projetenchere.dal;

public class RetraitDAOFactory {

	public static RetraitDAO getRetraitDAO() {
		return new RetraitDAOImpl();
	}
	
}
