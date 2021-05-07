package fr.eni.projetenchere.dal.retrait;

import fr.eni.projetenchere.bo.Retrait;

public interface RetraitDAO {

	public Retrait getRetraitById(int id) throws Exception;
	
	public Retrait insertRetrait(Retrait r) throws Exception;

	public Retrait updateRetrait(Retrait r) throws Exception;

	public void removeRetrait(Retrait r) throws Exception;

}
