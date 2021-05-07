package fr.eni.projetenchere.dal;

public class EnchereDAOFact {

	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOImpl();
	}
}
