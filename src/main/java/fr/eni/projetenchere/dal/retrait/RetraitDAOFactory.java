package fr.eni.projetenchere.dal.retrait;

public class RetraitDAOFactory {

	public static RetraitDAO getRetraitDAO() {
		return new RetraitDAOImpl();
	}
	
}
