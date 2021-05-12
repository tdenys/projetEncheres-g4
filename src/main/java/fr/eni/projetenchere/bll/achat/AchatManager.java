package fr.eni.projetenchere.bll.achat;

import fr.eni.projetenchere.bo.Utilisateur;

public interface AchatManager {
	
	public Utilisateur doAchat(Utilisateur u, String ccn, int credit) throws Exception;
}
