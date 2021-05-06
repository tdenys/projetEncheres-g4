package fr.eni.projetenchere.dal;

public class CategorieDAOFactory { 

	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOImpl();
	}
	
}
