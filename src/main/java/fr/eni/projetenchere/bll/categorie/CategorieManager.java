package fr.eni.projetenchere.bll.categorie;

import java.util.List;

import fr.eni.projetenchere.bo.Categorie;

public interface CategorieManager {
	
	public List<Categorie> getAll() throws Exception;
	
	public Categorie getCategorieById(int id) throws Exception;
}
