package fr.eni.projetenchere.dal.enchere;

public class EnchereDAOFactory {

	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOImpl();
	}
}
