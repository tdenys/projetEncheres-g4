package fr.eni.projetenchere.dal.enchere;

public class EnchereDAOFact {

	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOImpl();
	}
}
