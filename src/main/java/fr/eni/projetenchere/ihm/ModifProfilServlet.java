package fr.eni.projetenchere.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetenchere.bll.utilisateur.UtilisateurManager;
import fr.eni.projetenchere.bll.utilisateur.UtilisateurManagerFact;
import fr.eni.projetenchere.bo.Utilisateur;

@WebServlet("/profil/modifier")
public class ModifProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtilisateurManager manager = UtilisateurManagerFact.getInstance();
       
    public ModifProfilServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
		
		
		String sup = "0";
		sup = request.getParameter("sup");
		
		if("1".equals(sup)) {
			try {
				manager.removeUtilisateur(u);
				
				request.getSession().invalidate();
				response.sendRedirect(request.getContextPath() + "/");
			} catch (Exception e) {
				request.setAttribute("erreur", e.getMessage());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifProfil.jsp");
				rd.forward(request, response);
			}
		} else if(u != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifProfil.jsp");
			rd.forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = null; 
		String nom = null; 
		String prenom = null; 
		String email = null; 
		String telephone = null; 
		String rue = null; 
		String ville = null; 
		String codePostal = null; 
		String motDePasseActuel = null; 
		String nouveauMotDePasse = null;
		String motDePasseConfirmation = null;
		HttpSession session = request.getSession();
		Utilisateur uSession = (Utilisateur) session.getAttribute("utilisateur");
		
		pseudo = request.getParameter("pseudo");
		nom = request.getParameter("nom");
		prenom = request.getParameter("prenom");
		email = request.getParameter("email");
		telephone = request.getParameter("telephone");
		rue = request.getParameter("rue");
		ville = request.getParameter("ville");
		codePostal = request.getParameter("codePostal");
		motDePasseActuel = request.getParameter("motDePasseActuel");
		nouveauMotDePasse = request.getParameter("nouveauMotDePasse");
		motDePasseConfirmation = request.getParameter("motDePasseConfirmation");
		
		try {
			Utilisateur u = manager.updateUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasseActuel, nouveauMotDePasse, motDePasseConfirmation, uSession.getPseudo());
			u.setCredit(uSession.getCredit());
			u.setAdministrateur(uSession.isAdministrateur());
	        session.setAttribute("utilisateur", u);
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		doGet(request, response);
	}

}
