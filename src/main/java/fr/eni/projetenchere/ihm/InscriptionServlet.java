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

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UtilisateurManager manager = UtilisateurManagerFact.getInstance();
	
    public InscriptionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("utilisateur");
		if(u == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
			rd.forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/connexion");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// Cr�ation des champs a recuperer
		String pseudo = null; 
		String nom = null; 
		String prenom = null; 
		String email = null; 
		String telephone = null; 
		String rue = null; 
		String ville = null; 
		String codePostal = null; 
		String motDePasse = null; 
		String motDePasseConfirmation = null;
		
		// Lecture des parametres
		pseudo = request.getParameter("pseudo");
		nom = request.getParameter("nom");
		prenom = request.getParameter("prenom");
		email = request.getParameter("email");
		telephone = request.getParameter("telephone");
		rue = request.getParameter("rue");
		ville = request.getParameter("ville");
		codePostal = request.getParameter("codePostal");
		motDePasse = request.getParameter("motDePasse");
		motDePasseConfirmation = request.getParameter("motDePasseConfirmation");
		
		// Verification de l'existence en base 
		try {
			Utilisateur utilisateur = manager.insertUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, motDePasseConfirmation);
			
	        HttpSession session = request.getSession();
	        session.setAttribute("utilisateur", utilisateur);
	        session.setMaxInactiveInterval(300);
			
	        response.sendRedirect(request.getContextPath() + "/"); 
	        
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
			// Transfert de l'affichage a�la JSP
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
			rd.forward(request, response);
		}
	}
	
}
