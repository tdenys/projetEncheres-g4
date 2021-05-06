package fr.eni.projetenchere.bll.categorie;

import java.util.List;

import fr.eni.projetenchere.bo.Categorie;

public class CategorieManagerImpl implements CategorieManager {

	private CategorieDAO dao = CategorieDAOFactory.getCategorieDAO();
	
	@Override
	public List<Categorie> getAll() {
		
		return dao.getAll();
	}

	@Override
	public Categorie getCategorieById(int id) throws Exception {

		return dao.getCategorieById(id);
	}
	
}
