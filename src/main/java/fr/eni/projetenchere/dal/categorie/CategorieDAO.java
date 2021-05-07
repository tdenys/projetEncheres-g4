package fr.eni.projetenchere.dal.categorie;

import java.util.List;

import fr.eni.projetenchere.bo.Categorie;

public interface CategorieDAO { 

	public Categorie getCategorieById(int id) throws Exception;
	
	public List<Categorie> getAll() throws Exception;

}
