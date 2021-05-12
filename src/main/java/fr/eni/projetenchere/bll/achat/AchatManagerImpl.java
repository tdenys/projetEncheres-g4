package fr.eni.projetenchere.bll.achat;

import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.utilisateur.UtilisateurDAO;
import fr.eni.projetenchere.dal.utilisateur.UtilisateurDAOFactory;

public class AchatManagerImpl implements AchatManager{
	
	private UtilisateurDAO utilisateurDAO = UtilisateurDAOFactory.getUtilisateurDAO();
	
	public static boolean CheckWithLuhn(String ccNumber)
    {
		
		// Algorithme de Luhn
		int i = 1;
		int newCB = 0;
        for (char ch: ccNumber.toCharArray()) {
        	int nb = Integer.parseInt(String.valueOf(ch));
        	if(i%2 != 0) {
        		nb = nb*2;
        		if(nb > 9) {
        			nb = nb - 9;
        		}
        	}
        	newCB += nb;
        	i++;
        	
        }
        return (newCB % 10 == 0);
    }

	@Override
	public Utilisateur doAchat(Utilisateur u, String ccn, int credit) throws Exception {
		if(CheckWithLuhn(ccn)) {
			try {
				u.setCredit(u.getCredit() + credit);
				utilisateurDAO.updateUtilisateurWithCredit(u);
			} catch(Exception e) {
				e.printStackTrace();
				throw new Exception("Le numéro de la carte est invalide");
			}
		}
		else {
			throw new Exception("Le numéro de la carte est invalide");
		}
		return u;
	} 
}
