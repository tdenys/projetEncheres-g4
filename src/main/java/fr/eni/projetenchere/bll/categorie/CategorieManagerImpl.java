package fr.eni.projetenchere.bll.categorie;

import java.util.List;

import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.dal.categorie.*;

public class CategorieManagerImpl implements CategorieManager {

	private CategorieDAO dao = CategorieDAOFactory.getCategorieDAO();
	
	@Override
	public List<Categorie> getAll() throws Exception {
		
		return dao.getAll();
	}

	@Override
	public Categorie getCategorieById(int id) throws Exception {

		return dao.getCategorieById(id);
	}
	
}
