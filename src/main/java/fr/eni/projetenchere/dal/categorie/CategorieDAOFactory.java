package fr.eni.projetenchere.dal.categorie;

public class CategorieDAOFactory { 

	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOImpl();
	}
	
}
