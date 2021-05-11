package fr.eni.projetenchere.dal.retrait;

import java.sql.Connection;

import fr.eni.projetenchere.bo.Retrait;

public interface RetraitDAO {

	public Retrait getRetraitById(int id) throws Exception;
	
	public Retrait insertRetrait(Retrait r) throws Exception;

	public Retrait updateRetrait(Retrait r) throws Exception;
	public Retrait updateRetrait(Connection cnx, Retrait r) throws Exception;

	public void removeRetrait(Retrait r) throws Exception;

	Retrait insertRetrait(Connection cnx, Retrait r) throws Exception;

}
